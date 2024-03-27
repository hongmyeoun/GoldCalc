package com.hongmyeoun.goldcalc.view.setting.cardContent

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.view.setting.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.KazerothRaidVM

@Composable
fun KazerothRaid(viewModel: KazerothRaidVM) {
    val echidna = twoPhaseBoss(
        name = viewModel.echi,
        seeMoreGold = viewModel.echiSMG,
        clearGold = viewModel.echiCG
    )

    viewModel.sumGold(echidna)
}