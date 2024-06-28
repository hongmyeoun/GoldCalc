package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.goldCheck.FourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.ThreePhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM

@Composable
fun AbyssDungeon(viewModel: AbyssDungeonVM) {
    var kayangelRotated by remember { mutableStateOf(false) }
    var ivoryTowerRotated by remember { mutableStateOf(false) }
    val kayangelRotaR by animateFloatAsState(
        targetValue = if (kayangelRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    val ivoryTowerRotaR by animateFloatAsState(
        targetValue = if (ivoryTowerRotated) 180f else 0f,
        animationSpec = tween(500)
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidBossCheck(
            name = "카양겔",
            modifier = modifier,
            checked = viewModel.kayangelCheck,
            onCheckedChange = { viewModel.onKayangelCheck() }
        )

        RaidBossCheck(
            name = "상아탑",
            modifier = modifier,
            checked = viewModel.ivoryCheck,
            onCheckedChange = { viewModel.onIvoryCheck() }
        )
    }

    if (viewModel.kayangelCheck) {
        RaidCardUI(
            bossImg = R.drawable.abyss_dungeon_kayangel,
            isRotated = kayangelRotated,
            rotaR = kayangelRotaR,
            onClick = { kayangelRotated = !kayangelRotated },
            phaseCard = {
                ThreePhaseBoss(
                    rotaR = kayangelRotaR,

                    name = viewModel.kayangel.name,
                    raidBossImg = R.drawable.logo_kayangel,
                    totalGold = viewModel.kayangel.totalGold,

                    phaseOneLevel = viewModel.kayangel.onePhase.level,
                    phaseOneGold = viewModel.kayangel.onePhase.totalGold,
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
                    phaseTwoGold = viewModel.kayangel.twoPhase.totalGold,
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
                    phaseThreeGold = viewModel.kayangel.threePhase.totalGold,
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
        )
    }

    if (viewModel.ivoryCheck) {
        RaidCardUI(
            bossImg = R.drawable.abyss_dungeon_ivory_tower,
            isRotated = ivoryTowerRotated,
            rotaR = ivoryTowerRotaR,
            onClick = { ivoryTowerRotated = !ivoryTowerRotated },
            phaseCard = {
                FourPhaseBoss(
                    rotaR = ivoryTowerRotaR,

                    name = viewModel.ivoryTower.name,
                    raidBossImg = R.drawable.logo_ivory_tower,
                    totalGold = viewModel.ivoryTower.totalGold,

                    phaseOneLevel = viewModel.ivoryTower.onePhase.level,
                    phaseOneGold = viewModel.ivoryTower.onePhase.totalGold,
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
                    phaseTwoGold = viewModel.ivoryTower.twoPhase.totalGold,
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
                    phaseThreeGold = viewModel.ivoryTower.threePhase.totalGold,
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
                    phaseFourGold = viewModel.ivoryTower.fourPhase.totalGold,
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
        )
    }
}