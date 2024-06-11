package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun CharDetailTitleTextStyle() = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.White
)

@Composable
fun normalTextStyle() = TextStyle(
    fontSize = 10.sp,
    color = Color.White
)