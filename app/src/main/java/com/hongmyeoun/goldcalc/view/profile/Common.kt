package com.hongmyeoun.goldcalc.view.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun titleTextStyle(fontSize: TextUnit = 18.sp, color: Color = Color.White) = TextStyle(
    fontSize = fontSize,
    fontWeight = FontWeight.Bold,
    color = color
)

fun normalTextStyle(color: Color = Color.White, fontSize: TextUnit = 10.sp) = TextStyle(
    fontSize = fontSize,
    color = color
)

fun titleBoldWhite12() = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    color = Color.White
)

@Composable
fun Title(
    title: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}
