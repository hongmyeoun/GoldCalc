package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {
    val behemoth = twoPhaseBoss(
        name = viewModel.behe,
        seeMoreGold = viewModel.beheSMG,
        clearGold = viewModel.beheCG
    )

    viewModel.sumGold(behemoth)
}