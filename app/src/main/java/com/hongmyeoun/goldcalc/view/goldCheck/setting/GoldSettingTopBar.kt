package com.hongmyeoun.goldcalc.view.goldCheck.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.ui.theme.LightBlue
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM

@Composable
fun GoldSettingTopBar(
    viewModel: GoldSettingVM,
    navController: NavHostController
) {
    val showDialog by viewModel.showDialog.collectAsState()
    val showDetail by viewModel.showDetail.collectAsState()

    if (showDialog) {
        DeleteCharacterDialog(viewModel, navController)
    }

    Column(
        modifier = Modifier.background(LightGrayBG)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.weight(0.5f),
                onClick = { viewModel.onShowDetailClicked() }
            ) {
                if (showDetail) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        tint = Color.White,
                        contentDescription = "펼치기"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = Color.White,
                        contentDescription = "접기"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                text = viewModel.character.value?.name ?: "정보없음",
                style = titleTextStyle(fontSize = 20.sp),
                textAlign = TextAlign.Center,
            )
            IconButton(
                modifier = Modifier.weight(0.5f),
                onClick = { viewModel.onClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = "삭제"
                )
            }
        }
    }
}

@Composable
private fun DeleteCharacterDialog(
    viewModel: GoldSettingVM,
    navController: NavHostController,
) {
    Dialog(onDismissRequest = { viewModel.onDissmissRequest() }) {

        Column(
            modifier = Modifier.background(LightGrayBG, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = LightBlue,
                            fontSize = 20.sp
                        )
                    ) {
                        append("${viewModel.character.value?.name}")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append("의 정보를 ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    ) {
                        append("삭제")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append(" 하시겠습니까?")
                    }

                },
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDissmissRequest()
                    },
                ) {
                    Text(text = "취소")
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDissmissRequest()
                        navController.popBackStack()
                        viewModel.onDelete()
                    },
                ) {
                    Text(
                        text = "삭제",
                        color = Color.Red
                    )
                }

            }
        }
    }
}

