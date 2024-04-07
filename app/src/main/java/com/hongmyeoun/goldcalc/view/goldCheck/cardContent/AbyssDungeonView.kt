package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hongmyeoun.goldcalc.view.goldCheck.FourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.ThreePhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM

@Composable
fun AbyssDungeon(viewModel: AbyssDungeonVM) {

    LaunchedEffect(viewModel.kayangelTG, viewModel.ivoryTowerTG) {
        viewModel.sumGold()
    }

    ThreePhaseBoss(
        name = viewModel.kayangel.name,
        totalGold = viewModel.totalGold,

        phaseOneLevel = viewModel.kayangel.onePhase.level,
        phaseOneSMC = viewModel.kayangel.onePhase.seeMoreCheck,
        phaseOneCC = viewModel.kayangel.onePhase.clearCheck,
        onOnePhaseLevelClicked = {
            viewModel.kayangel.onePhase.onLevelClicked()
            viewModel.kayangelTotal()
        },
        onOnePhaseClearCheckBoxChecked = {
            viewModel.kayangel.onePhase.onClearCheckBoxClicked(it)
            viewModel.kayangelTotal()
        },
        onOnePhaseSeeMoreCheckBoxChecked = {
            viewModel.kayangel.onePhase.onSeeMoreCheckBoxClicked(it)
            viewModel.kayangelTotal()
        },

        phaseTwoLevel = viewModel.kayangel.twoPhase.level,
        phaseTwoSMC = viewModel.kayangel.twoPhase.seeMoreCheck,
        phaseTwoCC = viewModel.kayangel.twoPhase.clearCheck,
        onTwoPhaseLevelClicked = {
            viewModel.kayangel.twoPhase.onLevelClicked()
            viewModel.kayangelTotal()
        },
        onTwoPhaseClearCheckBoxChecked = {
            viewModel.kayangel.twoPhase.onClearCheckBoxClicked(it)
            viewModel.kayangelTotal()
        },
        onTwoPhaseSeeMoreCheckBoxChecked = {
            viewModel.kayangel.twoPhase.onSeeMoreCheckBoxClicked(it)
            viewModel.kayangelTotal()
        },

        phaseThreeLevel = viewModel.kayangel.threePhase.level,
        phaseThreeSMC = viewModel.kayangel.threePhase.seeMoreCheck,
        phaseThreeCC = viewModel.kayangel.threePhase.clearCheck,
        onThreePhaseLevelClicked = {
            viewModel.kayangel.threePhase.onLevelClicked()
            viewModel.kayangelTotal()
        },
        onThreePhaseClearCheckBoxChecked = {
            viewModel.kayangel.threePhase.onClearCheckBoxClicked(it)
            viewModel.kayangelTotal()
        },
        onThreePhaseSeeMoreCheckBoxChecked = {
            viewModel.kayangel.threePhase.onSeeMoreCheckBoxClicked(it)
            viewModel.kayangelTotal()
        }
    )

    FourPhaseBoss(
        name = viewModel.ivoryTower.name,
        totalGold = viewModel.totalGold,

        phaseOneLevel = viewModel.ivoryTower.onePhase.level,
        phaseOneSMC = viewModel.ivoryTower.onePhase.seeMoreCheck,
        phaseOneCC = viewModel.ivoryTower.onePhase.clearCheck,
        onOnePhaseLevelClicked = {
            viewModel.ivoryTower.onePhase.onLevelClicked()
            viewModel.ivoryTowerTotal()
        },
        onOnePhaseClearCheckBoxChecked = {
            viewModel.ivoryTower.onePhase.onClearCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },
        onOnePhaseSeeMoreCheckBoxChecked = {
            viewModel.ivoryTower.onePhase.onSeeMoreCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },

        phaseTwoLevel = viewModel.ivoryTower.twoPhase.level,
        phaseTwoSMC = viewModel.ivoryTower.twoPhase.seeMoreCheck,
        phaseTwoCC = viewModel.ivoryTower.twoPhase.clearCheck,
        onTwoPhaseLevelClicked = {
            viewModel.ivoryTower.twoPhase.onLevelClicked()
            viewModel.ivoryTowerTotal()
        },
        onTwoPhaseClearCheckBoxChecked = {
            viewModel.ivoryTower.twoPhase.onClearCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },
        onTwoPhaseSeeMoreCheckBoxChecked = {
            viewModel.ivoryTower.twoPhase.onSeeMoreCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },

        phaseThreeLevel = viewModel.ivoryTower.threePhase.level,
        phaseThreeSMC = viewModel.ivoryTower.threePhase.seeMoreCheck,
        phaseThreeCC = viewModel.ivoryTower.threePhase.clearCheck,
        onThreePhaseLevelClicked = {
            viewModel.ivoryTower.threePhase.onLevelClicked()
            viewModel.ivoryTowerTotal()
        },
        onThreePhaseClearCheckBoxChecked = {
            viewModel.ivoryTower.threePhase.onClearCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },
        onThreePhaseSeeMoreCheckBoxChecked = {
            viewModel.ivoryTower.threePhase.onSeeMoreCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },

        phaseFourLevel = viewModel.ivoryTower.fourPhase.level,
        phaseFourSMC = viewModel.ivoryTower.fourPhase.seeMoreCheck,
        phaseFourCC = viewModel.ivoryTower.fourPhase.clearCheck,
        onFourPhaseLevelClicked = {
            viewModel.ivoryTower.fourPhase.onLevelClicked()
            viewModel.ivoryTowerTotal()
        },
        onFourPhaseClearCheckBoxChecked = {
            viewModel.ivoryTower.fourPhase.onClearCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        },
        onFourPhaseSeeMoreCheckBoxChecked = {
            viewModel.ivoryTower.fourPhase.onSeeMoreCheckBoxClicked(it)
            viewModel.ivoryTowerTotal()
        }
    )
}