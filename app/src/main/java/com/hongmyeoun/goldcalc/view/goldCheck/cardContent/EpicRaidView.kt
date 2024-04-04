package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {

    LaunchedEffect(viewModel.behemothTG) {
        viewModel.sumGold()
    }

    twoPhaseBoss(
        name = viewModel.behe,
        seeMoreGold = viewModel.beheSMG,
        clearGold = viewModel.beheCG,
        totalGold = viewModel.behemothTG,
        onUpdateTotalGoldOnePhase = { update ->
            viewModel.behemothOnePhase(update)
        },
        onUpdateTotalGoldTwoPhase = { update ->
            viewModel.behemothTwoPhase(update)
        }
    )


}