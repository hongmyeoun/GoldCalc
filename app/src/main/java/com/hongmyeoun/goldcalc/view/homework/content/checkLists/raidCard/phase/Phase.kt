package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase

import androidx.compose.runtime.Composable

@Composable
fun BasePhase(
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
fun OnePhase(
    rotaR: Float,

    name: String,
    raidBossImg: Int,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneGold: Int,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    BossCard(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            OneGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
            )
        },
        phaseCheckUI = {
            BasePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}

@Composable
fun TwoPhase(
    rotaR: Float,

    name: String,
    raidBossImg: Int,
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
            BasePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            BasePhase(
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
    raidBossImg: Int,
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
            BasePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            BasePhase(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            BasePhase(
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
    raidBossImg: Int,
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
            BasePhase(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            BasePhase(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            BasePhase(
                phase = 3,
                difficulty = phaseThreeLevel,
                clearCheck = phaseThreeCC,
                moreCheck = phaseThreeSMC,
                onLevelClicked = { onThreePhaseLevelClicked() },
                onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
            )

            BasePhase(
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