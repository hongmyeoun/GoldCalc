package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
//import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.AbyssDungeon
//import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.CommandRaid
//import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.EpicRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.KazerothRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.KazerothRaidTest
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVMTest

@Composable
fun Setting(
    navController: NavHostController,
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM = viewModel(),
    adVM: AbyssDungeonVM = viewModel(),
    kzVM: KazerothRaidVM = viewModel(),
    kzTVM: KazerothRaidVMTest = viewModel(),
    epVM: EpicRaidVM = viewModel()
) {
    val height = if (viewModel.expanded) Modifier.wrapContentHeight() else Modifier.height(65.dp)
    val arrowIcon = if (viewModel.expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    val character by viewModel.character.collectAsState()

    LaunchedEffect(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold, viewModel.plusGold, viewModel.minusGold) {
        viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                TopBarBox(
                    title = "군단장",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.moveCommandRaid() }
                )
                TopBarBox(
                    title = "어비스 던전",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.moveAbyssDungeon() }
                )
                TopBarBox(
                    title = "카제로스",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.moveKazeRaid() }
                )
                TopBarBox(
                    title = "에픽",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.moveEpicRaid()  }
                )
                TopBarBox(
                    title = "기타",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.moveETC() }
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .then(height)
                    .fillMaxWidth()
                    .clickable { viewModel.expand() },
                contentAlignment = Alignment.BottomCenter
            ) {
                Column {
                    Icon(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp),
                        imageVector = arrowIcon,
                        contentDescription = "펼치기, 접기"
                    )

                    if (viewModel.expanded) {
                        Row {
                            Text(text = "군단장 레이드")
                            Text(text = "10,000")
                        }
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "발탄")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "비아키스")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "쿠크세이튼")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                                Text(text = "3관 난이도 : 골드")
                            }

                            Column (modifier = Modifier.weight(1f)) {
                                Text(text = "아브렐슈드")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                                Text(text = "3관 난이도 : 골드")
                                Text(text = "4관 난이도 : 골드")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "일리아칸")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                                Text(text = "3관 난이도 : 골드")
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "카멘")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                                Text(text = "3관 난이도 : 골드")
                                Text(text = "4관 난이도 : 골드")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Text(text = "어비스 던전")
                            Text(text = "10,000")
                        }
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "카양겔")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                                Text(text = "3관 난이도 : 골드")
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "혼돈의 상아탑")
                                Text(text = "1관 난이도 : 골드")
                                Text(text = "2관 난이도 : 골드")
                                Text(text = "3관 난이도 : 골드")
                                Text(text = "4관 난이도 : 골드")
                            }
                        }

                        Row {
                            Text(text = "카제로스 레이드")
                            Text(text = "10,000")
                        }
                        Text(text = "에키드나")
                        Text(text = "1관 난이도 : 골드")
                        Text(text = "2관 난이도 : 골드")
                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Text(text = "에픽 레이드")
                            Text(text = "10,000")
                        }
                        Text(text = "베히모스")
                        Text(text = "1관 난이도 : 골드")
                        Text(text = "2관 난이도 : 골드")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier.weight(3f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "변경 전")
                                Row {
                                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "${character?.weeklyGold}")
                                }
                            }
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "화살표")
                            Column {
                                Text(text = "변경 후")
                                Row {
                                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "${viewModel.totalGold}")
                                }
                            }
                        }
                        Row(horizontalArrangement = Arrangement.End) {
                            OutlinedButton(onClick = { navController.popBackStack() }) {
                                Text("취소")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    viewModel.onDoneClick()
                                    navController.popBackStack()
                                }
                            ) {
                                Text(text = "완료")
                            }
                        }
                    }
                }
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (viewModel.selectedTab) {
                "군단장" -> {
//                    RaidCard(
//                        raidType = "군단장 레이드",
//                        raidImg = Icons.Default.AccountBox,
//                        totalGold = cbVM.totalGold
//                    ) {
//                        CommandRaid(viewModel = cbVM)
//                    }
                }
                "어비스 던전" -> {
//                    RaidCard(
//                        raidType = "어비스 던전",
//                        raidImg = Icons.Default.AccountBox,
//                        totalGold = adVM.totalGold,
//                    ) {
//                        AbyssDungeon(viewModel = adVM)
//                    }
                }
                "카제로스" -> {
                    RaidCard(
                        raidType = "카제로스 레이드",
                        raidImg = Icons.Default.AccountBox,
                        totalGold = kzVM.totalGold
                    ) {
                        KazerothRaid(viewModel = kzVM)
                    }
                }
                "에픽" -> {
                    RaidCard(
                        raidType = "카제로스 레이드",
                        raidImg = Icons.Default.AccountBox,
                        totalGold = kzVM.totalGold
                    ) {
                        KazerothRaidTest(viewModel = kzTVM)
                    }
//                    RaidCard(
//                        raidType = "에픽 레이드",
//                        raidImg = Icons.Default.AccountBox,
//                        totalGold = epVM.totalGold
//                    ) {
//                        EpicRaid(viewModel = epVM)
//                    }
                }
                "기타" -> {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.plusGold,
                            onValueChange = { viewModel.plusGoldValue(it) },
                            label = { Text(text = "추가 골드") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)
                                    keyboardController?.hide()
                                    focusState.clearFocus()
                                }
                            )
                        )

                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.minusGold,
                            onValueChange = { viewModel.minusGoldValue(it) },
                            label = { Text(text = "사용 골드") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)
                                    keyboardController?.hide()
                                    focusState.clearFocus()
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarBox(
    title: String,
    modifier: Modifier,
    onClick: ()->Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                shape = RectangleShape,
                color = Color.LightGray
            )
            .clickable { onClick() }
        ,
        contentAlignment = Alignment.Center
    ){
        Text(text = title)
    }

}
