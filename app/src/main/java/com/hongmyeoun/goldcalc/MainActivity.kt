package com.hongmyeoun.goldcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.hongmyeoun.goldcalc.viewModel.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.CommandBossVM

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

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            val height = if (cbVM.expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)

            Card(
                modifier = Modifier
                    .animateContentSize(animationSpec = tween(durationMillis = 1500))
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
                        contentDescription = "군단장레이드 이미지"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "군단장"
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${cbVM.totalGold}")
                    }
                    IconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = { cbVM.expand() },
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        Icon(
                            imageVector = if (cbVM.expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "펼치기"
                        )
                    }
                }

                val valtan = twoPhaseBoss(
                    name = cbVM.valtan,
                    seeMoreGold = cbVM.valtanSMG,
                    clearGold = cbVM.valtanCG
                )
                val biackiss = twoPhaseBoss(
                    name = cbVM.bi,
                    seeMoreGold = cbVM.biSMG,
                    clearGold = cbVM.biCG
                )
                val koukuSaton = threePhaseBoss(
                    name = cbVM.kuku,
                    seeMoreGold = cbVM.kukuSMG,
                    clearGold = cbVM.kukuCG
                )
                val abrelshud = fourPhaseBoss(
                    name = cbVM.abrel,
                    seeMoreGold = cbVM.abrelSMG,
                    clearGold = cbVM.abrelCG
                )
                val illiakan = threePhaseBoss(
                    name = cbVM.illi,
                    seeMoreGold = cbVM.illiSMG,
                    clearGold = cbVM.illiCG
                )
                val kamen = fourPhaseBoss(
                    name = cbVM.kamen,
                    seeMoreGold = cbVM.kamenSMG,
                    clearGold = cbVM.kamenCG
                )

                cbVM.sumGold(valtan, biackiss, koukuSaton, abrelshud, illiakan, kamen)

            }
        }
        item {
            val height = if (adVM.expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)

            Card(
                modifier = Modifier
                    .animateContentSize()
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
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${adVM.totalGold}")
                    }
                    IconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = { adVM.expand() },
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        Icon(
                            imageVector = if (adVM.expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "펼치기"
                        )
                    }
                }

                val kayangel = threePhaseBoss(
                    name = adVM.kayang,
                    seeMoreGold = adVM.kayangSMG,
                    clearGold = adVM.kayangCG
                )
                val ivoryTower = fourPhaseBoss(
                    name = adVM.ivT,
                    seeMoreGold = adVM.ivTSMG,
                    clearGold = adVM.ivTCG
                )

                adVM.sumGold(kayangel, ivoryTower)
            }
        }
//        item {
//            var expanded by remember { mutableStateOf(false) }
//            var totalGold by remember { mutableStateOf(0) }
//            val height = if (expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)
//
//            Card(
//                modifier = Modifier
//                    .animateContentSize()
//                    .then(height)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        modifier = Modifier.weight(0.5f),
//                        imageVector = Icons.Default.AccountBox,
//                        contentDescription = "카제로스레이드 이미지"
//                    )
//                    Text(
//                        modifier = Modifier.weight(1f),
//                        text = "카제로스레이드"
//                    )
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = "$totalGold")
//                    }
//                    IconButton(
//                        modifier = Modifier.weight(0.5f),
//                        onClick = { expanded = !expanded },
//                        interactionSource = remember { MutableInteractionSource() },
//                    ) {
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                            contentDescription = "펼치기"
//                        )
//                    }
//                }
//
//                val echidna = twoPhaseBoss(name = "에키드나", seeMoreGold = listOf(2200, 3400, 2800, 4100), clearGold = listOf(5000, 9500, 6000, 12500))
//
//                totalGold = echidna
//            }
//        }
//        item {
//            var expanded by remember { mutableStateOf(false) }
//            var totalGold by remember { mutableStateOf(0) }
//            val height = if (expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)
//
//            Card(
//                modifier = Modifier
//                    .animateContentSize()
//                    .then(height)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        modifier = Modifier.weight(0.5f),
//                        imageVector = Icons.Default.AccountBox,
//                        contentDescription = "에픽레이드 이미지"
//                    )
//                    Text(
//                        modifier = Modifier.weight(1f),
//                        text = "에픽레이드"
//                    )
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = "$totalGold")
//                    }
//                    IconButton(
//                        modifier = Modifier.weight(0.5f),
//                        onClick = { expanded = !expanded },
//                        interactionSource = remember { MutableInteractionSource() },
//                    ) {
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                            contentDescription = "펼치기"
//                        )
//                    }
//                }
//
//                val behemoth = twoPhaseBoss(name = "베히모스", seeMoreGold = listOf(3100, 4900, 3100, 4900), clearGold = listOf(7000, 14500, 7000, 14500))
//
//                totalGold = behemoth
//            }
//        }


    }
}

@Composable
fun GoldSettingCard(viewModel: CommandBossVM){
    val height = if (viewModel.expanded) Modifier.wrapContentHeight() else Modifier.height(80.dp)

    Card(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(durationMillis = 1500))
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
                contentDescription = "군단장레이드 이미지"
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "군단장"
            )
            Row(
                modifier = Modifier.weight(1f),
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

        val valtan = twoPhaseBoss(
            name = viewModel.valtan,
            seeMoreGold = viewModel.valtanSMG,
            clearGold = viewModel.valtanCG
        )
        val biackiss = twoPhaseBoss(
            name = viewModel.bi,
            seeMoreGold = viewModel.biSMG,
            clearGold = viewModel.biCG
        )
        val koukuSaton = threePhaseBoss(
            name = viewModel.kuku,
            seeMoreGold = viewModel.kukuSMG,
            clearGold = viewModel.kukuCG
        )
        val abrelshud = fourPhaseBoss(
            name = viewModel.abrel,
            seeMoreGold = viewModel.abrelSMG,
            clearGold = viewModel.abrelCG
        )
        val illiakan = threePhaseBoss(
            name = viewModel.illi,
            seeMoreGold = viewModel.illiSMG,
            clearGold = viewModel.illiCG
        )
        val kamen = fourPhaseBoss(
            name = viewModel.kamen,
            seeMoreGold = viewModel.kamenSMG,
            clearGold = viewModel.kamenCG
        )

        viewModel.sumGold(valtan, biackiss, koukuSaton, abrelshud, illiakan, kamen)

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
                modifier = Modifier.weight(3f),
                text = name
            )
            Row(
                modifier = Modifier.weight(1f),
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
                modifier = Modifier.weight(3f),
                text = name
            )
            Row(
                modifier = Modifier.weight(1f),
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
                modifier = Modifier.weight(3f),
                text = name
            )
            Row(
                modifier = Modifier.weight(1f),
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