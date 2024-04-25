package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.goldCheck.TwoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {
    var behemothRotated by remember { mutableStateOf(false) }
    val behemothRotaR by animateFloatAsState(
        targetValue = if (behemothRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    Column(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RaidBossCheck(
                name = "베히모스",
                modifier = Modifier.weight(1f),
                checked = viewModel.beheCheck,
                onCheckedChange = { viewModel.onBeheCheck() }
            )
        }
    }

    RaidCardUI(
        bossImg = R.drawable.epic_behemoth,
        isCheck = viewModel.beheCheck,
        isRotated = behemothRotated,
        rotaR = behemothRotaR,
        onClick = { behemothRotated = !behemothRotated },
        phaseCard = {
            TwoPhaseBoss(
                rotaR = behemothRotaR,

                name = viewModel.behemoth.name,
                raidBossImg = R.drawable.logo_behemoth,
                totalGold = viewModel.behemoth.totalGold,

                phaseOneLevel = viewModel.behemoth.onePhase.level,
                phaseOneGold = viewModel.behemoth.onePhase.totalGold,
                phaseOneSMC = viewModel.behemoth.onePhase.seeMoreCheck,
                phaseOneCC = viewModel.behemoth.onePhase.clearCheck,
                onOnePhaseLevelClicked = {
                    viewModel.behemoth.onePhase.onLevelClicked()
                    viewModel.sumGold()
                },
                onOnePhaseClearCheckBoxChecked = {
                    viewModel.behemoth.onePhase.onClearCheckBoxClicked(it)
                    viewModel.sumGold()
                },
                onOnePhaseSeeMoreCheckBoxChecked = {
                    viewModel.behemoth.onePhase.onSeeMoreCheckBoxClicked(it)
                    viewModel.sumGold()
                },

                phaseTwoLevel = viewModel.behemoth.twoPhase.level,
                phaseTwoGold = viewModel.behemoth.twoPhase.totalGold,
                phaseTwoSMC = viewModel.behemoth.twoPhase.seeMoreCheck,
                phaseTwoCC = viewModel.behemoth.twoPhase.clearCheck,
                onTwoPhaseLevelClicked = {
                    viewModel.behemoth.twoPhase.onLevelClicked()
                    viewModel.sumGold()
                },
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