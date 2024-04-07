package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun KazerothRaid(viewModel: KazerothRaidVM) {

    LaunchedEffect(viewModel.echidnaTG) {
        viewModel.sumGold()
    }

    twoPhaseBoss(
        name = viewModel.echidna.name,
        totalGold = viewModel.totalGold,

        phaseOneLevel = viewModel.echidna.onePhase.level,
        phaseOneSMC = viewModel.echidna.onePhase.seeMoreCheck,
        phaseOneCC = viewModel.echidna.onePhase.clearCheck,
        onOnePhaseLevelClicked = {
            viewModel.echidna.onePhase.onLevelClicked()
            viewModel.echidnaTotal()
        },
        onOnePhaseClearCheckBoxChecked = {
            viewModel.echidna.onePhase.onClearCheckBoxClicked(it)
            viewModel.echidnaTotal()
        },
        onOnePhaseSeeMoreCheckBoxChecked = {
            viewModel.echidna.onePhase.onSeeMoreCheckBoxClicked(it)
            viewModel.echidnaTotal()
        },

        phaseTwoLevel = viewModel.echidna.twoPhase.level,
        phaseTwoSMC = viewModel.echidna.twoPhase.seeMoreCheck,
        phaseTwoCC = viewModel.echidna.twoPhase.clearCheck,
        onTwoPhaseLevelClicked = {
            viewModel.echidna.twoPhase.onLevelClicked()
            viewModel.echidnaTotal()
        },
        onTwoPhaseClearCheckBoxChecked = {
            viewModel.echidna.twoPhase.onClearCheckBoxClicked(it)
            viewModel.echidnaTotal()
        },
        onTwoPhaseSeeMoreCheckBoxChecked = {
            viewModel.echidna.twoPhase.onSeeMoreCheckBoxClicked(it)
            viewModel.echidnaTotal()
        },
    )

}