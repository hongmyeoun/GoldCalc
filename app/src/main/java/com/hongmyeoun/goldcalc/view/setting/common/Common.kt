package com.hongmyeoun.goldcalc.view.setting.common

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
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongmyeoun.goldcalc.ui.theme.LightBlue
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Dialog(
    title: String,
    action: String,
    viewModel: SettingVM,
    onConfirm: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = { viewModel.onDissmissRequest() }) {

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
                        append(title)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append("를 ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    ) {
                        append(action)
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
                        onConfirm()
                    },
                ) {
                    Text(
                        text = action,
                        color = Color.Red
                    )
                }

            }
        }
    }
}


fun doneSnackbar(
    snackbarHostState: SnackbarHostState,
    text: String,
    scope: CoroutineScope
) {
    scope.launch {
        val job = launch {
            snackbarHostState.showSnackbar(message = text)
        }
        delay(2000L)
        job.cancel()
    }
}