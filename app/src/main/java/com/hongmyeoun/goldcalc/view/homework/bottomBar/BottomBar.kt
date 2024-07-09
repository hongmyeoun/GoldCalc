package com.hongmyeoun.goldcalc.view.homework.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.ImageReturn.goldImage
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.homework.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.homework.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.homework.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM
import com.hongmyeoun.goldcalc.viewModel.homework.KazerothRaidVM

@Composable
fun HomeworkBottomBar(
    viewModel: HomeworkVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    navController: NavHostController
) {
    if (viewModel.expanded) {
        Summary(viewModel, cbVM, adVM, kzVM, epVM, navController)
    }

    BottomBar(
        viewModel = viewModel,
        navController = navController
    ) { viewModel.onDoneClick(cbVM, adVM, kzVM, epVM) }
}



@Composable
private fun BottomBar(
    viewModel: HomeworkVM,
    navController: NavHostController,
    onDoneClicked: () -> Unit
) {
    val character by viewModel.character.collectAsState()

    val borderPadding = if (viewModel.expanded) Modifier
        .padding(start = 16.dp, end = 8.dp, bottom = 16.dp, top = 16.dp) else Modifier.padding(start = 16.dp, end = 8.dp, bottom = 16.dp)

    var dragOffsetY by remember { mutableStateOf(0f) }
    val threshold = 100f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        dragOffsetY += dragAmount.y

                        // 드래그된 거리가 임계값을 초과하면 expand 호출
                        if (dragOffsetY < -threshold) {
                            viewModel.expand()
                        }
                    }
                )
            }
            .then(borderPadding)
    ) {
        DragDerivationIcon()

        BottomBarTexts(character, viewModel, navController, onDoneClicked)
    }
}

@Composable
fun BottomBarTexts(
    character: Character?,
    viewModel: HomeworkVM,
    navController: NavHostController,
    onDoneClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(3f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GoldText(beforeOrAfter = Homework.BEFORE, gold = character?.weeklyGold ?: 0)
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.ArrowForward,
                tint = Color.White,
                contentDescription = "화살표"
            )
            Spacer(modifier = Modifier.width(12.dp))

            GoldText(beforeOrAfter = Homework.AFTER, gold = viewModel.totalGold)
        }

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Homework.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(ButtonText.CANCEL)
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenQual,
                    contentColor = Color.White
                ),
                onClick = {
                    onDoneClicked()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Homework.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = ButtonText.CONFIRM)
            }
        }
    }
}

@Composable
fun DragDerivationIcon() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(64.dp)
                .height(4.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .align(Alignment.Center)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GoldText(beforeOrAfter: String, gold: Int) {
    Column {
        Text(
            text = "변경 $beforeOrAfter",
            style = titleTextStyle(fontSize = 18.sp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier.size(18.dp),
                model = goldImage(gold),
                contentDescription = "골드 이미지",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = gold.formatWithCommas(),
                style = normalTextStyle(fontSize = 14.sp)
            )
        }
    }
}
