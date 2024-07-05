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
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.TwoPhaseNoHard
import com.hongmyeoun.goldcalc.viewModel.homework.EpicRaidVM

@Composable
fun Epic(viewModel: EpicRaidVM) {
    var behemothRotated by remember { mutableStateOf(false) }
    val behemothRotaR by animateFloatAsState(
        targetValue = if (behemothRotated) 180f else 0f,
        animationSpec = tween(500),
        label = "회전 애니메이션"
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidCheckBox(
            name = "베히모스",
            modifier = modifier,
            checked = viewModel.beheCheck,
            onCheckedChange = { viewModel.onBeheCheck() }
        )
    }

    if (viewModel.beheCheck) {
        RaidCard(
            bossImg = R.drawable.epic_behemoth,
            isRotated = behemothRotated,
            rotaR = behemothRotaR,
            onClick = { behemothRotated = !behemothRotated },
            phaseCard = {
                TwoPhaseNoHard(
                    rotaR = behemothRotaR,

                    name = viewModel.behemoth.name,
                    raidBossImg = R.drawable.logo_behemoth,
                    totalGold = viewModel.behemoth.totalGold,

                    phaseOneGold = viewModel.behemoth.onePhase.totalGold,
                    phaseOneSMC = viewModel.behemoth.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.behemoth.onePhase.clearCheck,
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.behemoth.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.behemoth.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoGold = viewModel.behemoth.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.behemoth.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.behemoth.twoPhase.clearCheck,
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.behemoth.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.behemoth.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )

            }
        )
    }

}