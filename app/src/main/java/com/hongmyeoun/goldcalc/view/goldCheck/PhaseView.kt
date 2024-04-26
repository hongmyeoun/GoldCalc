package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R

@Composable
fun PhaseUI(
    phase: Int,
    difficulty: String,
    clearCheck: Boolean,
    moreCheck: Boolean,
    onLevelClicked: () -> Unit,
    onClearClicked: (Boolean) -> Unit,
    onMoreClicked: (Boolean) -> Unit
) {
    val difficultyColor = if (difficulty == "하드") Color.Red else MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(start = 16.dp),
            text = "$phase 관문",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedButton(
            onClick = { onLevelClicked() },
            enabled = clearCheck || moreCheck,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = difficultyColor,
                disabledContentColor = Color.Gray
            )
        ) {
            Text(text = difficulty)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = clearCheck,
                onCheckedChange = { onClearClicked(it) }
            )
            Text(text = "클리어")
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = moreCheck,
                onCheckedChange = { onMoreClicked(it) }
            )
            Text(text = "더보기")
        }
    }

}

@Composable
fun TwoPhaseBoss(
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
    RaidCardUI(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            TwoPhaseGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold
            )
        },
        phaseCheckUI = {
            PhaseUI(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
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
fun ThreePhaseBoss(
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
    RaidCardUI(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            ThreePhaseGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold,
                phaseThreeGold = phaseThreeGold
            )
        },
        phaseCheckUI = {
            PhaseUI(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
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
fun FourPhaseBoss(
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
    RaidCardUI(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            FourPhaseGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold,
                phaseThreeGold = phaseThreeGold,
                phaseFourGold = phaseFourGold
            )
        },
        phaseCheckUI = {
            PhaseUI(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
                phase = 3,
                difficulty = phaseThreeLevel,
                clearCheck = phaseThreeCC,
                moreCheck = phaseThreeSMC,
                onLevelClicked = { onThreePhaseLevelClicked() },
                onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RaidCardUI(
    rotaR: Float,
    raidBossImg: Int,
    totalGold: Int,
    phaseGoldTextUI: @Composable () -> Unit,
    phaseCheckUI: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .graphicsLayer {
                rotationY = rotaR
                cameraDistance = 8 * density
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            GlideImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .weight(1f),
                contentScale = ContentScale.Crop,
                model = raidBossImg,
                contentDescription =  "보스 아이콘"
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                phaseGoldTextUI()

                TotalGoldText(totalGold = totalGold)
            }
        }

        phaseCheckUI()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TotalGoldText(totalGold: Int) {
    Divider()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "합 : "
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "$totalGold",
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(8.dp))
        GlideImage(
            modifier = Modifier
                .weight(0.5f)
                .size(16.dp),
            alignment = Alignment.Center,
            model = R.drawable.gold_coin,
            contentDescription = "골드 아이콘"
        )
    }
}

@Composable
fun RaidNameText(name: String) {
    Text(
        text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(64.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhaseGoldText(
    phase: String,
    phaseGold: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$phase : ")
        Text(
            modifier = Modifier.weight(1f),
            text = "$phaseGold",
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(8.dp))
        GlideImage(
            modifier = Modifier
                .weight(0.5f)
                .size(16.dp),
            alignment = Alignment.Center,
            model = R.drawable.gold_coin,
            contentDescription = "골드 아이콘"
        )
    }
}

@Composable
fun TwoPhaseGoldText(
    name: String,
    phaseOneGold: Int,
    phaseTwoGold: Int,
) {
    RaidNameText(name = name)

    PhaseGoldText(
        phase = "①",
        phaseGold = phaseOneGold
    )
    PhaseGoldText(
        phase = "②",
        phaseGold = phaseTwoGold
    )
}

@Composable
fun ThreePhaseGoldText(
    name: String,
    phaseOneGold: Int,
    phaseTwoGold: Int,
    phaseThreeGold: Int,
) {
    TwoPhaseGoldText(
        name = name,
        phaseOneGold = phaseOneGold,
        phaseTwoGold = phaseTwoGold
    )
    PhaseGoldText(
        phase = "③",
        phaseGold = phaseThreeGold
    )
}

@Composable
fun FourPhaseGoldText(
    name: String,
    phaseOneGold: Int,
    phaseTwoGold: Int,
    phaseThreeGold: Int,
    phaseFourGold: Int
) {
    ThreePhaseGoldText(
        name = name,
        phaseOneGold = phaseOneGold,
        phaseTwoGold = phaseTwoGold,
        phaseThreeGold = phaseThreeGold
    )
    PhaseGoldText(
        phase = "④",
        phaseGold = phaseFourGold
    )
}

@Composable
fun PhaseUIOneDifficulty(
    phase: Int,
    difficulty: String,
    clearCheck: Boolean,
    moreCheck: Boolean,
    onClearClicked: (Boolean) -> Unit,
    onMoreClicked: (Boolean) -> Unit
) {
    val difficultyColor = if (difficulty == "하드") Color.Red else MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(start = 16.dp),
            text = "$phase 관문",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedButton(
            onClick = {  },
            enabled = clearCheck || moreCheck,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = difficultyColor,
                disabledContentColor = Color.Gray
            )
        ) {
            Text(text = difficulty)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = clearCheck,
                onCheckedChange = { onClearClicked(it) }
            )
            Text(text = "클리어")
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = moreCheck,
                onCheckedChange = { onMoreClicked(it) }
            )
            Text(text = "더보기")
        }
    }

}


@Composable
fun TwoPhaseBossNoHard(
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
    RaidCardUI(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            TwoPhaseGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold
            )
        },
        phaseCheckUI = {
            PhaseUIOneDifficulty(
                phase = 1,
                difficulty = "노말",
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUIOneDifficulty(
                phase = 2,
                difficulty = "노말",
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}

@Composable
fun ThreePhaseBossNoHard(
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

    phaseThreeGold: Int,
    phaseThreeSMC: Boolean,
    phaseThreeCC: Boolean,
    onThreePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onThreePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    RaidCardUI(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            ThreePhaseGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold,
                phaseThreeGold = phaseThreeGold
            )
        },
        phaseCheckUI = {
            PhaseUIOneDifficulty(
                phase = 1,
                difficulty = "노말",
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUIOneDifficulty(
                phase = 2,
                difficulty = "노말",
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUIOneDifficulty(
                phase = 3,
                difficulty = "노말",
                clearCheck = phaseThreeCC,
                moreCheck = phaseThreeSMC,
                onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}

@Composable
fun FourPhaseBossLastHard(
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
    RaidCardUI(
        rotaR = rotaR,
        raidBossImg = raidBossImg,
        totalGold = totalGold,
        phaseGoldTextUI = {
            FourPhaseGoldText(
                name = name,
                phaseOneGold = phaseOneGold,
                phaseTwoGold = phaseTwoGold,
                phaseThreeGold = phaseThreeGold,
                phaseFourGold = phaseFourGold
            )
        },
        phaseCheckUI = {
            PhaseUI(
                phase = 1,
                difficulty = phaseOneLevel,
                clearCheck = phaseOneCC,
                moreCheck = phaseOneSMC,
                onLevelClicked = { onOnePhaseLevelClicked() },
                onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
                phase = 2,
                difficulty = phaseTwoLevel,
                clearCheck = phaseTwoCC,
                moreCheck = phaseTwoSMC,
                onLevelClicked = { onTwoPhaseLevelClicked() },
                onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUI(
                phase = 3,
                difficulty = phaseThreeLevel,
                clearCheck = phaseThreeCC,
                moreCheck = phaseThreeSMC,
                onLevelClicked = { onThreePhaseLevelClicked() },
                onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
            )

            PhaseUIOneDifficulty(
                phase = 4,
                difficulty = "하드",
                clearCheck = phaseFourCC,
                moreCheck = phaseFourSMC,
                onClearClicked = { onFourPhaseClearCheckBoxChecked(it) },
                onMoreClicked = { onFourPhaseSeeMoreCheckBoxChecked(it) }
            )
        }
    )
}
