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
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun KazerothRaid(viewModel: KazerothRaidVM) {

    var roatated by remember { mutableStateOf(false) }
    val rotaR by animateFloatAsState(
        targetValue = if (roatated) 180f else 0f,
        animationSpec = tween(500)
    )

    Column(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RaidBossCheck(
                name = "에키드나",
                modifier = Modifier.weight(1f),
                checked = viewModel.echiCheck,
                onCheckedChange = { viewModel.onEchiCheck() }
            )
        }
    }

    RaidCardUI(
        bossImg = R.drawable.kazeroth_echidna,
        isCheck = viewModel.echiCheck,
        isRotated = roatated,
        rotaR = rotaR,
        onClick = { roatated = !roatated },
        phaseCard = {
            TwoPhaseBoss(
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