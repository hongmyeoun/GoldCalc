package com.hongmyeoun.goldcalc.view.setting.cardContent

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.view.setting.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.EpicRaidVM

@Composable
fun EpicRaid(viewModel: EpicRaidVM) {
    val behemoth = twoPhaseBoss(
        name = viewModel.behe,
        seeMoreGold = viewModel.beheSMG,
        clearGold = viewModel.beheCG
    )

    viewModel.sumGold(behemoth)
}