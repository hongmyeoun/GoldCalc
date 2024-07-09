package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
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
import com.hongmyeoun.goldcalc.model.common.ImageReturn.goldImage
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.ui.theme.YellowQual
import com.hongmyeoun.goldcalc.view.common.noRippleClickable

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BossCard(
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
                contentDescription = "보스 아이콘"
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
private fun TotalGoldText(
    totalGold: Int,
) {
    Divider(color = Color.White)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Homework.TOTAL_GOLD
        )
        Text(
            modifier = Modifier.weight(0.7f),
            text = totalGold.formatWithCommas(),
            textAlign = TextAlign.End
        )
        GlideImage(
            modifier = Modifier
                .weight(0.3f)
                .size(16.dp),
            alignment = Alignment.Center,
            model = goldImage(totalGold),
            contentDescription = "골드 아이콘"
        )
    }
}

@Composable
private fun RaidNameText(name: String) {
    Text(
        text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(64.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun OneGoldText(
    phase: String,
    phaseGold: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$phase : ")
        Text(
            modifier = Modifier.weight(0.7f),
            text = phaseGold.formatWithCommas(),
            textAlign = TextAlign.End
        )
        GlideImage(
            modifier = Modifier
                .weight(0.3f)
                .size(16.dp),
            alignment = Alignment.Center,
            model = goldImage(phaseGold),
            contentDescription = "골드 아이콘"
        )
    }
}

@Composable
fun TwoGoldText(
    name: String,
    phaseOneGold: Int,
    phaseTwoGold: Int,
) {
    RaidNameText(name = name)

    OneGoldText(
        phase = Homework.PHASE_ONE_NUM,
        phaseGold = phaseOneGold
    )
    OneGoldText(
        phase = Homework.PHASE_TWO_NUM,
        phaseGold = phaseTwoGold
    )
}

@Composable
fun ThreeGoldText(
    name: String,
    phaseOneGold: Int,
    phaseTwoGold: Int,
    phaseThreeGold: Int,
) {
    TwoGoldText(
        name = name,
        phaseOneGold = phaseOneGold,
        phaseTwoGold = phaseTwoGold
    )
    OneGoldText(
        phase = Homework.PHASE_THREE_NUM,
        phaseGold = phaseThreeGold
    )
}

@Composable
fun FourGoldText(
    name: String,
    phaseOneGold: Int,
    phaseTwoGold: Int,
    phaseThreeGold: Int,
    phaseFourGold: Int
) {
    ThreeGoldText(
        name = name,
        phaseOneGold = phaseOneGold,
        phaseTwoGold = phaseTwoGold,
        phaseThreeGold = phaseThreeGold
    )
    OneGoldText(
        phase = Homework.PHASE_FOUR_NUM,
        phaseGold = phaseFourGold
    )
}

@Composable
fun ClearMoreCheckBox(
    clearCheck: Boolean,
    onClearClicked: (Boolean) -> Unit,
    moreCheck: Boolean,
    onMoreClicked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f).noRippleClickable {  },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = clearCheck,
                onCheckedChange = { onClearClicked(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = ImageBG,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Text(text = Homework.CLEAR)
        }

        Row(
            modifier = Modifier.weight(1f).noRippleClickable {  },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = moreCheck,
                onCheckedChange = { onMoreClicked(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = ImageBG,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Text(text = Homework.SEE_MORE)
        }
    }
}

@Composable
fun Level(
    phase: Int,
    difficulty: String,
    clearCheck: Boolean,
    moreCheck: Boolean,
    onLevelClicked: () -> Unit = {},
    oneDifficult: Boolean = false
) {
    val difficultyColor = if (difficulty == Raid.Difficulty.KR_HARD) RedQual else if (difficulty == Raid.Difficulty.KR_NORMAL) GreenQual else YellowQual

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable {  }
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
            onClick = { if (!oneDifficult) { onLevelClicked() } },
            enabled = clearCheck || moreCheck,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = difficultyColor,
                disabledContentColor = Color.LightGray
            )
        ) {
            Text(text = difficulty)
        }
    }
}