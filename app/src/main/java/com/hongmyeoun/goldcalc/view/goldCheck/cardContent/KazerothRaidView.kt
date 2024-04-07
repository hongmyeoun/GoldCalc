package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun KazerothRaid(viewModel: KazerothRaidVM) {

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
        twoPhaseBoss(
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