package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase

import androidx.compose.runtime.Composable

@Composable
fun OnePhase(
    phase: Int,
    difficulty: String,
    clearCheck: Boolean,
    moreCheck: Boolean,
    onLevelClicked: () -> Unit,
    onClearClicked: (Boolean) -> Unit,
    onMoreClicked: (Boolean) -> Unit
) {
    Level(
        phase = phase,
        difficulty = difficulty,
        clearCheck = clearCheck,
        moreCheck = moreCheck,
        onLevelClicked = onLevelClicked,
    )
    ClearMoreCheckBox(
        clearCheck = clearCheck,
        onClearClicked = { onClearClicked(it) },
        moreCheck = moreCheck,
        onMoreClicked = { onMoreClicked(it) }
    )
}

@Composable
fun TwoPhase(
    rotaR: Float,

    name: String,
    raidBossImg: String?,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneGold: Int,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoLevel: String,
    phaseTwoGold: Int,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
    onTwoPhaseLevelClicked: () -> Unit,
    onTwoPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onTwoPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    BossCard(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            TwoGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold
            )
        },
        phaseCheckUI = {
            OnePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            OnePhase(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}

@Composable
fun ThreePhase(
    rotaR: Float,

    name: String,
    raidBossImg: String?,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneGold: Int,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoLevel: String,
    phaseTwoGold: Int,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
    onTwoPhaseLevelClicked: () -> Unit,
    onTwoPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onTwoPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseThreeLevel: String,
    phaseThreeGold: Int,
    phaseThreeSMC: Boolean,
    phaseThreeCC: Boolean,
    onThreePhaseLevelClicked: () -> Unit,
    onThreePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onThreePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    BossCard(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            ThreeGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold,
                phaseThreeGold = phaseThreeGold
            )
        },
        phaseCheckUI = {
            OnePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            OnePhase(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            OnePhase(
                phase = 3,
                difficulty = phaseThreeLevel,
                clearCheck = phaseThreeCC,
                moreCheck = phaseThreeSMC,
                onLevelClicked = { onThreePhaseLevelClicked() },
                onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}

@Composable
fun FourPhase(
    rotaR: Float,

    name: String,
    raidBossImg: String?,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneGold: Int,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoLevel: String,
    phaseTwoGold: Int,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
    onTwoPhaseLevelClicked: () -> Unit,
    onTwoPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onTwoPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseThreeLevel: String,
    phaseThreeGold: Int,
    phaseThreeSMC: Boolean,
    phaseThreeCC: Boolean,
    onThreePhaseLevelClicked: () -> Unit,
    onThreePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onThreePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseFourLevel: String,
    phaseFourGold: Int,
    phaseFourSMC: Boolean,
    phaseFourCC: Boolean,
    onFourPhaseLevelClicked: () -> Unit,
    onFourPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onFourPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    BossCard(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            FourGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold,
                phaseThreeGold = phaseThreeGold,
                phaseFourGold = phaseFourGold
            )
        },
        phaseCheckUI = {
            OnePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            OnePhase(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            OnePhase(
                phase = 3,
                difficulty = phaseThreeLevel,
                clearCheck = phaseThreeCC,
                moreCheck = phaseThreeSMC,
                onLevelClicked = { onThreePhaseLevelClicked() },
                onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
            )

            OnePhase(
                phase = 4,
                difficulty = phaseFourLevel,
                clearCheck = phaseFourCC,
                moreCheck = phaseFourSMC,
                onLevelClicked = { onFourPhaseLevelClicked() },
                onClearClicked = { onFourPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onFourPhaseSeeMoreCheckBoxChecked(it) }
            )

        }
    )
}