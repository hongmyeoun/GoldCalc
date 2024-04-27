package com.hongmyeoun.goldcalc.viewModel.goldCheck

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.view.search.getCharDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoldSettingVM @Inject constructor(
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
        }
    }

    fun onDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            _character.value?.let {
                characterRepository.delete(_character.value!!)
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
        expanded = !expanded
    }

    var selectedTab by mutableStateOf("군단장")
    fun moveCommandRaid() {
        selectedTab = "군단장"
    }

    fun moveAbyssDungeon() {
        selectedTab = "어비스 던전"
    }

    fun moveKazeRaid() {
        selectedTab = "카제로스"
    }

    fun moveEpicRaid() {
        selectedTab = "에픽"
    }

    fun moveETC() {
        selectedTab = "기타"
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

    fun onReloadClick(context: Context, characterName: String?) {
        characterName?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val characterDetail = getCharDetail(context, characterName)
                characterDetail?.let {
                    updateCharacterDetail(characterDetail)
                }
            }
            getCharacter(characterName)
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
                                isClear = commandRaid.valtan.onePhase.clearCheck,
                                mCheck = commandRaid.valtan.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.command[0].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.valtan.twoPhase.level,
                                isClear = commandRaid.valtan.twoPhase.clearCheck,
                                mCheck = commandRaid.valtan.twoPhase.seeMoreCheck,
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
                                isClear = commandRaid.biackiss.onePhase.clearCheck,
                                mCheck = commandRaid.biackiss.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.command[1].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.biackiss.twoPhase.level,
                                isClear = commandRaid.biackiss.twoPhase.clearCheck,
                                mCheck = commandRaid.biackiss.twoPhase.seeMoreCheck,
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
                                isClear = commandRaid.koukuSaton.onePhase.clearCheck,
                                mCheck = commandRaid.koukuSaton.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.command[2].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.koukuSaton.twoPhase.level,
                                isClear = commandRaid.koukuSaton.twoPhase.clearCheck,
                                mCheck = commandRaid.koukuSaton.twoPhase.seeMoreCheck,
                            ),
                            originalCheckList.command[2].phases[2].copy( // 3페
                                difficulty = commandRaid.koukuSaton.threePhase.level,
                                isClear = commandRaid.koukuSaton.threePhase.clearCheck,
                                mCheck = commandRaid.koukuSaton.threePhase.seeMoreCheck
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
                                isClear = commandRaid.abrelshud.onePhase.clearCheck,
                                mCheck = commandRaid.abrelshud.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.command[3].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.abrelshud.twoPhase.level,
                                isClear = commandRaid.abrelshud.twoPhase.clearCheck,
                                mCheck = commandRaid.abrelshud.twoPhase.seeMoreCheck,
                            ),
                            originalCheckList.command[3].phases[2].copy( // 3페
                                difficulty = commandRaid.abrelshud.threePhase.level,
                                isClear = commandRaid.abrelshud.threePhase.clearCheck,
                                mCheck = commandRaid.abrelshud.threePhase.seeMoreCheck
                            ),
                            originalCheckList.command[3].phases[3].copy( // 4페
                                difficulty = commandRaid.abrelshud.fourPhase.level,
                                isClear = commandRaid.abrelshud.fourPhase.clearCheck,
                                mCheck = commandRaid.abrelshud.fourPhase.seeMoreCheck
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
                                isClear = commandRaid.illiakan.onePhase.clearCheck,
                                mCheck = commandRaid.illiakan.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.command[4].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.illiakan.twoPhase.level,
                                isClear = commandRaid.illiakan.twoPhase.clearCheck,
                                mCheck = commandRaid.illiakan.twoPhase.seeMoreCheck,
                            ),
                            originalCheckList.command[4].phases[2].copy( // 3페
                                difficulty = commandRaid.illiakan.threePhase.level,
                                isClear = commandRaid.illiakan.threePhase.clearCheck,
                                mCheck = commandRaid.illiakan.threePhase.seeMoreCheck
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
                                isClear = commandRaid.kamen.onePhase.clearCheck,
                                mCheck = commandRaid.kamen.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.command[5].phases[1].copy(
                                // 2페
                                difficulty = commandRaid.kamen.twoPhase.level,
                                isClear = commandRaid.kamen.twoPhase.clearCheck,
                                mCheck = commandRaid.kamen.twoPhase.seeMoreCheck,
                            ),
                            originalCheckList.command[5].phases[2].copy( // 3페
                                difficulty = commandRaid.kamen.threePhase.level,
                                isClear = commandRaid.kamen.threePhase.clearCheck,
                                mCheck = commandRaid.kamen.threePhase.seeMoreCheck
                            ),
                            originalCheckList.command[5].phases[3].copy( // 4페
                                difficulty = commandRaid.kamen.fourPhase.level,
                                isClear = commandRaid.kamen.fourPhase.clearCheck,
                                mCheck = commandRaid.kamen.fourPhase.seeMoreCheck
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
                                isClear = abyssDungeon.kayangel.onePhase.clearCheck,
                                mCheck = abyssDungeon.kayangel.onePhase.seeMoreCheck
                            ),
                            originalCheckList.abyssDungeon[0].phases[1].copy( // 2페
                                difficulty = abyssDungeon.kayangel.twoPhase.level,
                                isClear = abyssDungeon.kayangel.twoPhase.clearCheck,
                                mCheck = abyssDungeon.kayangel.twoPhase.seeMoreCheck
                            ),
                            originalCheckList.abyssDungeon[0].phases[2].copy( // 3페
                                difficulty = abyssDungeon.kayangel.threePhase.level,
                                isClear = abyssDungeon.kayangel.threePhase.clearCheck,
                                mCheck = abyssDungeon.kayangel.threePhase.seeMoreCheck
                            ),
                        ),
                        isCheck = abyssDungeon.kayangelCheck
                    ),
                    originalCheckList.abyssDungeon[1].copy( // 상아탑
                        phases = listOf(
                            originalCheckList.abyssDungeon[1].phases[0].copy( // 1페
                                difficulty = abyssDungeon.ivoryTower.onePhase.level,
                                isClear = abyssDungeon.ivoryTower.onePhase.clearCheck,
                                mCheck = abyssDungeon.ivoryTower.onePhase.seeMoreCheck
                            ),
                            originalCheckList.abyssDungeon[1].phases[1].copy( // 2페
                                difficulty = abyssDungeon.ivoryTower.twoPhase.level,
                                isClear = abyssDungeon.ivoryTower.twoPhase.clearCheck,
                                mCheck = abyssDungeon.ivoryTower.twoPhase.seeMoreCheck
                            ),
                            originalCheckList.abyssDungeon[1].phases[2].copy( // 3페
                                difficulty = abyssDungeon.ivoryTower.threePhase.level,
                                isClear = abyssDungeon.ivoryTower.threePhase.clearCheck,
                                mCheck = abyssDungeon.ivoryTower.threePhase.seeMoreCheck
                            ),
                            originalCheckList.abyssDungeon[1].phases[3].copy( // 4페
                                difficulty = abyssDungeon.ivoryTower.fourPhase.level,
                                isClear = abyssDungeon.ivoryTower.fourPhase.clearCheck,
                                mCheck = abyssDungeon.ivoryTower.fourPhase.seeMoreCheck
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
                                isClear = kazerothRaid.echidna.onePhase.clearCheck,
                                mCheck = kazerothRaid.echidna.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.kazeroth[0].phases[1].copy(
                                // 2페
                                difficulty = kazerothRaid.echidna.twoPhase.level,
                                isClear = kazerothRaid.echidna.twoPhase.clearCheck,
                                mCheck = kazerothRaid.echidna.twoPhase.seeMoreCheck,
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
                                isClear = epicRaid.behemoth.onePhase.clearCheck,
                                mCheck = epicRaid.behemoth.onePhase.seeMoreCheck,
                            ),
                            originalCheckList.epic[0].phases[1].copy(
                                // 2페
                                difficulty = epicRaid.behemoth.twoPhase.level,
                                isClear = epicRaid.behemoth.twoPhase.clearCheck,
                                mCheck = epicRaid.behemoth.twoPhase.seeMoreCheck,
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

            original.copy(
                weeklyGold = totalGold,
                plusGold = plusGold,
                minusGold = minusGold,
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