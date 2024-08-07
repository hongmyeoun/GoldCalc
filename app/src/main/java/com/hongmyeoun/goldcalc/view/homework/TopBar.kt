package com.hongmyeoun.goldcalc.view.homework

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.LightBlue
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM
import kotlinx.coroutines.launch

@Composable
fun HomeworkTopBar(
    viewModel: HomeworkVM,
    navController: NavHostController,
    scrollState: LazyListState
) {
    val showDialog by viewModel.showDialog.collectAsState()
    val showDetail by viewModel.showDetail.collectAsState()

    val scope = rememberCoroutineScope()

    if (showDialog) {
        DeleteDialog(viewModel, navController)
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
                onClick = {
                    viewModel.onShowDetailClicked()
                    scope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
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
                text = viewModel.character.value?.name ?: Homework.NULL_VALUE,
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
private fun DeleteDialog(
    viewModel: HomeworkVM,
    navController: NavHostController,
) {
    Dialog(
        onDismissRequest = { viewModel.onDismissRequest() }
    ) {
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
                        append(Homework.DELETE_DIALOG_TEXT_MIDDLE)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    ) {
                        append(ButtonText.DELETE)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append(Homework.DELETE_DIALOG_TEXT_LAST)
                    }

                },
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 0.5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.LightGray
                    )
                ) {
                    Text(text = ButtonText.CANCEL)
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                )

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDismissRequest()
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Homework.route) {
                                inclusive = true
                            }
                        }
                        viewModel.onDelete()
                    },
                ) {
                    Text(
                        text = ButtonText.DELETE,
                        color = Color.Red
                    )
                }
            }
        }
    }
}
