package com.hongmyeoun.goldcalc.view.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.view.setting.cardContent.AbyssDungeon
import com.hongmyeoun.goldcalc.view.setting.cardContent.CommandRaid
import com.hongmyeoun.goldcalc.view.setting.cardContent.EpicRaid
import com.hongmyeoun.goldcalc.view.setting.cardContent.KazerothRaid
import com.hongmyeoun.goldcalc.viewModel.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.KazerothRaidVM

@Composable
fun Setting(
    viewModel: GoldSettingVM = viewModel(),
    cbVM: CommandBossVM = viewModel(),
    adVM: AbyssDungeonVM = viewModel(),
    kzVM: KazerothRaidVM = viewModel(),
    epVM: EpicRaidVM = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        RaidCard(
            raidType = "군단장 레이드",
            raidImg = Icons.Default.AccountBox,
            totalGold = cbVM.totalGold,
            expanded = cbVM.expanded,
            onArrowClicked = { cbVM.expand() }
        ) {
            CommandRaid(viewModel = cbVM)
        }
        RaidCard(
            raidType = "어비스 던전",
            raidImg = Icons.Default.AccountBox,
            totalGold = adVM.totalGold,
            expanded = adVM.expanded,
            onArrowClicked = { adVM.expand() },
        ) {
            AbyssDungeon(viewModel = adVM)
        }
        RaidCard(
            raidType = "카제로스 레이드",
            raidImg = Icons.Default.AccountBox,
            totalGold = kzVM.totalGold,
            expanded = kzVM.expanded,
            onArrowClicked = { kzVM.expand() }
        ) {
            KazerothRaid(viewModel = kzVM)
        }
        RaidCard(
            raidType = "에픽 레이드",
            raidImg = Icons.Default.AccountBox,
            totalGold = epVM.totalGold,
            expanded = epVM.expanded,
            onArrowClicked = { epVM.expand() }
        ) {
            EpicRaid(viewModel = epVM)
        }

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
                    onDone = { keyboardController?.hide() }
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
                    onDone = { keyboardController?.hide() }
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Text(text = "주간 수입")
                Text(text = "${viewModel.calcTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)}")
            }
        }
    }
}
