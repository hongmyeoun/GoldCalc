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
import kotlinx.coroutines.launch

class CharacterCardVM(val character: Character, val characterRepository: CharacterRepository) : ViewModel() {
    private val epModel = EpicRaidModel(character)
    private val kzModel = KazerothRaidModel(character)
    private val cmModel = CommandBossModel(character)
    private val abModel = AbyssDungeonModel(character)

    var totalGold by mutableStateOf(0)
    private val maxGold =
        epModel.behemoth.totalGold + kzModel.echidna.totalGold + abModel.ivoryTower.totalGold + abModel.kayangel.totalGold + cmModel.kamen.totalGold + cmModel.illiakan.totalGold + cmModel.abrelshud.totalGold + cmModel.koukuSaton.totalGold + cmModel.biackiss.totalGold + cmModel.valtan.totalGold
    var progressPersentage by mutableStateOf(0.0f)
        private set

    fun updateProgressPercentage() {
        progressPersentage = if (maxGold != 0) totalGold.toFloat() / maxGold else 0.0f
    }

    fun updateEarnGold(earnGold: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newEarnGold = character.copy(earnGold = earnGold)
            characterRepository.update(newEarnGold)
        }
    }

    fun calcTotalGold() {
        totalGold = behemothTG + echidnaTG + kamenTG + ivoryTowerTG + illiakanTG + kayangelTG + abrelshudTG + koukuSatonTG + biackissTG + valtanTG
        updateEarnGold(totalGold)
        updateProgressPercentage()
    }

    private val raidList = character.checkList

    val behemoth = raidList.epic[0].phases
    val beheCheck = behemoth[0].isClear
    private var behemothTG by mutableStateOf(character.raidPhaseInfo.behemothTotalGold)
    val behemothPhase = character.raidPhaseInfo.behemothPhase

    fun beheGoldCalc(nowPhase: Int) {
        behemothTG = when (nowPhase) {
            1 -> {
                epModel.behemoth.onePhase.totalGold
            }

            2 -> {
                epModel.behemoth.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(behemothPhase = nowPhase, behemothTotalGold = behemothTG))
            characterRepository.update(update)
        }
    }

    val echidna = raidList.kazeroth[0].phases
    val echiCheck = echidna[0].isClear
    private var echidnaTG by mutableStateOf(character.raidPhaseInfo.echidnaTotalGold)
    val echidnaPhase = character.raidPhaseInfo.echidnaPhase

    fun echiGoldCalc(nowPhase: Int) {
        echidnaTG = when (nowPhase) {
            1 -> {
                kzModel.echidna.onePhase.totalGold
            }

            2 -> {
                kzModel.echidna.totalGold
            }
            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(echidnaPhase = nowPhase, echidnaTotalGold = echidnaTG))
            characterRepository.update(update)
        }
    }

    val kamen = raidList.command[5].phases
    val kamenCehck = kamen[0].isClear
    private var kamenTG by mutableStateOf(character.raidPhaseInfo.kamenTotalGold)
    val kamenPhase = character.raidPhaseInfo.kamenPhase

    fun kamenGoldCalc(nowPhase: Int) {
        kamenTG = when (nowPhase) {
            1 -> {
                cmModel.kamen.onePhase.totalGold
            }

            2 -> {
                cmModel.kamen.onePhase.totalGold + cmModel.kamen.twoPhase.totalGold
            }

            3 -> {
                cmModel.kamen.onePhase.totalGold + cmModel.kamen.twoPhase.totalGold + cmModel.kamen.threePhase.totalGold
            }

            4 -> {
                cmModel.kamen.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(kamenPhase = nowPhase, kamenTotalGold = kamenTG))
            characterRepository.update(update)
        }
    }

    val ivoryTower = raidList.abyssDungeon[1].phases
    val ivoryCheck = ivoryTower[0].isClear
    private var ivoryTowerTG by mutableStateOf(character.raidPhaseInfo.ivoryTotalGold)
    val ivoryPhase = character.raidPhaseInfo.ivoryPhase


    fun iTGoldCalc(nowPhase: Int) {
        ivoryTowerTG = when (nowPhase) {
            1 -> {
                abModel.ivoryTower.onePhase.totalGold
            }

            2 -> {
                abModel.ivoryTower.onePhase.totalGold + abModel.ivoryTower.twoPhase.totalGold
            }

            3 -> {
                abModel.ivoryTower.onePhase.totalGold + abModel.ivoryTower.twoPhase.totalGold + abModel.ivoryTower.threePhase.totalGold
            }

            4 -> {
                abModel.ivoryTower.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(ivoryPhase = nowPhase, ivoryTotalGold = ivoryTowerTG))
            characterRepository.update(update)
        }
    }



    val illiakan = raidList.command[4].phases
    val illiCheck = illiakan[0].isClear
    private var illiakanTG by mutableStateOf(character.raidPhaseInfo.illiakanTotalGold)
    val illiakanPhase = character.raidPhaseInfo.illiakanPhase


    fun illiGoldCalc(nowPhase: Int) {
        illiakanTG = when (nowPhase) {
            1 -> {
                cmModel.illiakan.onePhase.totalGold
            }

            2 -> {
                cmModel.illiakan.onePhase.totalGold + cmModel.illiakan.twoPhase.totalGold
            }

            3 -> {
                cmModel.illiakan.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(illiakanPhase = nowPhase, illiakanTotalGold = illiakanTG))
            characterRepository.update(update)
        }

    }

    val kayangel = raidList.abyssDungeon[0].phases
    val kayanCheck = kayangel[0].isClear
    private var kayangelTG by mutableStateOf(character.raidPhaseInfo.kayangelTotalGold)
    val kayangelPhase = character.raidPhaseInfo.kayangelPhase


    fun kayanGoldCalc(nowPhase: Int) {
        kayangelTG = when (nowPhase) {
            1 -> {
                abModel.kayangel.onePhase.totalGold
            }

            2 -> {
                abModel.kayangel.onePhase.totalGold + abModel.kayangel.twoPhase.totalGold
            }

            3 -> {
                abModel.kayangel.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(kayangelPhase = nowPhase, kayangelTotalGold = kayangelTG))
            characterRepository.update(update)
        }
    }

    val abrelshud = raidList.command[3].phases
    val abrelCheck = abrelshud[0].isClear
    private var abrelshudTG by mutableStateOf(character.raidPhaseInfo.abrelTotalGold)
    val abrelPhase = character.raidPhaseInfo.abrelPhase


    fun abrelGoldCalc(nowPhase: Int) {
        abrelshudTG = when (nowPhase) {
            1 -> {
                cmModel.abrelshud.onePhase.totalGold
            }

            2 -> {
                cmModel.abrelshud.onePhase.totalGold + cmModel.abrelshud.twoPhase.totalGold
            }

            3 -> {
                cmModel.abrelshud.onePhase.totalGold + cmModel.abrelshud.twoPhase.totalGold + cmModel.abrelshud.threePhase.totalGold
            }

            4 -> {
                cmModel.abrelshud.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(abrelPhase = nowPhase, abrelTotalGold = abrelshudTG))
            characterRepository.update(update)
        }

    }

    val koukuSaton = raidList.command[2].phases
    val koukuCheck = koukuSaton[0].isClear
    private var koukuSatonTG by mutableStateOf(character.raidPhaseInfo.koukuTotalGold)
    val koukuPhase = character.raidPhaseInfo.koukuPhase


    fun kokuGoldCalc(nowPhase: Int) {
        koukuSatonTG = when (nowPhase) {
            1 -> {
                cmModel.koukuSaton.onePhase.totalGold
            }

            2 -> {
                cmModel.koukuSaton.onePhase.totalGold + cmModel.koukuSaton.twoPhase.totalGold
            }

            3 -> {
                cmModel.koukuSaton.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(koukuPhase = nowPhase, koukuTotalGold = koukuSatonTG))
            characterRepository.update(update)
        }
    }

    val biackiss = raidList.command[1].phases
    val biaCheck = biackiss[0].isClear
    private var biackissTG by mutableStateOf(character.raidPhaseInfo.biackissTotalGold)
    val biackissPhase = character.raidPhaseInfo.biackissPhase


    fun biaGoldCalc(nowPhase: Int) {
        biackissTG = when (nowPhase) {
            1 -> {
                cmModel.biackiss.onePhase.totalGold
            }

            2 -> {
                cmModel.biackiss.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(biackissPhase = nowPhase, biackissTotalGold = biackissTG))
            characterRepository.update(update)
        }

    }

    val valtan = raidList.command[0].phases
    val valtanCheck = valtan[0].isClear
    private var valtanTG by mutableStateOf(character.raidPhaseInfo.valtanTotalGold)
    val valtanPhase = character.raidPhaseInfo.valtanPhase

    fun valGoldCalc(nowPhase: Int) {
        valtanTG = when (nowPhase) {
            1 -> {
                cmModel.valtan.onePhase.totalGold
            }

            2 -> {
                cmModel.valtan.totalGold
            }

            else -> {
                0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val update = character.copy(raidPhaseInfo = character.raidPhaseInfo.copy(valtanPhase = nowPhase, valtanTotalGold = valtanTG))
            characterRepository.update(update)
        }
    }

    init {
        calcTotalGold()
        updateProgressPercentage()
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

