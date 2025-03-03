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
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.ThreePhase
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.TwoPhase
import com.hongmyeoun.goldcalc.viewModel.homework.KazerothRaidVM

@Composable
fun Kazeroth(viewModel: KazerothRaidVM) {

    var echiRoatated by remember { mutableStateOf(false) }
    val echiRotaR by animateFloatAsState(
        targetValue = if (echiRoatated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    var egirRoatated by remember { mutableStateOf(false) }
    val egirRotaR by animateFloatAsState(
        targetValue = if (egirRoatated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    var abrelRoatated by remember { mutableStateOf(false) }
    val abrelRotaR by animateFloatAsState(
        targetValue = if (abrelRoatated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    var mordumRotated by remember { mutableStateOf(false) }
    val mordumRotaR by animateFloatAsState(
        targetValue = if (mordumRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidCheckBox(
            name = Raid.Name.ECHIDNA,
            modifier = modifier,
            checked = viewModel.echiCheck,
            onCheckedChange = { viewModel.onEchiCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.EGIR,
            modifier = modifier,
            checked = viewModel.egirCheck,
            onCheckedChange = { viewModel.onEgirCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.ABRELSHUD_2,
            modifier = modifier,
            checked = viewModel.abrelCheck,
            onCheckedChange = { viewModel.onAbrelCheck() }
        )

        RaidCheckBox(
            name = Raid.Name.MORDUM,
            modifier = modifier,
            checked = viewModel.mordumCheck,
            onCheckedChange = { viewModel.onKamenCheck() }
        )
    }

    if (viewModel.echiCheck) {
        RaidCard(
            bossImg = R.drawable.kazeroth_echidna,
            isRotated = echiRoatated,
            rotaR = echiRotaR,
            onClick = { echiRoatated = !echiRoatated },
            phaseCard = {
                TwoPhase(
                    rotaR = echiRotaR,

                    name = viewModel.echidna.name,
                    raidBossImg = R.drawable.logo_echidna,
                    totalGold = viewModel.echidna.totalGold,

                    phaseOneLevel = viewModel.echidna.onePhase.level,
                    phaseOneGold = viewModel.echidna.onePhase.totalGold,
                    phaseOneSMC = viewModel.echidna.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.echidna.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.echidna.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.echidna.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.echidna.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.echidna.twoPhase.level,
                    phaseTwoGold = viewModel.echidna.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.echidna.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.echidna.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.echidna.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.echidna.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.echidna.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )
            }
        )
    }

    if (viewModel.egirCheck) {
        RaidCard(
            bossImg = R.drawable.kazeroth_egir,
            isRotated = egirRoatated,
            rotaR = egirRotaR,
            onClick = { egirRoatated = !egirRoatated },
            phaseCard = {
                TwoPhase(
                    rotaR = egirRotaR,

                    name = viewModel.egir.name,
                    raidBossImg = R.drawable.logo_egir,
                    totalGold = viewModel.egir.totalGold,

                    phaseOneLevel = viewModel.egir.onePhase.level,
                    phaseOneGold = viewModel.egir.onePhase.totalGold,
                    phaseOneSMC = viewModel.egir.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.egir.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.egir.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.egir.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.egir.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.egir.twoPhase.level,
                    phaseTwoGold = viewModel.egir.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.egir.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.egir.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.egir.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.egir.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.egir.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )
            }
        )
    }

    if (viewModel.abrelCheck) {
        RaidCard(
            bossImg = R.drawable.kazeroth_abrelshud,
            isRotated = abrelRoatated,
            rotaR = abrelRotaR,
            onClick = { abrelRoatated = !abrelRoatated },
            phaseCard = {
                TwoPhase(
                    rotaR = abrelRotaR,

                    name = viewModel.abrelshud2.name,
                    raidBossImg = R.drawable.logo_abrelshud2,
                    totalGold = viewModel.abrelshud2.totalGold,

                    phaseOneLevel = viewModel.abrelshud2.onePhase.level,
                    phaseOneGold = viewModel.abrelshud2.onePhase.totalGold,
                    phaseOneSMC = viewModel.abrelshud2.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.abrelshud2.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.abrelshud2.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.abrelshud2.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud2.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.abrelshud2.twoPhase.level,
                    phaseTwoGold = viewModel.abrelshud2.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.abrelshud2.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.abrelshud2.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.abrelshud2.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.abrelshud2.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud2.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )
            }
        )
    }

    if (viewModel.mordumCheck) {
        RaidCard(
            bossImg = R.drawable.kazeroth_mordum,
            isRotated = mordumRotated,
            rotaR = mordumRotaR,
            onClick = { mordumRotated = !mordumRotated },
            phaseCard = {
                ThreePhase(
                    rotaR = mordumRotaR,

                    name = viewModel.mordum.name,
                    raidBossImg = R.drawable.logo_mordum,
                    totalGold = viewModel.mordum.totalGold,

                    phaseOneLevel = viewModel.mordum.onePhase.level,
                    phaseOneGold = viewModel.mordum.onePhase.totalGold,
                    phaseOneSMC = viewModel.mordum.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.mordum.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.mordum.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.mordum.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.mordum.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.mordum.twoPhase.level,
                    phaseTwoGold = viewModel.mordum.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.mordum.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.mordum.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.mordum.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.mordum.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.mordum.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.mordum.threePhase.level,
                    phaseThreeGold = viewModel.mordum.threePhase.totalGold,
                    phaseThreeSMC = viewModel.mordum.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.mordum.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.mordum.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.mordum.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.mordum.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )
            }
        )
    }

}