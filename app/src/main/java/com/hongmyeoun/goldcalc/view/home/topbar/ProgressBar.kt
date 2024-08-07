package com.hongmyeoun.goldcalc.view.home.topbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.common.toPercentage
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.viewModel.home.HomeVM

@Composable
fun ProgressBar(viewModel: HomeVM) {
    val progressPercentage by viewModel.progressPercentage.collectAsState()

    val animatedProgress = animateFloatAsState(
        targetValue = progressPercentage,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = Labels.Animation.PROGRESSBAR
    ).value

    Box {
        LinearProgressIndicator(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth()
                .border(
                    width = 0.5f.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(16.dp)
                ),
            progress = animatedProgress,
            color = CharacterEmblemBG,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            text = progressPercentage.toPercentage(),
            textAlign = TextAlign.End,
            color = Color.White
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}
