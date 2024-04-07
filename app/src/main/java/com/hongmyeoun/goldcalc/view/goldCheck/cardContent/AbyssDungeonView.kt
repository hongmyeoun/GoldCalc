package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.view.goldCheck.FourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.ThreePhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM

@Composable
fun AbyssDungeon(viewModel: AbyssDungeonVM) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(text = "카양겔")
            Checkbox(
                checked = viewModel.kayangelCheck,
                onCheckedChange = {
                    viewModel.onKayangelCheck()
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "상아탑")
            Checkbox(
                checked = viewModel.ivoryCheck,
                onCheckedChange = {
                    viewModel.onIvoryCheck()
                }
            )
        }
    }

    if (viewModel.kayangelCheck) {
        ThreePhaseBoss(
            name = viewModel.kayangel.name,
            totalGold = viewModel.totalGold,

            phaseOneLevel = viewModel.kayangel.onePhase.level,
            phaseOneSMC = viewModel.kayangel.onePhase.seeMoreCheck,
            phaseOneCC = viewModel.kayangel.onePhase.clearCheck,
            onOnePhaseLevelClicked = {
                viewModel.kayangel.onePhase.onLevelClicked()
                viewModel.sumGold()
            },
            onOnePhaseClearCheckBoxChecked = {
                viewModel.kayangel.onePhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onOnePhaseSeeMoreCheckBoxChecked = {
                viewModel.kayangel.onePhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            },

            phaseTwoLevel = viewModel.kayangel.twoPhase.level,
            phaseTwoSMC = viewModel.kayangel.twoPhase.seeMoreCheck,
            phaseTwoCC = viewModel.kayangel.twoPhase.clearCheck,
            onTwoPhaseLevelClicked = {
                viewModel.kayangel.twoPhase.onLevelClicked()
                viewModel.sumGold()
            },
            onTwoPhaseClearCheckBoxChecked = {
                viewModel.kayangel.twoPhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onTwoPhaseSeeMoreCheckBoxChecked = {
                viewModel.kayangel.twoPhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            },

            phaseThreeLevel = viewModel.kayangel.threePhase.level,
            phaseThreeSMC = viewModel.kayangel.threePhase.seeMoreCheck,
            phaseThreeCC = viewModel.kayangel.threePhase.clearCheck,
            onThreePhaseLevelClicked = {
                viewModel.kayangel.threePhase.onLevelClicked()
                viewModel.sumGold()
            },
            onThreePhaseClearCheckBoxChecked = {
                viewModel.kayangel.threePhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onThreePhaseSeeMoreCheckBoxChecked = {
                viewModel.kayangel.threePhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            }
        )
    }

    if (viewModel.ivoryCheck) {
        FourPhaseBoss(
            name = viewModel.ivoryTower.name,
            totalGold = viewModel.totalGold,

            phaseOneLevel = viewModel.ivoryTower.onePhase.level,
            phaseOneSMC = viewModel.ivoryTower.onePhase.seeMoreCheck,
            phaseOneCC = viewModel.ivoryTower.onePhase.clearCheck,
            onOnePhaseLevelClicked = {
                viewModel.ivoryTower.onePhase.onLevelClicked()
                viewModel.sumGold()
            },
            onOnePhaseClearCheckBoxChecked = {
                viewModel.ivoryTower.onePhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onOnePhaseSeeMoreCheckBoxChecked = {
                viewModel.ivoryTower.onePhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            },

            phaseTwoLevel = viewModel.ivoryTower.twoPhase.level,
            phaseTwoSMC = viewModel.ivoryTower.twoPhase.seeMoreCheck,
            phaseTwoCC = viewModel.ivoryTower.twoPhase.clearCheck,
            onTwoPhaseLevelClicked = {
                viewModel.ivoryTower.twoPhase.onLevelClicked()
                viewModel.sumGold()
            },
            onTwoPhaseClearCheckBoxChecked = {
                viewModel.ivoryTower.twoPhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onTwoPhaseSeeMoreCheckBoxChecked = {
                viewModel.ivoryTower.twoPhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            },

            phaseThreeLevel = viewModel.ivoryTower.threePhase.level,
            phaseThreeSMC = viewModel.ivoryTower.threePhase.seeMoreCheck,
            phaseThreeCC = viewModel.ivoryTower.threePhase.clearCheck,
            onThreePhaseLevelClicked = {
                viewModel.ivoryTower.threePhase.onLevelClicked()
                viewModel.sumGold()
            },
            onThreePhaseClearCheckBoxChecked = {
                viewModel.ivoryTower.threePhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onThreePhaseSeeMoreCheckBoxChecked = {
                viewModel.ivoryTower.threePhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            },

            phaseFourLevel = viewModel.ivoryTower.fourPhase.level,
            phaseFourSMC = viewModel.ivoryTower.fourPhase.seeMoreCheck,
            phaseFourCC = viewModel.ivoryTower.fourPhase.clearCheck,
            onFourPhaseLevelClicked = {
                viewModel.ivoryTower.fourPhase.onLevelClicked()
                viewModel.sumGold()
            },
            onFourPhaseClearCheckBoxChecked = {
                viewModel.ivoryTower.fourPhase.onClearCheckBoxClicked(it)
                viewModel.sumGold()
            },
            onFourPhaseSeeMoreCheckBoxChecked = {
                viewModel.ivoryTower.fourPhase.onSeeMoreCheckBoxClicked(it)
                viewModel.sumGold()
            }
        )
    }
}