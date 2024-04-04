package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hongmyeoun.goldcalc.view.goldCheck.fourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.threePhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM

@Composable
fun AbyssDungeon(viewModel: AbyssDungeonVM) {

    LaunchedEffect(viewModel.kayangelTG, viewModel.ivoryTowerTG) {
        viewModel.sumGold()
    }

    threePhaseBoss(
        name = viewModel.kayang,
        seeMoreGold = viewModel.kayangSMG,
        clearGold = viewModel.kayangCG,
        totalGold = viewModel.kayangelTG,
        onUpdateTotalGoldOnePhase = { update ->
            viewModel.kayangelOnePhase(update)
        },
        onUpdateTotalGoldTwoPhase = { update ->
            viewModel.kayangelTwoPhase(update)
        },
        onUpdateTotalGoldThreePhase = { update ->
            viewModel.kayangelThreePhase(update)
        }
    )
    fourPhaseBoss(
        name = viewModel.ivT,
        seeMoreGold = viewModel.ivTSMG,
        clearGold = viewModel.ivTCG,
        totalGold = viewModel.ivoryTowerTG,
        onUpdateTotalGoldOnePhase = { update ->
            viewModel.ivoryTowerOnePhase(update)
        },
        onUpdateTotalGoldTwoPhase = { update ->
            viewModel.ivoryTowerTwoPhase(update)
        },
        onUpdateTotalGoldThreePhase = { update ->
            viewModel.ivoryTowerThreePhase(update)
        },
        onUpdateTotalGoldFourPhase = { update ->
            viewModel.ivoryTowerFourPhase(update)
        }
    )

}