package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.R

@Composable
fun RaidCard(
    raidType: String,
    raidImg: Int,
    totalGold: Int,
    raidContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.weight(0.5f),
            painter = painterResource(id = raidImg),
            contentDescription = "던전 아이콘"
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
            Image(
                painter = painterResource(id = R.drawable.gold_coins),
                contentDescription = "골드 아이콘"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$totalGold")
        }

    }

    raidContent()

}
