package com.hongmyeoun.goldcalc.viewModel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.AbyssDungeonModel
import com.hongmyeoun.goldcalc.model.goldCheck.CommandBossModel
import com.hongmyeoun.goldcalc.model.goldCheck.EpicRaidModel
import com.hongmyeoun.goldcalc.model.goldCheck.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.Character
import com.hongmyeoun.goldcalc.model.roomDB.Phase

class CharacterCardVM(val character: Character) : ViewModel() {
    private val epModel = EpicRaidModel(character)
    private val kzModel = KazerothRaidModel(character)
    private val cmModel = CommandBossModel(character)
    private val abModel = AbyssDungeonModel(character)

    var totalGold by mutableStateOf(0)
    private val maxGold =
        epModel.behemoth.totalGold + kzModel.echidna.totalGold + abModel.ivoryTower.totalGold + abModel.kayangel.totalGold + cmModel.kamen.totalGold + cmModel.illiakan.totalGold + cmModel.abrelshud.totalGold + cmModel.koukuSaton.totalGold + cmModel.biackiss.totalGold + cmModel.valtan.totalGold
    var progressPersentage by mutableStateOf(0.0f)
        private set

    init {
        updateProgressPercentage()
    }

    fun updateProgressPercentage() {
        progressPersentage = if (maxGold != 0) totalGold.toFloat() / maxGold else 0.0f
    }

    fun calcTotalGold() {
        totalGold = behemothTG + echidnaTG + kamenTG + ivoryTowerTG + illiakanTG + kayangelTG + abrelshudTG + koukuSatonTG + biackissTG + valtanTG
        updateProgressPercentage()
    }

    private val raidList = character.checkList

    val behemoth = raidList.epic[0].phases
    val beheCheck = behemoth[0].isClear
    private var behemothTG by mutableStateOf(0)

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
    }

    val echidna = raidList.kazeroth[0].phases
    val echiCheck = echidna[0].isClear
    private var echidnaTG by mutableStateOf(0)

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
    }

    val kamen = raidList.command[5].phases
    val kamenCehck = kamen[0].isClear
    private var kamenTG by mutableStateOf(0)

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
    }

    val ivoryTower = raidList.abyssDungeon[1].phases
    val ivoryCheck = ivoryTower[0].isClear
    private var ivoryTowerTG by mutableStateOf(0)

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
    }

    val illiakan = raidList.command[4].phases
    val illiCheck = illiakan[0].isClear
    private var illiakanTG by mutableStateOf(0)

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
    }

    val kayangel = raidList.abyssDungeon[0].phases
    val kayanCheck = kayangel[0].isClear
    private var kayangelTG by mutableStateOf(0)

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
    }

    val abrelshud = raidList.command[3].phases
    val abrelCheck = abrelshud[0].isClear
    private var abrelshudTG by mutableStateOf(0)

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
    }

    val koukuSaton = raidList.command[2].phases
    val koukuCheck = koukuSaton[0].isClear
    private var koukuSatonTG by mutableStateOf(0)

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
    }

    val biackiss = raidList.command[1].phases
    val biaCheck = biackiss[0].isClear
    private var biackissTG by mutableStateOf(0)

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
    }

    val valtan = raidList.command[0].phases
    val valtanCheck = valtan[0].isClear
    private var valtanTG by mutableStateOf(0)

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

class GoldContentStateVM : ViewModel() {
    var nowPhase by mutableStateOf(0)

    fun onClicked(phase: Int): Int {
        if (nowPhase == phase) {
            nowPhase = 0
        } else {
            nowPhase += 1
        }
        return nowPhase
    }
}

