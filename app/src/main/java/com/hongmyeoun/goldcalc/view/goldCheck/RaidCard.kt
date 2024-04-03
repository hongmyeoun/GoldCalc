package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

//@Composable
//fun RaidCard(
//    raidType: String,
//    raidImg: ImageVector,
//    totalGold: Int,
//    expanded: Boolean,
//    onArrowClicked: () -> Unit,
//    raidContent: @Composable () -> Unit
//) {
//    val height = if (expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)
//
//    Card(
//        modifier = Modifier
//            .animateContentSize(animationSpec = tween(durationMillis = 1000))
//            .then(height)
//            .fillMaxWidth()
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                modifier = Modifier.weight(0.5f),
//                imageVector = raidImg,
//                contentDescription = "$raidType 이미지"
//            )
//            Text(
//                modifier = Modifier.weight(1f),
//                text = raidType
//            )
//            Row(
//                modifier = Modifier.weight(1f),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End
//            ) {
//                Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "$totalGold")
//            }
//            IconButton(
//                modifier = Modifier.weight(0.5f),
//                onClick = { onArrowClicked() },
//                interactionSource = remember { MutableInteractionSource() },
//            ) {
//                Icon(
//                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                    contentDescription = "펼치기"
//                )
//            }
//        }
//
//        raidContent()
//
//        IconButton(
//            modifier = Modifier.fillMaxWidth(),
//            onClick = { onArrowClicked() }
//        ) {
//            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "접기")
//        }
//    }
//
//    Spacer(modifier = Modifier.height(16.dp))
//
//}

@Composable
fun RaidCard(
    raidType: String,
    raidImg: ImageVector,
    totalGold: Int,
    raidContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.weight(0.5f),
            imageVector = raidImg,
            contentDescription = "$raidType 이미지"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = raidType
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$totalGold")
        }

    }

    raidContent()

}
