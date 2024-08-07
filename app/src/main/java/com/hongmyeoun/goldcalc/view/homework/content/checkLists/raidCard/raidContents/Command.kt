package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCard
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCheckBox
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCheckLists
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.FourPhase
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.FourPhaseLastHard
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.ThreePhase
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.TwoPhase
import com.hongmyeoun.goldcalc.viewModel.homework.CommandBossVM

@Composable
fun Command(
    viewModel: CommandBossVM,
) {
    var valtanRotated by remember { mutableStateOf(false) }
    var biackissRotated by remember { mutableStateOf(false) }
    var koukuSatonRotated by remember { mutableStateOf(false) }
    var abrelshudRotated by remember { mutableStateOf(false) }
    var illiakanRotated by remember { mutableStateOf(false) }
    var kamenRotated by remember { mutableStateOf(false) }

    val valtanRotaR by animateFloatAsState(
        targetValue = if (valtanRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )
    val biackissRotaR by animateFloatAsState(
        targetValue = if (biackissRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )
    val koukuSatonRotaR by animateFloatAsState(
        targetValue = if (koukuSatonRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )
    val abrelshudRotaR by animateFloatAsState(
        targetValue = if (abrelshudRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )
    val illiakanRotaR by animateFloatAsState(
        targetValue = if (illiakanRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )
    val kamenRotaR by animateFloatAsState(
        targetValue = if (kamenRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    RaidCheckLists(maxItem = 3) { modifier ->
        RaidCheckBox(
            name = Raid.Name.VALTAN,
            modifier = modifier,
            checked = viewModel.valtanCheck,
            onCheckedChange = { viewModel.onValtanCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.BIACKISS_SHORT,
            modifier = modifier,
            checked = viewModel.biaCheck,
            onCheckedChange = { viewModel.onBiaCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.KOUKU_SATON_SHORT,
            modifier = modifier,
            checked = viewModel.koukuCheck,
            onCheckedChange = { viewModel.onKoukuCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.ABRELSHUD_SHORT,
            modifier = modifier,
            checked = viewModel.abreCheck,
            onCheckedChange = { viewModel.onAbreCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.ILLIAKAN_SHORT,
            modifier = modifier,
            checked = viewModel.illiCheck,
            onCheckedChange = { viewModel.onIlliCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.KAMEN,
            modifier = modifier,
            checked = viewModel.kamenCheck,
            onCheckedChange = { viewModel.onKamenCheck() }
        )
    }

    if (viewModel.valtanCheck) {
        RaidCard(
            bossImg = R.drawable.command_valtan,
            isRotated = valtanRotated,
            rotaR = valtanRotaR,
            onClick = { valtanRotated = !valtanRotated },
            phaseCard = {
                TwoPhase(
                    rotaR = valtanRotaR,

                    name = viewModel.valtan.name,
                    raidBossImg = R.drawable.logo_valtan,
                    totalGold = viewModel.valtan.totalGold,

                    phaseOneLevel = viewModel.valtan.onePhase.level,
                    phaseOneGold = viewModel.valtan.onePhase.totalGold,
                    phaseOneSMC = viewModel.valtan.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.valtan.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.valtan.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.valtan.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.valtan.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.valtan.twoPhase.level,
                    phaseTwoGold = viewModel.valtan.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.valtan.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.valtan.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.valtan.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.valtan.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.valtan.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )
            }
        )
    }


    if (viewModel.biaCheck) {
        RaidCard(
            bossImg = R.drawable.command_biackiss,
            isRotated = biackissRotated,
            rotaR = biackissRotaR,
            onClick = { biackissRotated = !biackissRotated },
            phaseCard = {
                TwoPhase(
                    rotaR = biackissRotaR,

                    name = viewModel.biackiss.name,
                    raidBossImg = R.drawable.logo_biackiss,
                    totalGold = viewModel.biackiss.totalGold,

                    phaseOneLevel = viewModel.biackiss.onePhase.level,
                    phaseOneGold = viewModel.biackiss.onePhase.totalGold,
                    phaseOneSMC = viewModel.biackiss.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.biackiss.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.biackiss.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.biackiss.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.biackiss.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.biackiss.twoPhase.level,
                    phaseTwoGold = viewModel.biackiss.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.biackiss.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.biackiss.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.biackiss.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.biackiss.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.biackiss.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )
            }
        )
    }

    if (viewModel.koukuCheck) {
        RaidCard(
            bossImg = R.drawable.command_kouku,
            isRotated = koukuSatonRotated,
            rotaR = koukuSatonRotaR,
            onClick = { koukuSatonRotated = !koukuSatonRotated },
            phaseCard = {
                ThreePhase(
                    rotaR = koukuSatonRotaR,

                    name = viewModel.koukuSaton.name,
                    raidBossImg = R.drawable.logo_saton,
                    totalGold = viewModel.koukuSaton.totalGold,

                    phaseOneLevel = viewModel.koukuSaton.onePhase.level,
                    phaseOneGold = viewModel.koukuSaton.onePhase.totalGold,
                    phaseOneSMC = viewModel.koukuSaton.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.koukuSaton.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.koukuSaton.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.koukuSaton.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.koukuSaton.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.koukuSaton.twoPhase.level,
                    phaseTwoGold = viewModel.koukuSaton.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.koukuSaton.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.koukuSaton.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.koukuSaton.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.koukuSaton.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.koukuSaton.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.koukuSaton.threePhase.level,
                    phaseThreeGold = viewModel.koukuSaton.threePhase.totalGold,
                    phaseThreeSMC = viewModel.koukuSaton.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.koukuSaton.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.koukuSaton.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.koukuSaton.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.koukuSaton.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )
            }
        )
    }

    if (viewModel.abreCheck) {
        RaidCard(
            bossImg = R.drawable.command_abrelshud,
            isRotated = abrelshudRotated,
            rotaR = abrelshudRotaR,
            onClick = { abrelshudRotated = !abrelshudRotated },
            phaseCard = {
                FourPhase(
                    rotaR = abrelshudRotaR,

                    name = viewModel.abrelshud.name,
                    raidBossImg = R.drawable.logo_abrelshud,
                    totalGold = viewModel.abrelshud.totalGold,

                    phaseOneLevel = viewModel.abrelshud.onePhase.level,
                    phaseOneGold = viewModel.abrelshud.onePhase.totalGold,
                    phaseOneSMC = viewModel.abrelshud.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.abrelshud.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.abrelshud.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.abrelshud.twoPhase.level,
                    phaseTwoGold = viewModel.abrelshud.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.abrelshud.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.abrelshud.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.abrelshud.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.abrelshud.threePhase.level,
                    phaseThreeGold = viewModel.abrelshud.threePhase.totalGold,
                    phaseThreeSMC = viewModel.abrelshud.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.abrelshud.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.abrelshud.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseFourLevel = viewModel.abrelshud.fourPhase.level,
                    phaseFourGold = viewModel.abrelshud.fourPhase.totalGold,
                    phaseFourSMC = viewModel.abrelshud.fourPhase.seeMoreCheck,
                    phaseFourCC = viewModel.abrelshud.fourPhase.clearCheck,
                    onFourPhaseLevelClicked = {
                        viewModel.abrelshud.fourPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onFourPhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.fourPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onFourPhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.fourPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )
            }
        )
    }

    if (viewModel.illiCheck) {
        RaidCard(
            bossImg = R.drawable.command_illiakan,
            isRotated = illiakanRotated,
            rotaR = illiakanRotaR,
            onClick = { illiakanRotated = !illiakanRotated },
            phaseCard = {
                ThreePhase(
                    rotaR = illiakanRotaR,

                    name = viewModel.illiakan.name,
                    raidBossImg = R.drawable.logo_illiakan,
                    totalGold = viewModel.illiakan.totalGold,

                    phaseOneLevel = viewModel.illiakan.onePhase.level,
                    phaseOneGold = viewModel.illiakan.onePhase.totalGold,
                    phaseOneSMC = viewModel.illiakan.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.illiakan.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.illiakan.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.illiakan.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.illiakan.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.illiakan.twoPhase.level,
                    phaseTwoGold = viewModel.illiakan.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.illiakan.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.illiakan.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.illiakan.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.illiakan.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.illiakan.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.illiakan.threePhase.level,
                    phaseThreeGold = viewModel.illiakan.threePhase.totalGold,
                    phaseThreeSMC = viewModel.illiakan.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.illiakan.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.illiakan.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.illiakan.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.illiakan.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )
            }
        )
    }

    if (viewModel.kamenCheck) {
        RaidCard(
            bossImg = R.drawable.command_kamen,
            isRotated = kamenRotated,
            rotaR = kamenRotaR,
            onClick = { kamenRotated = !kamenRotated },
            phaseCard = {
                FourPhaseLastHard(
                    rotaR = kamenRotaR,

                    name = viewModel.kamen.name,
                    raidBossImg = R.drawable.logo_kamen,
                    totalGold = viewModel.kamen.totalGold,

                    phaseOneLevel = viewModel.kamen.onePhase.level,
                    phaseOneGold = viewModel.kamen.onePhase.totalGold,
                    phaseOneSMC = viewModel.kamen.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.kamen.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.kamen.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.kamen.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.kamen.twoPhase.level,
                    phaseTwoGold = viewModel.kamen.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.kamen.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.kamen.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.kamen.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.kamen.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.kamen.threePhase.level,
                    phaseThreeGold = viewModel.kamen.threePhase.totalGold,
                    phaseThreeSMC = viewModel.kamen.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.kamen.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.kamen.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.kamen.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseFourGold = viewModel.kamen.fourPhase.totalGold,
                    phaseFourSMC = viewModel.kamen.fourPhase.seeMoreCheck,
                    phaseFourCC = viewModel.kamen.fourPhase.clearCheck,
                    onFourPhaseClearCheckBoxChecked = {
                        viewModel.kamen.fourPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onFourPhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.fourPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )
            }
        )
    }
}

