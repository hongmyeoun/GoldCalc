package com.hongmyeoun.goldcalc.view.setting.cardContent

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.view.setting.fourPhaseBoss
import com.hongmyeoun.goldcalc.view.setting.threePhaseBoss
import com.hongmyeoun.goldcalc.viewModel.AbyssDungeonVM

@Composable
fun AbyssDungeon(viewModel: AbyssDungeonVM){
    val kayangel = threePhaseBoss(
        name = viewModel.kayang,
        seeMoreGold = viewModel.kayangSMG,
        clearGold = viewModel.kayangCG
    )
    val ivoryTower = fourPhaseBoss(
        name = viewModel.ivT,
        seeMoreGold = viewModel.ivTSMG,
        clearGold = viewModel.ivTCG
    )
    viewModel.sumGold(kayangel, ivoryTower)
}