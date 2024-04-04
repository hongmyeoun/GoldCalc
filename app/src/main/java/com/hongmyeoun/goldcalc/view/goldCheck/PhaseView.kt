package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun phase(
    gate: String,
    seeMoreGoldN: Int,
    seeMoreGoldH: Int,
    clearGoldN: Int,
    clearGoldH: Int,
    onUpdateTotalGold: (Int) -> Unit
) {
    var level by remember { mutableStateOf("노말") }
    var seeMoreCheck by remember { mutableStateOf(false) }
    var clearCheck by remember { mutableStateOf(false) }
    var seeMoreGold by remember { mutableStateOf(0) }
    var clearGold by remember { mutableStateOf(0) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 16.dp),
                text = gate
            )
            OutlinedButton(
                onClick = {
                    level = if (level == "노말") "하드" else "노말"
                    seeMoreGold = if (seeMoreCheck && level == "노말") seeMoreGoldN else if (seeMoreCheck) seeMoreGoldH else 0
                    clearGold = if (clearCheck && level == "노말") clearGoldN else if (clearCheck) clearGoldH else 0
                    onUpdateTotalGold(clearGold - seeMoreGold)
                }
            ) {
                Text(text = level)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = clearCheck,
                    onCheckedChange = {
                        clearCheck = !clearCheck
                        clearGold = if (clearCheck && level == "노말") clearGoldN else if (clearCheck && level == "하드") clearGoldH else 0
                        onUpdateTotalGold(clearGold - seeMoreGold)
                    }
                )
                Text(text = "클리어")
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = seeMoreCheck,
                    onCheckedChange = {
                        seeMoreCheck = !seeMoreCheck
                        seeMoreGold = if (seeMoreCheck && level == "노말") seeMoreGoldN else if (seeMoreCheck && level == "하드") seeMoreGoldH else 0
                        onUpdateTotalGold(clearGold - seeMoreGold)
                    }
                )
                Text(text = "더보기")
            }
        }
    }
}

@Composable
fun twoPhaseBoss(
    name: String,
    seeMoreGold: List<Int>,
    clearGold: List<Int>,
    totalGold: Int,
    onUpdateTotalGoldOnePhase: (Int) -> Unit,
    onUpdateTotalGoldTwoPhase: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "보스 이미지")
            Text(
                modifier = Modifier.weight(2f),
                text = name
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
        phase(
            gate = "1 관문",
            seeMoreGoldN = seeMoreGold[0],
            seeMoreGoldH = seeMoreGold[2],
            clearGoldN = clearGold[0],
            clearGoldH = clearGold[2],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldOnePhase(updated)
            }
        )
        phase(
            gate = "2 관문",
            seeMoreGoldN = seeMoreGold[1],
            seeMoreGoldH = seeMoreGold[3],
            clearGoldN = clearGold[1],
            clearGoldH = clearGold[3],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldTwoPhase(updated)
            }
        )
    }
}


@Composable
fun threePhaseBoss(
    name: String,
    seeMoreGold: List<Int>,
    clearGold: List<Int>,
    totalGold: Int,
    onUpdateTotalGoldOnePhase: (Int) -> Unit,
    onUpdateTotalGoldTwoPhase: (Int) -> Unit,
    onUpdateTotalGoldThreePhase: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "보스 이미지")
            Text(
                modifier = Modifier.weight(2f),
                text = name
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
        phase(
            gate = "1 관문",
            seeMoreGoldN = seeMoreGold[0],
            seeMoreGoldH = seeMoreGold[3],
            clearGoldN = clearGold[0],
            clearGoldH = clearGold[3],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldOnePhase(updated)
            }
        )
        phase(
            gate = "2 관문",
            seeMoreGoldN = seeMoreGold[1],
            seeMoreGoldH = seeMoreGold[4],
            clearGoldN = clearGold[1],
            clearGoldH = clearGold[4],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldTwoPhase(updated)
            }
        )
        phase(
            gate = "3 관문",
            seeMoreGoldN = seeMoreGold[2],
            seeMoreGoldH = seeMoreGold[5],
            clearGoldN = clearGold[2],
            clearGoldH = clearGold[5],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldThreePhase(updated)
            }
        )
    }
}

@Composable
fun fourPhaseBoss(
    name: String,
    seeMoreGold: List<Int>,
    clearGold: List<Int>,
    totalGold: Int,
    onUpdateTotalGoldOnePhase: (Int) -> Unit,
    onUpdateTotalGoldTwoPhase: (Int) -> Unit,
    onUpdateTotalGoldThreePhase: (Int) -> Unit,
    onUpdateTotalGoldFourPhase: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "보스 이미지")
            Text(
                modifier = Modifier.weight(2f),
                text = name
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
        phase(
            gate = "1 관문",
            seeMoreGoldN = seeMoreGold[0],
            seeMoreGoldH = seeMoreGold[4],
            clearGoldN = clearGold[0],
            clearGoldH = clearGold[4],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldOnePhase(updated)
            }
        )
        phase(
            gate = "2 관문",
            seeMoreGoldN = seeMoreGold[1],
            seeMoreGoldH = seeMoreGold[5],
            clearGoldN = clearGold[1],
            clearGoldH = clearGold[5],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldTwoPhase(updated)
            }
        )
        phase(
            gate = "3 관문",
            seeMoreGoldN = seeMoreGold[2],
            seeMoreGoldH = seeMoreGold[6],
            clearGoldN = clearGold[2],
            clearGoldH = clearGold[6],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldThreePhase(updated)
            }
        )
        phase(
            gate = "4 관문",
            seeMoreGoldN = seeMoreGold[3],
            seeMoreGoldH = seeMoreGold[7],
            clearGoldN = clearGold[3],
            clearGoldH = clearGold[7],
            onUpdateTotalGold = { updated ->
                onUpdateTotalGoldFourPhase(updated)
            }
        )
    }
}
