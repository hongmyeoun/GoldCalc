package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCheckBox
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCard
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCheckLists
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.TwoPhase
import com.hongmyeoun.goldcalc.viewModel.homework.KazerothRaidVM

@Composable
fun Kazeroth(viewModel: KazerothRaidVM) {

    var roatated by remember { mutableStateOf(false) }
    val rotaR by animateFloatAsState(
        targetValue = if (roatated) 180f else 0f,
        animationSpec = tween(500),
        label = "회전 애니메이션"
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidCheckBox(
            name = "에키드나",
            modifier = modifier,
            checked = viewModel.echiCheck,
            onCheckedChange = { viewModel.onEchiCheck() }
        )
    }

    if (viewModel.echiCheck) {
        RaidCard(
            bossImg = R.drawable.kazeroth_echidna,
            isRotated = roatated,
            rotaR = rotaR,
            onClick = { roatated = !roatated },
            phaseCard = {
                TwoPhase(
                    rotaR = rotaR,

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
}