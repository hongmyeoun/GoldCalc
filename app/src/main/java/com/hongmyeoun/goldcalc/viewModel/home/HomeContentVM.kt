package com.hongmyeoun.goldcalc.viewModel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.homework.AbyssDungeonModel
import com.hongmyeoun.goldcalc.model.homework.CommandBossModel
import com.hongmyeoun.goldcalc.model.homework.EpicRaidModel
import com.hongmyeoun.goldcalc.model.homework.EventRaidModel
import com.hongmyeoun.goldcalc.model.homework.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.character.Phase
import com.hongmyeoun.goldcalc.model.roomDB.character.RaidList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

class HomeContentVM @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val charName: String
): ViewModel() {
    private val _character = MutableStateFlow(Character())
    val character: StateFlow<Character> = _character

    private val _cmModel = MutableStateFlow(CommandBossModel(null))
    private val _abModel = MutableStateFlow(AbyssDungeonModel(null))
    private val _kzModel = MutableStateFlow(KazerothRaidModel(null))
    private val _epModel = MutableStateFlow(EpicRaidModel(null))
    private val _eventModel = MutableStateFlow(EventRaidModel(null))

    private val _totalGold = MutableStateFlow(0)
    val totalGold: StateFlow<Int> = _totalGold

    private val _extraGold = MutableStateFlow(0)
    private val _remainGold = MutableStateFlow(0)

    private val _totalGoldPercentage = MutableStateFlow(0)

    private val _kamenTG = MutableStateFlow(0)
    private val _illiakanTG = MutableStateFlow(0)
    private val _abrelshudTG = MutableStateFlow(0)
    private val _koukuSatonTG = MutableStateFlow(0)
    private val _biackissTG = MutableStateFlow(0)
    private val _valtanTG = MutableStateFlow(0)

    private val _kayangelTG = MutableStateFlow(0)
    private val _ivoryTowerTG = MutableStateFlow(0)

    private val _echidnaTG = MutableStateFlow(0)
    private val _egirTG = MutableStateFlow(0)
    private val _ablreshudTG2 = MutableStateFlow(0)
    private val _mordumTG = MutableStateFlow(0)

    private val _behemothTG = MutableStateFlow(0)
    private val _eventTG = MutableStateFlow(0)

    private val _maxGold = MutableStateFlow(0)

    private val _progressPercentage = MutableStateFlow(0f)
    val progressPercentage: StateFlow<Float> = _progressPercentage

    private fun updateProgressPercentage() {
        _progressPercentage.value = if (_maxGold.value != 0) 1 - (_remainGold.value.toFloat() / _totalGoldPercentage.value) else 0.0f
    }

    var enabled by mutableStateOf(true)

    init {
        getCharacter()
    }

    private fun calcTotalGold() {
        val commandTG = _kamenTG.value + _illiakanTG.value + _abrelshudTG.value + _koukuSatonTG.value + _biackissTG.value + _valtanTG.value
        val abyssDungeonTG = _ivoryTowerTG.value + _kayangelTG.value
        val kazerothTG = _mordumTG.value + _ablreshudTG2.value + _egirTG.value + _echidnaTG.value
        val epicTG = _behemothTG.value
        val eventTG = _eventTG.value
        _totalGold.value = commandTG + abyssDungeonTG + kazerothTG + epicTG + eventTG
        updateProgressPercentage()
    }

    private fun getCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getCharacterByName(charName).collect { character ->
                character?.let { // 이걸 안하면 삭제시 없는 페이지에 없는 character(null)값이 들어와서 객체들을 불러오지 못해 튕김
                    if (it.checkList.kazeroth.size < 3) {
                        val abrelshud2 = RaidList(
                            name = Raid.Name.ABRELSHUD_2,
                            phases = listOf(Phase(), Phase())
                        )

                        val update = it.checkList.kazeroth.toMutableList()
                        update.add(abrelshud2)

                        val updateCheckList = it.checkList.copy(kazeroth = update)
                        val updateChar = it.copy(checkList = updateCheckList)

                        characterRepository.update(updateChar)
                        _character.value = updateChar
                        getModel(updateChar)
                    } else if (it.checkList.kazeroth.size < 4) {
                        val mordum = RaidList(
                            name = Raid.Name.MORDUM,
                            phases = listOf(Phase(), Phase(), Phase())
                        )

                        val update = it.checkList.kazeroth.toMutableList()
                        update.add(mordum)

                        val updateCheckList = it.checkList.copy(kazeroth = update)
                        val updateChar = it.copy(checkList = updateCheckList)

                        characterRepository.update(updateChar)
                        _character.value = updateChar
                        getModel(updateChar)
                    } else {
                        val newInfo = ensureRaidPhaseInfoUpdated(it)
                        _character.value = newInfo
                        getModel(newInfo)
                    }
                }
                initTG(_character.value)
            }
        }
    }

    private fun ensureRaidPhaseInfoUpdated(character: Character): Character {
        val oldInfo = character.raidPhaseInfo
        return if (oldInfo.eventPhase == 0 && oldInfo.eventTotalGold == 0) {
            character.copy(
                raidPhaseInfo = oldInfo.copy(
                    eventPhase = 0,
                    eventTotalGold = 0
                )
            )
        } else {
            character
        }
    }

    fun onAvatarClick(character: Character?) {
        character?.let {
            if (!character.characterImage.isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val newValue = !character.avatarImage
                    val avatarImgUpdate = _character.value.copy(avatarImage = newValue)
                    characterRepository.update(avatarImgUpdate)
                }
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
        _egirTG.value = character.raidPhaseInfo.egirTotalGold
        _ablreshudTG2.value = character.raidPhaseInfo.abrel2TotalGold
        _mordumTG.value = character.raidPhaseInfo.mordumTotalGold
        _eventTG.value = character.raidPhaseInfo.eventTotalGold

        _maxGold.value = character.weeklyGold
        _remainGold.value = _maxGold.value - _totalGold.value

        _extraGold.value = character.plusGold.toInt() - character.minusGold.toInt()

        _totalGoldPercentage.value = _maxGold.value + _extraGold.value.absoluteValue

        _progressPercentage.value = if (_maxGold.value != 0) 1 - (_remainGold.value.toFloat() / _totalGoldPercentage.value) else 0.0f
    }

    private fun getModel(character: Character) {
        _cmModel.value = CommandBossModel(character)
        _abModel.value = AbyssDungeonModel(character)
        _kzModel.value = KazerothRaidModel(character)
        _epModel.value = EpicRaidModel(character)
        _eventModel.value = EventRaidModel(character)
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

    fun kamenGoldCalc(nowPhase: Int, noReward: Boolean) {
        _kamenTG.value = when (nowPhase) {
            1 -> { _cmModel.value.kamen.onePhase.totalGold }
            2 -> { _cmModel.value.kamen.onePhase.totalGold + _cmModel.value.kamen.twoPhase.totalGold }
            3 -> { _cmModel.value.kamen.onePhase.totalGold + _cmModel.value.kamen.twoPhase.totalGold + _cmModel.value.kamen.threePhase.totalGold }
            4 -> {
                if (noReward) {
                    _cmModel.value.kamen.totalGold - _cmModel.value.kamen.fourPhase.totalGold
                } else {
                    _cmModel.value.kamen.totalGold
                }
            }
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

    fun abrelGoldCalc(nowPhase: Int, noReward: Boolean) {
        _abrelshudTG.value = when (nowPhase) {
            1 -> { _cmModel.value.abrelshud.onePhase.totalGold }
            2 -> { _cmModel.value.abrelshud.onePhase.totalGold + _cmModel.value.abrelshud.twoPhase.totalGold }
            3 -> { _cmModel.value.abrelshud.onePhase.totalGold + _cmModel.value.abrelshud.twoPhase.totalGold + _cmModel.value.abrelshud.threePhase.totalGold }
            4 -> {
                if (noReward) {
                    _cmModel.value.abrelshud.totalGold - _cmModel.value.abrelshud.fourPhase.totalGold
                } else {
                    _cmModel.value.abrelshud.totalGold
                }
            }
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
            3 -> { _abModel.value.ivoryTower.totalGold }
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

    fun egirGoldCalc(nowPhase: Int) {
        _egirTG.value = when (nowPhase) {
            1 -> { _kzModel.value.egir.onePhase.totalGold }
            2 -> { _kzModel.value.egir.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value,raidPhaseInfo = _character.value.raidPhaseInfo.copy(egirPhase = nowPhase, egirTotalGold = _egirTG.value))
            characterRepository.update(update)
        }
    }

    fun abrel2GoldCalc(nowPhase: Int) {
        _ablreshudTG2.value = when (nowPhase) {
            1 -> { _kzModel.value.abrelshud.onePhase.totalGold }
            2 -> { _kzModel.value.abrelshud.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value,raidPhaseInfo = _character.value.raidPhaseInfo.copy(abrel2Phase = nowPhase, abrel2TotalGold = _ablreshudTG2.value))
            characterRepository.update(update)
        }
    }

    fun mordumGoldCalc(nowPhase: Int) {
        _mordumTG.value = when(nowPhase) {
            1 -> { _kzModel.value.mordum.onePhase.totalGold }
            2 -> { _kzModel.value.mordum.twoPhase.totalGold }
            3 -> { _kzModel.value.mordum.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(mordumPhase = nowPhase, mordumTotalGold = _mordumTG.value))
            characterRepository.update(update)
        }
    }

    fun eventGoldCalc(nowPhase: Int) {
        _eventTG.value = when(nowPhase) {
            1 -> { _eventModel.value.event.onePhase.totalGold }
            else -> { 0 }
        }

        calcTotalGold()

        viewModelScope.launch(Dispatchers.IO) {
            val update = _character.value.copy(earnGold = _totalGold.value, raidPhaseInfo = _character.value.raidPhaseInfo.copy(eventPhase = nowPhase, eventTotalGold = _eventTG.value))
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

    fun raidImg(raidName: String) : Int {
        return when (raidName) {
            Raid.Name.VALTAN -> R.drawable.command_valtan
            Raid.Name.BIACKISS -> R.drawable.command_biackiss
            Raid.Name.KOUKU_SATON -> R.drawable.command_kouku
            Raid.Name.ABRELSHUD -> R.drawable.command_abrelshud
            Raid.Name.ILLIAKAN -> R.drawable.command_illiakan
            Raid.Name.KAMEN -> R.drawable.command_kamen
            Raid.Name.KAYANGEL -> R.drawable.abyss_dungeon_kayangel
            Raid.Name.IVORY_TOWER_LONG -> R.drawable.abyss_dungeon_ivory_tower
            Raid.Name.ECHIDNA -> R.drawable.kazeroth_echidna
            Raid.Name.BEHEMOTH -> R.drawable.epic_behemoth
            Raid.Name.EGIR -> R.drawable.kazeroth_egir
            Raid.Name.ABRELSHUD_2 -> R.drawable.kazeroth_abrelshud
            Raid.Name.MORDUM -> R.drawable.kazeroth_mordum
            Raid.Name.EVENT_RAID -> R.drawable.event_kamen
            else -> R.drawable.kazeroth_echidna
        }
    }
}
