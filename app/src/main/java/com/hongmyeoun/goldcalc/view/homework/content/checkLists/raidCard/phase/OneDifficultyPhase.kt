package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.model.constants.raid.Raid

@Composable
fun OneDifficultyPhase(
    phase: Int,
    difficulty: String,
    clearCheck: Boolean,
    moreCheck: Boolean,
    onClearClicked: (Boolean) -> Unit,
    onMoreClicked: (Boolean) -> Unit
) {
    Level(
        phase = phase,
        difficulty = difficulty,
        clearCheck = clearCheck,
        moreCheck = moreCheck,
        oneDifficult = true
    )
    ClearMoreCheckBox(
        clearCheck = clearCheck,
        onClearClicked = { onClearClicked(it) },
        moreCheck = moreCheck,
        onMoreClicked = { onMoreClicked(it) }
    )
}


@Composable
fun TwoPhaseNoHard(
    rotaR: Float,

    name: String,
    raidBossImg: Int,
    totalGold: Int,

    phaseOneGold: Int,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoGold: Int,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
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
            OneDifficultyPhase(
                phase = 1,
                difficulty = Raid.Difficulty.KR_NORMAL,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            OneDifficultyPhase(
                phase = 2,
                difficulty = Raid.Difficulty.KR_NORMAL,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}

@Composable
fun FourPhaseLastHard(
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

    phaseFourGold: Int,
    phaseFourSMC: Boolean,
    phaseFourCC: Boolean,
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

            OneDifficultyPhase(
                phase = 4,
                difficulty = Raid.Difficulty.KR_HARD,
                clearCheck = phaseFourCC,
                moreCheck = phaseFourSMC,
                onClearClicked = { onFourPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onFourPhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}