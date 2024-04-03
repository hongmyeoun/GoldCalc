package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun KazerothRaid(viewModel: KazerothRaidVM) {
    val echidna = twoPhaseBoss(
        name = viewModel.echi,
        seeMoreGold = viewModel.echiSMG,
        clearGold = viewModel.echiCG
    )

    LaunchedEffect(echidna) {
        viewModel.sumGold(echidna)
    }
}