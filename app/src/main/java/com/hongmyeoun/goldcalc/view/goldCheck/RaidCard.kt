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
