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
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {
    var behemothRotated by remember { mutableStateOf(false) }
    val behemothRotaR by animateFloatAsState(
        targetValue = if (behemothRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(text = "베히모스")
            Checkbox(
                checked = viewModel.beheCheck,
                onCheckedChange = {
                    viewModel.onBeheCheck()
                }
            )
        }
    }

    if (viewModel.beheCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = behemothRotaR
                    cameraDistance = 8 * density
                }
                .clickable { behemothRotated = !behemothRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!behemothRotated) {
                Image(
                    painter = painterResource(id = R.drawable.epic_behemoth),
                    contentDescription = "베히모스"
                )
            } else {
                TwoPhaseBoss(
                    rotaR = behemothRotaR,

                    name = viewModel.behemoth.name,
                    totalGold = viewModel.behemoth.totalGold,

                    phaseOneLevel = viewModel.behemoth.onePhase.level,
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
        }
    }
}