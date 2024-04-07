package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {
    twoPhaseBoss(
        name = viewModel.behemoth.name,
        totalGold = viewModel.totalGold,

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