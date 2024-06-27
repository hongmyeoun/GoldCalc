package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.ui.theme.ImageBG

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RaidCardUI(
    bossImg: Int,
    isRotated: Boolean,
    rotaR: Float,
    onClick: () -> Unit,
    phaseCard: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer {
                rotationY = rotaR
                cameraDistance = 8 * density
            }
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        if (!isRotated) {
            GlideImage(
                modifier = Modifier.aspectRatio(21f / 9f),
                contentScale = ContentScale.Crop,
                model = bossImg,
                contentDescription = "보스 이미지"
            )
        } else {
            phaseCard()
        }
    }

}

@Composable
fun RaidBossCheck(
    name: String,
    modifier: Modifier,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = name, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = ImageBG,
                uncheckedColor = Color.White // 테두리 색 변경해줌
            )
        )
    }
}