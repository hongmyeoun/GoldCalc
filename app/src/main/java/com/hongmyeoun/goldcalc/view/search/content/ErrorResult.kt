package com.hongmyeoun.goldcalc.view.search.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hongmyeoun.goldcalc.R

@Composable
fun Error(errorMessage: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage == "네트워크 오류") {
            Image(
                painter = painterResource(id = R.drawable.baseline_wifi_off_24),
                contentDescription = "Disconnect"
            )
            Text(
                text = errorMessage,
                color = Color(0xFFCFCFCF)
            )
        } else if (errorMessage == "서버 응답 실패: 503") {
            Text(
                text = "서버 점검중 입니다~ㅠㅠ",
                color = Color(0xFFCFCFCF)
            )
        } else if (errorMessage != null && errorMessage.contains("없는")) {
            Text(
                text = errorMessage,
                color = Color(0xFFCFCFCF)
            )
        }
    }
}