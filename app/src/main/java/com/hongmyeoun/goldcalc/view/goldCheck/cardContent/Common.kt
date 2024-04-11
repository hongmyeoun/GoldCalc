package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RaidCardUI(
    bossImg: Int,
    isCheck: Boolean,
    isRotated: Boolean,
    rotaR: Float,
    onClick: () -> Unit,
    phaseCard: @Composable () -> Unit
) {
    if (isCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = rotaR
                    cameraDistance = 8 * density
                }
                .clickable { onClick() }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!isRotated) {
                Image(
                    modifier = Modifier.aspectRatio(21f/9f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = bossImg),
                    contentDescription = "보스 이미지"
                )
            } else {
                phaseCard()
            }
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
    Row(modifier = modifier) {
        Text(text = name)
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            }
        )
    }
}