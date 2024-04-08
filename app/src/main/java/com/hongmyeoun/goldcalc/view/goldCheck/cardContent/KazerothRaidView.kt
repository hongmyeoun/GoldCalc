package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
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

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(text = "에키드나")
            Checkbox(
                checked = viewModel.echiCheck,
                onCheckedChange = {
                    viewModel.onEchiCheck()
                }
            )
        }
    }

    if (viewModel.echiCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = rotaR
                    cameraDistance = 8 * density
                }
                .clickable { roatated = !roatated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!roatated) {
                Image(
                    painter = painterResource(id = R.drawable.kazeroth_echidna),
                    contentDescription = "에키드나"
                )
            } else {
                TwoPhaseBoss(
                    rotaR = rotaR,

                    name = viewModel.echidna.name,
                    totalGold = viewModel.echidna.totalGold,

                    phaseOneLevel = viewModel.echidna.onePhase.level,
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
        }
    }

}