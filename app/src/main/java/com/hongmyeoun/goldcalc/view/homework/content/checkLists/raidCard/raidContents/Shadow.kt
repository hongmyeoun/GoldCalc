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
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.TwoPhase
import com.hongmyeoun.goldcalc.viewModel.homework.ShadowRaidVM

@Composable
fun Shadow(viewModel: ShadowRaidVM) {

    var sercaRoatated by remember { mutableStateOf(false) }
    val sercaRotaR by animateFloatAsState(
        targetValue = if (sercaRoatated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidCheckBox(
            name = Raid.Name.SERCA,
            modifier = modifier,
            checked = viewModel.sercaCheck,
            onCheckedChange = { viewModel.onSercaCheck() }
        )
    }

    if (viewModel.sercaCheck) {
        RaidCard(
            bossImg = R.drawable.kazeroth_echidna,
            isRotated = sercaRoatated,
            rotaR = sercaRotaR,
            onClick = { sercaRoatated = !sercaRoatated },
            phaseCard = {
                TwoPhase(
                    rotaR = sercaRotaR,

                    name = viewModel.serca.name,
                    raidBossImg = R.drawable.logo_echidna,
                    totalGold = viewModel.serca.totalGold,

                    phaseOneLevel = viewModel.serca.onePhase.level,
                    phaseOneGold = viewModel.serca.onePhase.totalGold,
                    phaseOneSMC = viewModel.serca.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.serca.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.serca.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.serca.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.serca.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.serca.twoPhase.level,
                    phaseTwoGold = viewModel.serca.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.serca.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.serca.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.serca.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.serca.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.serca.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )
            }
        )
    }
}