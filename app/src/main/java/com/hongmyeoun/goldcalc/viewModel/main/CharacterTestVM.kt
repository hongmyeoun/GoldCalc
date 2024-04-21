package com.hongmyeoun.goldcalc.viewModel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.goldCheck.AbyssDungeonModel
import com.hongmyeoun.goldcalc.model.goldCheck.CommandBossModel
import com.hongmyeoun.goldcalc.model.goldCheck.EpicRaidModel
import com.hongmyeoun.goldcalc.model.goldCheck.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.character.Phase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterTestVM @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val charName: String
): ViewModel() {
    private val _character = MutableStateFlow(Character())
    val character: StateFlow<Character> = _character

    private val _cmModel = MutableStateFlow(CommandBossModel(null))
    val cmModel: StateFlow<CommandBossModel> = _cmModel

    private val _abModel = MutableStateFlow(AbyssDungeonModel(null))
    val abModel: StateFlow<AbyssDungeonModel> = _abModel

    private val _kzModel = MutableStateFlow(KazerothRaidModel(null))
    val kzModel: StateFlow<KazerothRaidModel> = _kzModel

    private val _epModel = MutableStateFlow(EpicRaidModel(null))
    val epModel: StateFlow<EpicRaidModel> = _epModel

    var kamenTG by mutableStateOf(_character.value.raidPhaseInfo.kamenTotalGold)

    var enabled by mutableStateOf(true)
    fun enableDelay() {
        viewModelScope.launch {
            enabled = false
            delay(750)
            enabled = true
        }
    }

    init {
        getCharacter()
    }

    fun getCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getCharacterByName(charName).collect {
                _character.value = it
                getModel(it)
            }
        }
    }

    private fun getModel(character: Character) {
        _cmModel.value = CommandBossModel(character)
        _abModel.value = AbyssDungeonModel(character)
        _kzModel.value = KazerothRaidModel(character)
        _epModel.value = EpicRaidModel(character)
    }


    fun phaseCalc(phases: List<Phase>): Int {
        var phase = 1
        for (index in phases.indices) {
            if (phases[index].isClear) {
                phase = index + 1
            } else {
                break
            }
        }
        return phase
    }

    fun kamenGoldCalc(nowPhase: Int) {
        kamenTG = when (nowPhase) {
            1 -> { _cmModel.value.kamen.onePhase.totalGold }
            2 -> { _cmModel.value.kamen.onePhase.totalGold + _cmModel.value.kamen.twoPhase.totalGold }
            3 -> { _cmModel.value.kamen.onePhase.totalGold + _cmModel.value.kamen.twoPhase.totalGold + _cmModel.value.kamen.threePhase.totalGold }
            4 -> { _cmModel.value.kamen.totalGold }
            else -> { 0 }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(raidPhaseInfo = _character.value.raidPhaseInfo.copy(kamenPhase = nowPhase, kamenTotalGold = kamenTG))
            characterRepository.update(update)
            enableDelay()
        }
    }

}