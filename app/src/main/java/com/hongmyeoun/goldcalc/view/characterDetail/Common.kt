package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


fun titleTextStyle() = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    color = Color.White
)

fun normalTextStyle(color: Color = Color.White) = TextStyle(
    fontSize = 10.sp,
    color = color
)

fun smallTextStyle() = TextStyle(
    fontSize = 8.sp,
    color = Color.White
)

fun titleBoldWhite12() = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    color = Color.White
)
