package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.Character
import com.hongmyeoun.goldcalc.model.roomDB.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoldSettingVM(private val repository: CharacterRepository, charName: String) : ViewModel() {
    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    init {
        getCharacter(charName)
    }

    private fun getCharacter(charName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacterByName(charName).collect { character ->
                _character.value = character
            }
        }
    }

    fun onDoneClick(
        kazerothRaid: KazerothRaidVM
        ) {
        val originalCharacter = _character.value
        val originalCheckList = originalCharacter?.checkList

        val update = originalCheckList?.copy(
    //                command = listOf(
    //                    originalCheckList.command[0].copy( // 발탄
    //                        phases = listOf(
    //                            originalCheckList.command[0].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[0].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        ),
    //                        isCheck = ,
    //                    ),
    //                    originalCheckList.command[1].copy( // 비아키스
    //                        phases = listOf(
    //                            originalCheckList.command[1].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[1].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        ),
    //                        isCheck = ,
    //                    ),
    //                    originalCheckList.command[2].copy( // 쿠크세이튼
    //                        phases = listOf(
    //                            originalCheckList.command[2].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[2].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[2].phases[2].copy( // 3페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        ),
    //                        isCheck = ,
    //                    ),
    //                    originalCheckList.command[3].copy( // 아브렐슈드
    //                        phases = listOf(
    //                            originalCheckList.command[3].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[3].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[3].phases[2].copy( // 3페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[3].phases[3].copy( // 4페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        ),
    //                        isCheck = ,
    //                    ),
    //                    originalCheckList.command[4].copy( // 일리아칸
    //                        phases = listOf(
    //                            originalCheckList.command[4].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[4].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[4].phases[2].copy( // 3페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        ),
    //                        isCheck = ,
    //                    ),
    //                    originalCheckList.command[5].copy( // 카멘
    //                        phases = listOf(
    //                            originalCheckList.command[5].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[5].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[5].phases[2].copy( // 3페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.command[5].phases[3].copy( // 4페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        ),
    //                        isCheck = ,
    //                    ),
    //                ),
    //                abyssDungeon = listOf(
    //                    originalCheckList.abyssDungeon[0].copy( // 카양겔
    //                        phases = listOf(
    //                            originalCheckList.abyssDungeon[0].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                            originalCheckList.abyssDungeon[0].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                            originalCheckList.abyssDungeon[0].phases[2].copy( // 3페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                        )
    //                    ),
    //                    originalCheckList.abyssDungeon[1].copy( // 상아탑
    //                        phases = listOf(
    //                            originalCheckList.abyssDungeon[1].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                            originalCheckList.abyssDungeon[1].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                            originalCheckList.abyssDungeon[1].phases[2].copy( // 3페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                            originalCheckList.abyssDungeon[1].phases[3].copy( // 4페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck =
    //                            ),
    //                        )
    //                    )
    //                ),
            kazeroth = listOf(
                originalCheckList.kazeroth[0].copy( // 에키드나
                    phases = listOf(
                        originalCheckList.kazeroth[0].phases[0].copy( // 1페
                            difficulty = kazerothRaid.echidna.onePhase.level,
                            isClear = kazerothRaid.echidna.onePhase.clearCheck,
                            mCheck = kazerothRaid.echidna.onePhase.seeMoreCheck,
                        ),
                        originalCheckList.kazeroth[0].phases[1].copy( // 2페
                            difficulty = kazerothRaid.echidna.twoPhase.level,
                            isClear = kazerothRaid.echidna.twoPhase.clearCheck,
                            mCheck = kazerothRaid.echidna.twoPhase.seeMoreCheck,
                        ),
                    )
                )
            ),
    //                epic = listOf(
    //                    originalCheckList.epic[0].copy( // 베히모스
    //                        phases = listOf(
    //                            originalCheckList.epic[0].phases[0].copy( // 1페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                            originalCheckList.epic[0].phases[1].copy( // 2페
    //                                difficulty = ,
    //                                isClear = ,
    //                                mCheck = ,
    //                            ),
    //                        )
    //                    )
    //                ),
        )?.let {
            originalCharacter.copy(
                weeklyGold = totalGold,
                checkList = it
            )
        }
        update?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.update(update)
            }
        }
    }

    var plusGold by mutableStateOf("0")
    fun plusGoldValue(newValue: String) {
        plusGold = newValue.filter { it.isDigit() }
    }

    var minusGold by mutableStateOf("0")
    fun minusGoldValue(newValue: String) {
        minusGold = newValue.filter { it.isDigit() }
    }

    var totalGold by mutableStateOf(0)
    private fun calcTotalGold(cb: Int, ad: Int, kz: Int, ep: Int): Int {
        val plusGoldInt = plusGold.toIntOrNull() ?: 0
        val minusGoldInt = minusGold.toIntOrNull() ?: 0
        totalGold = cb + ad + kz + ep + plusGoldInt - minusGoldInt
        return totalGold
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

}