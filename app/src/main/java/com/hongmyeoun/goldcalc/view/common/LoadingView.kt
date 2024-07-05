package com.hongmyeoun.goldcalc.view.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.MokokoGreen

// 로딩
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadingScreen(isBackground: Boolean = true) {
    val transition = rememberInfiniteTransition(label = "색 변경")
    val tintColor by transition.animateColor(
        initialValue = Color.White,
        targetValue = MokokoGreen,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "색이 바뀌는 애니메이션"
    )

    val bgColor = if (isBackground) ImageBG.copy(alpha = 0.5f) else ImageBG

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = R.drawable.mokoko_white,
            colorFilter = ColorFilter.tint(tintColor),
            contentDescription = "로딩 이미지"
        )
    }
}