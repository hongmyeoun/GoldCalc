package com.hongmyeoun.goldcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.view.AbyssDungeonCard
import com.hongmyeoun.goldcalc.view.CommandBossCard
import com.hongmyeoun.goldcalc.view.EpicRaidCard
import com.hongmyeoun.goldcalc.view.KazerothRaidCard
import com.hongmyeoun.goldcalc.viewModel.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.KazerothRaidVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoldCalcTheme {
//                MainScreen()
                Setting()
            }
        }
    }
}

@Composable
fun MainScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { Character() }
    }
}

@Composable
fun Character() {
    var progressPercentage by remember { mutableStateOf(0.0f) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(0.5f),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "직업"
            )
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(text = "서버")
                Row {
                    Text(text = "닉네임")
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "골드체크")
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "설정")
                }
                Row {
                    Text(text = "레벨")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "직업")
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "진행도")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "${(progressPercentage * 100).toInt()}%")
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = progressPercentage,
                    color = Color.Green
                )
            }
        }
        Row {
            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
                Text(text = "군단장 레이드")
            }
            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
                Text(text = "어비스 던전")
            }
        }
        Row {
            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
                Text(text = "어비스 레이드")
            }
            OutlinedButton(onClick = { progressPercentage = 0.0f }) {
                Text(text = "0%")
            }
        }
    }
}

@Composable
fun Setting() {
    val cbVM = CommandBossVM()
    val adVM = AbyssDungeonVM()
    val kzVM = KazerothRaidVM()
    val epVM = EpicRaidVM()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CommandBossCard(viewModel = cbVM)
        AbyssDungeonCard(viewModel = adVM)
        KazerothRaidCard(viewModel = kzVM)
        EpicRaidCard(viewModel = epVM)
    }
}

@Composable
fun fourPhaseBoss(
    name: String,
    seeMoreGold: List<Int>,
    clearGold: List<Int>
): Int {
    var totalGold by remember { mutableStateOf(0) }

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
        val (cg1, smg1) = phase(
            gate = "1 관문",
            seeMoreGoldN = seeMoreGold[0],
            seeMoreGoldH = seeMoreGold[4],
            clearGoldN = clearGold[0],
            clearGoldH = clearGold[4]
        )
        val (cg2, smg2) = phase(
            gate = "2 관문",
            seeMoreGoldN = seeMoreGold[1],
            seeMoreGoldH = seeMoreGold[5],
            clearGoldN = clearGold[1],
            clearGoldH = clearGold[5]
        )
        val (cg3, smg3) = phase(
            gate = "3 관문",
            seeMoreGoldN = seeMoreGold[2],
            seeMoreGoldH = seeMoreGold[6],
            clearGoldN = clearGold[2],
            clearGoldH = clearGold[6]
        )
        val (cg4, smg4) = phase(
            gate = "4 관문",
            seeMoreGoldN = seeMoreGold[3],
            seeMoreGoldH = seeMoreGold[7],
            clearGoldN = clearGold[3],
            clearGoldH = clearGold[7]
        )

        totalGold = cg1 + cg2 + cg3 + cg4 - smg1 - smg2 - smg3 - smg4
    }

    return totalGold
}

@Composable
fun threePhaseBoss(
    name: String,
    seeMoreGold: List<Int>,
    clearGold: List<Int>
): Int {
    var totalGold by remember { mutableStateOf(0) }

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
        val (cg1, smg1) = phase(
            gate = "1 관문",
            seeMoreGoldN = seeMoreGold[0],
            seeMoreGoldH = seeMoreGold[3],
            clearGoldN = clearGold[0],
            clearGoldH = clearGold[3]
        )
        val (cg2, smg2) = phase(
            gate = "2 관문",
            seeMoreGoldN = seeMoreGold[1],
            seeMoreGoldH = seeMoreGold[4],
            clearGoldN = clearGold[1],
            clearGoldH = clearGold[4]
        )
        val (cg3, smg3) = phase(
            gate = "3 관문",
            seeMoreGoldN = seeMoreGold[2],
            seeMoreGoldH = seeMoreGold[5],
            clearGoldN = clearGold[2],
            clearGoldH = clearGold[5]
        )

        totalGold = cg1 + cg2 + cg3 - smg1 - smg2 - smg3
    }

    return totalGold
}


@Composable
fun twoPhaseBoss(
    name: String,
    seeMoreGold: List<Int>,
    clearGold: List<Int>
): Int {
    var totalGold by remember { mutableStateOf(0) }

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
        val (cg1, smg1) = phase(
            gate = "1 관문",
            seeMoreGoldN = seeMoreGold[0],
            seeMoreGoldH = seeMoreGold[2],
            clearGoldN = clearGold[0],
            clearGoldH = clearGold[2]
        )
        val (cg2, smg2) = phase(
            gate = "2 관문",
            seeMoreGoldN = seeMoreGold[1],
            seeMoreGoldH = seeMoreGold[3],
            clearGoldN = clearGold[1],
            clearGoldH = clearGold[3]
        )

        totalGold = cg1 + cg2 - smg1 - smg2
    }

    return totalGold
}

@Composable
fun phase(
    gate: String,
    seeMoreGoldN: Int,
    seeMoreGoldH: Int,
    clearGoldN: Int,
    clearGoldH: Int
): Pair<Int, Int> {
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
                    }
                )
                Text(text = "더보기")
            }
        }
    }
    return Pair(clearGold, seeMoreGold)
}

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    Setting()
}