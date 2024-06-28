package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.goldCheck.TwoPhaseBossNoHard
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {
    var behemothRotated by remember { mutableStateOf(false) }
    val behemothRotaR by animateFloatAsState(
        targetValue = if (behemothRotated) 180f else 0f,
        animationSpec = tween(500)
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidBossCheck(
            name = "베히모스",
            modifier = modifier,
            checked = viewModel.beheCheck,
            onCheckedChange = { viewModel.onBeheCheck() }
        )
    }

    if (viewModel.beheCheck) {
        RaidCardUI(
            bossImg = R.drawable.epic_behemoth,
            isRotated = behemothRotated,
            rotaR = behemothRotaR,
            onClick = { behemothRotated = !behemothRotated },
            phaseCard = {
                TwoPhaseBossNoHard(
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