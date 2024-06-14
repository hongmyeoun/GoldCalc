package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


fun titleTextStyle() = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    color = Color.White
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
