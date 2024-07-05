package com.hongmyeoun.goldcalc.viewModel.goldCheck

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote.getCharDetail
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeworkVM @Inject constructor(
    private val characterRepository: CharacterRepository,
    charName: String
) : ViewModel() {
    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDissmissRequest() {
        _showDialog.value = false
    }

    fun onClicked() {
        _showDialog.value = true
    }

    private val _showDetail = MutableStateFlow(false)
    val showDetail: StateFlow<Boolean> = _showDetail

    fun onShowDetailClicked() {
        _showDetail.value = !_showDetail.value
    }

    var plusGold by mutableStateOf("0")
    var minusGold by mutableStateOf("0")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun getCharacter(charName: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getCharacterByName(charName).collect { character ->
                _character.value = character
            }
        }
        _isLoading.value = false
    }

    init {
        getCharacter(charName)
        viewModelScope.launch(Dispatchers.Main) {
            plusGold = _character.value?.plusGold ?: "0"
            minusGold = _character.value?.minusGold ?: "0"
            calcETCGold()
        }
    }

    fun onDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            _character.value?.let {
                characterRepository.delete(_character.value!!)
            }
        }
    }

    fun onAvatarClick(character: Character?) {
        character?.let {
            if (!character.characterImage.isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val newValue = !character.avatarImage
                    val avatarImgUpdate = _character.value!!.copy(avatarImage = newValue)
                    characterRepository.update(avatarImgUpdate)
                }
            }
        }
    }


    fun plusGoldValue(newValue: String) {
        plusGold = newValue.filter { it.isDigit() }
    }

    fun minusGoldValue(newValue: String) {
        minusGold = newValue.filter { it.isDigit() }
    }

    var etcGold by mutableStateOf(0)
    fun calcETCGold() {
        val plusGoldInt = plusGold.toIntOrNull() ?: 0
        val minusGoldInt = minusGold.toIntOrNull() ?: 0
        etcGold = plusGoldInt - minusGoldInt
    }

    var totalGold by mutableStateOf(0)
    private fun calcTotalGold(cb: Int, ad: Int, kz: Int, ep: Int) {
        calcETCGold()
        totalGold = cb + ad + kz + ep + etcGold
    }

    fun updateTotalGold(cb: Int, ad: Int, kz: Int, ep: Int) {
        calcTotalGold(cb, ad, kz, ep)
    }

    var expanded by mutableStateOf(false)
    fun expand() {
        expanded = true
    }
    fun close() {
        expanded = false
    }

    val headerTitle = listOf("군단장", "어비스 던전", "카제로스", "에픽", "기타")
    var selectedTab by mutableStateOf(0)
    fun moveHeader(index: Int) {
        selectedTab = index
    }

    private fun updateCharacterDetail(characterDetail: CharacterDetail) {
        _character.value?.let {
            val newDetail = _character.value!!.copy(
                itemLevel = characterDetail.itemMaxLevel,
                guildName = characterDetail.guildName,
                title = characterDetail.title,
                characterLevel = characterDetail.characterLevel,
                expeditionLevel = characterDetail.expeditionLevel,
                pvpGradeName = characterDetail.pvpGradeName,
                townLevel = characterDetail.townLevel,
                townName = characterDetail.townName,
                characterImage = characterDetail.characterImage,
            )
            characterRepository.update(newDetail)
        }
    }

    fun onReloadClick(context: Context, characterName: String?, snackbarHostState: SnackbarHostState) {
        characterName?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val characterDetail = getCharDetail(context, characterName)
                characterDetail?.let {
                    updateCharacterDetail(characterDetail)
                }
            }
            getCharacter(characterName)
        }
        doneSnackbar(
            snackbarHostState = snackbarHostState,
            text = "캐릭터 정보를 갱신했습니다."
        )
    }

    fun doneSnackbar(
        snackbarHostState: SnackbarHostState,
        text: String,
    ) {
        viewModelScope.launch {
            val job = launch {
                snackbarHostState.showSnackbar(message = text)
            }
            delay(2000L)
            job.cancel()
        }
    }

    fun onDoneClick(commandRaid: CommandBossVM, abyssDungeon: AbyssDungeonVM, kazerothRaid: KazerothRaidVM, epicRaid: EpicRaidVM) {
        val originalCharacter = _character.value
        val originalCheckList = originalCharacter?.checkList
        val originalRaidInfo = originalCharacter?.raidPhaseInfo

        val update = originalCharacter?.let { original ->
            val updatedCheckList = originalCheckList?.copy(
                command = listOf(
                    originalCheckList.command[0].copy(
                        // 발탄
                        phases = listOf(
                            originalCheckList.command[0].phases[0].copy(
                                // 1페
                                difficulty = commandRaid.valtan.onePhase.level,
                                isClear =  if (commandRaid.valtanCheck) commandRaid.valtan.onePhase.clearCheck else false,
                                mCheck = if (commandRaid.valtanCheck) commandRaid.valtan.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[0].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.valtan.twoPhase.level,
                                isClear =  if (commandRaid.valtanCheck) commandRaid.valtan.twoPhase.clearCheck else false,
                                mCheck = if (commandRaid.valtanCheck) commandRaid.valtan.twoPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = commandRaid.valtanCheck,
                    ),
                    originalCheckList.command[1].copy(
                        // 비아키스
                        phases = listOf(
                            originalCheckList.command[1].phases[0].copy(
                                // 1페
                                difficulty = commandRaid.biackiss.onePhase.level,
                                isClear =  if (commandRaid.biaCheck) commandRaid.biackiss.onePhase.clearCheck else false,
                                mCheck = if (commandRaid.biaCheck) commandRaid.biackiss.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[1].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.biackiss.twoPhase.level,
                                isClear =  if (commandRaid.biaCheck) commandRaid.biackiss.twoPhase.clearCheck else false,
                                mCheck = if (commandRaid.biaCheck) commandRaid.biackiss.twoPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = commandRaid.biaCheck,
                    ),
                    originalCheckList.command[2].copy(
                        // 쿠크세이튼
                        phases = listOf(
                            originalCheckList.command[2].phases[0].copy(
                                // 1페
                                difficulty = commandRaid.koukuSaton.onePhase.level,
                                isClear =  if (commandRaid.koukuCheck) commandRaid.koukuSaton.onePhase.clearCheck else false,
                                mCheck = if (commandRaid.koukuCheck) commandRaid.koukuSaton.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[2].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.koukuSaton.twoPhase.level,
                                isClear =  if (commandRaid.koukuCheck) commandRaid.koukuSaton.twoPhase.clearCheck else false,
                                mCheck = if (commandRaid.koukuCheck) commandRaid.koukuSaton.twoPhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[2].phases[2].copy( // 3페
                                difficulty = commandRaid.koukuSaton.threePhase.level,
                                isClear =  if (commandRaid.koukuCheck) commandRaid.koukuSaton.threePhase.clearCheck else false,
                                mCheck = if (commandRaid.koukuCheck) commandRaid.koukuSaton.threePhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = commandRaid.koukuCheck,
                    ),
                    originalCheckList.command[3].copy(
                        // 아브렐슈드
                        phases = listOf(
                            originalCheckList.command[3].phases[0].copy(
                                // 1페
                                difficulty = commandRaid.abrelshud.onePhase.level,
                                isClear =  if (commandRaid.abreCheck) commandRaid.abrelshud.onePhase.clearCheck else false,
                                mCheck = if (commandRaid.abreCheck) commandRaid.abrelshud.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[3].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.abrelshud.twoPhase.level,
                                isClear =  if (commandRaid.abreCheck) commandRaid.abrelshud.twoPhase.clearCheck else false,
                                mCheck = if (commandRaid.abreCheck) commandRaid.abrelshud.twoPhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[3].phases[2].copy( // 3페
                                difficulty = commandRaid.abrelshud.threePhase.level,
                                isClear =  if (commandRaid.abreCheck) commandRaid.abrelshud.threePhase.clearCheck else false,
                                mCheck = if (commandRaid.abreCheck) commandRaid.abrelshud.threePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[3].phases[3].copy( // 4페
                                difficulty = commandRaid.abrelshud.fourPhase.level,
                                isClear =  if (commandRaid.abreCheck) commandRaid.abrelshud.fourPhase.clearCheck else false,
                                mCheck = if (commandRaid.abreCheck) commandRaid.abrelshud.fourPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = commandRaid.abreCheck,
                    ),
                    originalCheckList.command[4].copy(
                        // 일리아칸
                        phases = listOf(
                            originalCheckList.command[4].phases[0].copy(
                                // 1페
                                difficulty = commandRaid.illiakan.onePhase.level,
                                isClear =  if (commandRaid.illiCheck) commandRaid.illiakan.onePhase.clearCheck else false,
                                mCheck = if (commandRaid.illiCheck) commandRaid.illiakan.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[4].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.illiakan.twoPhase.level,
                                isClear =  if (commandRaid.illiCheck) commandRaid.illiakan.twoPhase.clearCheck else false,
                                mCheck = if (commandRaid.illiCheck) commandRaid.illiakan.twoPhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[4].phases[2].copy( // 3페
                                difficulty = commandRaid.illiakan.threePhase.level,
                                isClear =  if (commandRaid.illiCheck) commandRaid.illiakan.threePhase.clearCheck else false,
                                mCheck = if (commandRaid.illiCheck) commandRaid.illiakan.threePhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = commandRaid.illiCheck,
                    ),
                    originalCheckList.command[5].copy(
                        // 카멘
                        phases = listOf(
                            originalCheckList.command[5].phases[0].copy(
                                // 1페
                                difficulty = commandRaid.kamen.onePhase.level,
                                isClear =  if (commandRaid.kamenCheck) commandRaid.kamen.onePhase.clearCheck else false,
                                mCheck = if (commandRaid.kamenCheck) commandRaid.kamen.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[5].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.kamen.twoPhase.level,
                                isClear =  if (commandRaid.kamenCheck) commandRaid.kamen.twoPhase.clearCheck else false,
                                mCheck = if (commandRaid.kamenCheck) commandRaid.kamen.twoPhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[5].phases[2].copy( // 3페
                                difficulty = commandRaid.kamen.threePhase.level,
                                isClear =  if (commandRaid.kamenCheck) commandRaid.kamen.threePhase.clearCheck else false,
                                mCheck = if (commandRaid.kamenCheck) commandRaid.kamen.threePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.command[5].phases[3].copy( // 4페
                                difficulty = commandRaid.kamen.fourPhase.level,
                                isClear =  if (commandRaid.kamenCheck) commandRaid.kamen.fourPhase.clearCheck else false,
                                mCheck = if (commandRaid.kamenCheck) commandRaid.kamen.fourPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = commandRaid.kamenCheck,
                    ),
                ),
                abyssDungeon = listOf(
                    originalCheckList.abyssDungeon[0].copy( // 카양겔
                        phases = listOf(
                            originalCheckList.abyssDungeon[0].phases[0].copy( // 1페
                                difficulty = abyssDungeon.kayangel.onePhase.level,
                                isClear =  if (abyssDungeon.kayangelCheck) abyssDungeon.kayangel.onePhase.clearCheck else false,
                                mCheck = if (abyssDungeon.kayangelCheck) abyssDungeon.kayangel.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.abyssDungeon[0].phases[1].copy( // 2페
                                difficulty = abyssDungeon.kayangel.twoPhase.level,
                                isClear =  if (abyssDungeon.kayangelCheck) abyssDungeon.kayangel.twoPhase.clearCheck else false,
                                mCheck = if (abyssDungeon.kayangelCheck) abyssDungeon.kayangel.twoPhase.seeMoreCheck else false,
                            ),
                            originalCheckList.abyssDungeon[0].phases[2].copy( // 3페
                                difficulty = abyssDungeon.kayangel.threePhase.level,
                                isClear =  if (abyssDungeon.kayangelCheck) abyssDungeon.kayangel.threePhase.clearCheck else false,
                                mCheck = if (abyssDungeon.kayangelCheck) abyssDungeon.kayangel.threePhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = abyssDungeon.kayangelCheck
                    ),
                    originalCheckList.abyssDungeon[1].copy( // 상아탑
                        phases = listOf(
                            originalCheckList.abyssDungeon[1].phases[0].copy( // 1페
                                difficulty = abyssDungeon.ivoryTower.onePhase.level,
                                isClear =  if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.onePhase.clearCheck else false,
                                mCheck = if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.abyssDungeon[1].phases[1].copy( // 2페
                                difficulty = abyssDungeon.ivoryTower.twoPhase.level,
                                isClear =  if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.twoPhase.clearCheck else false,
                                mCheck = if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.twoPhase.seeMoreCheck else false,
                            ),
                            originalCheckList.abyssDungeon[1].phases[2].copy( // 3페
                                difficulty = abyssDungeon.ivoryTower.threePhase.level,
                                isClear =  if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.threePhase.clearCheck else false,
                                mCheck = if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.threePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.abyssDungeon[1].phases[3].copy( // 4페
                                difficulty = abyssDungeon.ivoryTower.fourPhase.level,
                                isClear =  if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.fourPhase.clearCheck else false,
                                mCheck = if (abyssDungeon.ivoryCheck) abyssDungeon.ivoryTower.fourPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = abyssDungeon.ivoryCheck
                    )
                ),
                kazeroth = listOf(
                    originalCheckList.kazeroth[0].copy( // 에키드나
                        phases = listOf(
                            originalCheckList.kazeroth[0].phases[0].copy(
                                // 1페
                                difficulty = kazerothRaid.echidna.onePhase.level,
                                isClear =  if (kazerothRaid.echiCheck) kazerothRaid.echidna.onePhase.clearCheck else false,
                                mCheck = if (kazerothRaid.echiCheck) kazerothRaid.echidna.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.kazeroth[0].phases[1].copy(
                                // 2페
                                difficulty = kazerothRaid.echidna.twoPhase.level,
                                isClear =  if (kazerothRaid.echiCheck) kazerothRaid.echidna.twoPhase.clearCheck else false,
                                mCheck = if (kazerothRaid.echiCheck) kazerothRaid.echidna.twoPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = kazerothRaid.echiCheck
                    )
                ),
                epic = listOf(
                    originalCheckList.epic[0].copy( // 베히모스
                        phases = listOf(
                            originalCheckList.epic[0].phases[0].copy(
                                // 1페
                                difficulty = epicRaid.behemoth.onePhase.level,
                                isClear =  if (epicRaid.beheCheck) epicRaid.behemoth.onePhase.clearCheck else false,
                                mCheck = if (epicRaid.beheCheck) epicRaid.behemoth.onePhase.seeMoreCheck else false,
                            ),
                            originalCheckList.epic[0].phases[1].copy(
                                // 2페
                                difficulty = epicRaid.behemoth.twoPhase.level,
                                isClear =  if (epicRaid.beheCheck) epicRaid.behemoth.twoPhase.clearCheck else false,
                                mCheck = if (epicRaid.beheCheck) epicRaid.behemoth.twoPhase.seeMoreCheck else false,
                            ),
                        ),
                        isCheck = epicRaid.beheCheck
                    )
                ),
            )

            val updatedRaidInfo = originalRaidInfo?.copy(
                behemothPhase = if (!epicRaid.beheCheck) 0 else originalRaidInfo.behemothPhase,
                behemothTotalGold = if (!epicRaid.beheCheck) 0 else originalRaidInfo.behemothTotalGold,
                echidnaPhase = if (!kazerothRaid.echiCheck) 0 else originalRaidInfo.echidnaPhase,
                echidnaTotalGold = if (!kazerothRaid.echiCheck) 0 else originalRaidInfo.echidnaTotalGold,
                kamenPhase = if (!commandRaid.kamenCheck) 0 else originalRaidInfo.kamenPhase,
                kamenTotalGold = if (!commandRaid.kamenCheck) 0 else originalRaidInfo.kamenTotalGold,
                ivoryPhase = if (!abyssDungeon.ivoryCheck) 0 else originalRaidInfo.ivoryPhase,
                ivoryTotalGold = if (!abyssDungeon.ivoryCheck) 0 else originalRaidInfo.ivoryTotalGold,
                illiakanPhase = if (!commandRaid.illiCheck) 0 else originalRaidInfo.illiakanPhase,
                illiakanTotalGold = if (!commandRaid.illiCheck) 0 else originalRaidInfo.illiakanTotalGold,
                kayangelPhase = if (!abyssDungeon.kayangelCheck) 0 else originalRaidInfo.kayangelPhase,
                kayangelTotalGold = if (!abyssDungeon.kayangelCheck) 0 else originalRaidInfo.kayangelTotalGold,
                abrelPhase = if (!commandRaid.abreCheck) 0 else originalRaidInfo.abrelPhase,
                abrelTotalGold = if (!commandRaid.abreCheck) 0 else originalRaidInfo.abrelTotalGold,
                koukuPhase = if (!commandRaid.koukuCheck) 0 else originalRaidInfo.koukuPhase,
                koukuTotalGold = if (!commandRaid.koukuCheck) 0 else originalRaidInfo.koukuTotalGold,
                biackissPhase = if (!commandRaid.biaCheck) 0 else originalRaidInfo.biackissPhase,
                biackissTotalGold = if (!commandRaid.biaCheck) 0 else originalRaidInfo.biackissTotalGold,
                valtanPhase = if (!commandRaid.valtanCheck) 0 else originalRaidInfo.valtanPhase,
                valtanTotalGold = if (!commandRaid.valtanCheck) 0 else originalRaidInfo.valtanTotalGold,
            )

            var updateEarnGold = original.earnGold
            updatedRaidInfo?.let {
                updateEarnGold = updatedRaidInfo.behemothTotalGold + updatedRaidInfo.echidnaTotalGold + updatedRaidInfo.kamenTotalGold + updatedRaidInfo.ivoryTotalGold + updatedRaidInfo.illiakanTotalGold + updatedRaidInfo.kayangelTotalGold + updatedRaidInfo.abrelTotalGold + updatedRaidInfo.koukuTotalGold + updatedRaidInfo.biackissTotalGold + updatedRaidInfo.valtanTotalGold
            }

            original.copy(
                weeklyGold = totalGold,
                plusGold = plusGold,
                minusGold = minusGold,
                earnGold = updateEarnGold,
                checkList = updatedCheckList?: original.checkList,
                raidPhaseInfo = updatedRaidInfo?: original.raidPhaseInfo
            )
        }

        update?.let {
            viewModelScope.launch(Dispatchers.IO) {
                characterRepository.update(update)
            }
        }
    }
}