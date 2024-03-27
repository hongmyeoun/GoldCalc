package com.hongmyeoun.goldcalc.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.viewModel.AbyssDungeonVM

@Composable
fun AbyssDungeonCard(viewModel: AbyssDungeonVM){
    val height = if (viewModel.expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)

    Card(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(durationMillis = 1000))
            .then(height)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(0.5f),
                imageVector = Icons.Default.AccountBox,
                contentDescription = "어비스던전 이미지"
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "어비스던전"
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${viewModel.totalGold}")
            }
            IconButton(
                modifier = Modifier.weight(0.5f),
                onClick = { viewModel.expand() },
                interactionSource = remember { MutableInteractionSource() },
            ) {
                Icon(
                    imageVector = if (viewModel.expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "펼치기"
                )
            }
        }

        val kayangel = threePhaseBoss(
            name = viewModel.kayang,
            seeMoreGold = viewModel.kayangSMG,
            clearGold = viewModel.kayangCG
        )
        val ivoryTower = fourPhaseBoss(
            name = viewModel.ivT,
            seeMoreGold = viewModel.ivTSMG,
            clearGold = viewModel.ivTCG
        )

        viewModel.sumGold(kayangel, ivoryTower)

        IconButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.expand() }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "접기")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}