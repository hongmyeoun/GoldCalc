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

class CharacterCardVM @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val charName: String
): ViewModel() {
    private val _character = MutableStateFlow(Character())
    val character: StateFlow<Character> = _character

    private val _cmModel = MutableStateFlow(CommandBossModel(null))
    private val _abModel = MutableStateFlow(AbyssDungeonModel(null))
    private val _kzModel = MutableStateFlow(KazerothRaidModel(null))
    private val _epModel = MutableStateFlow(EpicRaidModel(null))

    private val _totalGold = MutableStateFlow(0)
    val totalGold: StateFlow<Int> = _totalGold

    private val _kamenTG = MutableStateFlow(0)
    private val _illiakanTG = MutableStateFlow(0)
    private val _abrelshudTG = MutableStateFlow(0)
    private val _koukuSatonTG = MutableStateFlow(0)
    private val _biackissTG = MutableStateFlow(0)
    private val _valtanTG = MutableStateFlow(0)

    private val _kayangelTG = MutableStateFlow(0)
    private val _ivoryTowerTG = MutableStateFlow(0)

    private val _echidnaTG = MutableStateFlow(0)

    private val _behemothTG = MutableStateFlow(0)

    private val _maxGold = MutableStateFlow(0)

    private val _progressPercentage = MutableStateFlow(0f)
    val progressPercentage: StateFlow<Float> = _progressPercentage

    private fun updateProgressPercentage() {
        _progressPercentage.value = if (_maxGold.value != 0) _totalGold.value.toFloat() / _maxGold.value else 0.0f
    }

    var enabled by mutableStateOf(true)
    fun enableDelay() {
        viewModelScope.launch {
            enabled = false
            delay(500)
            enabled = true
        }
    }

    init {
        getCharacter()
        updateProgressPercentage()
    }

    private fun calcTotalGold() {
        val commandTG = _kamenTG.value + _illiakanTG.value + _abrelshudTG.value + _koukuSatonTG.value + _biackissTG.value + _valtanTG.value
        val abyssDungeonTG = _ivoryTowerTG.value + _kayangelTG.value
        val kazerothTG = _echidnaTG.value
        val epicTG = _behemothTG.value
        _totalGold.value = commandTG + abyssDungeonTG + kazerothTG + epicTG
        updateProgressPercentage()
    }

    private fun getCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getCharacterByName(charName).collect { character ->
                character?.let { // 이걸 안하면 삭제시 없는 페이지에 없는 character(null)값이 들어와서 객체들을 불러오지 못해 튕김
                    _character.value = it
                    getModel(it)
                }
                initTG(_character.value)
            }
        }
    }

    fun initTG(character: Character) {
        _totalGold.value = character.earnGold + character.plusGold.toInt() - character.minusGold.toInt()
        _kamenTG.value = character.raidPhaseInfo.kamenTotalGold
        _illiakanTG.value = character.raidPhaseInfo.illiakanTotalGold
        _abrelshudTG.value = character.raidPhaseInfo.abrelTotalGold
        _koukuSatonTG.value = character.raidPhaseInfo.koukuTotalGold
        _biackissTG.value = character.raidPhaseInfo.biackissTotalGold
        _valtanTG.value = character.raidPhaseInfo.valtanTotalGold
        _kayangelTG.value = character.raidPhaseInfo.kayangelTotalGold
        _ivoryTowerTG.value = character.raidPhaseInfo.ivoryTotalGold
        _echidnaTG.value = character.raidPhaseInfo.echidnaTotalGold
        _behemothTG.value = character.raidPhaseInfo.behemothTotalGold

        _maxGold.value = character.weeklyGold
        _progressPercentage.value = if (_maxGold.value != 0) _totalGold.value.toFloat() / _maxGold.value else 0.0f
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
        _kamenTG.value = when (nowPhase) {
            1 -> { _cmModel.value.kamen.onePhase.totalGold }
            2 -> { _cmModel.value.kamen.onePhase.totalGold + _cmModel.value.kamen.twoPhase.totalGold }
            3 -> { _cmModel.value.kamen.onePhase.totalGold + _cmModel.value.kamen.twoPhase.totalGold + _cmModel.value.kamen.threePhase.totalGold }
            4 -> { _cmModel.value.kamen.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(kamenPhase = nowPhase, kamenTotalGold = _kamenTG.value))
            characterRepository.update(update)
        }
    }

    fun illiGoldCalc(nowPhase: Int) {
        _illiakanTG.value = when (nowPhase) {
            1 -> { _cmModel.value.illiakan.onePhase.totalGold }
            2 -> { _cmModel.value.illiakan.onePhase.totalGold + _cmModel.value.illiakan.twoPhase.totalGold }
            3 -> { _cmModel.value.illiakan.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(illiakanPhase = nowPhase, illiakanTotalGold = _illiakanTG.value))
            characterRepository.update(update)
        }
    }

    fun abrelGoldCalc(nowPhase: Int) {
        _abrelshudTG.value = when (nowPhase) {
            1 -> { _cmModel.value.abrelshud.onePhase.totalGold }
            2 -> { _cmModel.value.abrelshud.onePhase.totalGold + _cmModel.value.abrelshud.twoPhase.totalGold }
            3 -> { _cmModel.value.abrelshud.onePhase.totalGold + _cmModel.value.abrelshud.twoPhase.totalGold + _cmModel.value.abrelshud.threePhase.totalGold }
            4 -> { _cmModel.value.abrelshud.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(abrelPhase = nowPhase, abrelTotalGold = _abrelshudTG.value))
            characterRepository.update(update)
        }
    }

    fun kokuGoldCalc(nowPhase: Int) {
        _koukuSatonTG.value = when (nowPhase) {
            1 -> { _cmModel.value.koukuSaton.onePhase.totalGold }
            2 -> { _cmModel.value.koukuSaton.onePhase.totalGold + _cmModel.value.koukuSaton.twoPhase.totalGold }
            3 -> { _cmModel.value.koukuSaton.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(koukuPhase = nowPhase, koukuTotalGold = _koukuSatonTG.value))
            characterRepository.update(update)
        }
    }

    fun biaGoldCalc(nowPhase: Int) {
        _biackissTG.value = when (nowPhase) {
            1 -> { _cmModel.value.biackiss.onePhase.totalGold }
            2 -> { _cmModel.value.biackiss.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(biackissPhase = nowPhase, biackissTotalGold = _biackissTG.value))
            characterRepository.update(update)
        }
    }

    fun valGoldCalc(nowPhase: Int) {
        _valtanTG.value = when (nowPhase) {
            1 -> { _cmModel.value.valtan.onePhase.totalGold }
            2 -> { _cmModel.value.valtan.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(valtanPhase = nowPhase, valtanTotalGold = _valtanTG.value))
            characterRepository.update(update)
        }
    }

    fun kayanGoldCalc(nowPhase: Int) {
        _kayangelTG.value = when (nowPhase) {
            1 -> { _abModel.value.kayangel.onePhase.totalGold }
            2 -> { _abModel.value.kayangel.onePhase.totalGold + _abModel.value.kayangel.twoPhase.totalGold }
            3 -> { _abModel.value.kayangel.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value,raidPhaseInfo = _character.value.raidPhaseInfo.copy(kayangelPhase = nowPhase, kayangelTotalGold = _kayangelTG.value))
            characterRepository.update(update)
        }
    }

    fun iTGoldCalc(nowPhase: Int) {
        _ivoryTowerTG.value = when (nowPhase) {
            1 -> { _abModel.value.ivoryTower.onePhase.totalGold }
            2 -> { _abModel.value.ivoryTower.onePhase.totalGold + _abModel.value.ivoryTower.twoPhase.totalGold }
            3 -> { _abModel.value.ivoryTower.onePhase.totalGold + _abModel.value.ivoryTower.twoPhase.totalGold + _abModel.value.ivoryTower.threePhase.totalGold }
            4 -> { _abModel.value.ivoryTower.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value,raidPhaseInfo = _character.value.raidPhaseInfo.copy(ivoryPhase = nowPhase, ivoryTotalGold = _ivoryTowerTG.value))
            characterRepository.update(update)
        }
    }

    fun echiGoldCalc(nowPhase: Int) {
        _echidnaTG.value = when (nowPhase) {
            1 -> { _kzModel.value.echidna.onePhase.totalGold }
            2 -> { _kzModel.value.echidna.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value,raidPhaseInfo = _character.value.raidPhaseInfo.copy(echidnaPhase = nowPhase, echidnaTotalGold = _echidnaTG.value))
            characterRepository.update(update)
        }
    }

    fun beheGoldCalc(nowPhase: Int) {
        _behemothTG.value = when (nowPhase) {
            1 -> { _epModel.value.behemoth.onePhase.totalGold }
            2 -> { _epModel.value.behemoth.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(behemothPhase = nowPhase, behemothTotalGold = _behemothTG.value))
            characterRepository.update(update)
        }
    }

}

class GoldContentStateVM(initPhase: Int) : ViewModel() {
    var nowPhase by mutableStateOf(initPhase)

    fun onClicked(phase: Int): Int {
        if (nowPhase == phase) {
            nowPhase = 0
        } else {
            nowPhase += 1
        }
        return nowPhase
    }
}
