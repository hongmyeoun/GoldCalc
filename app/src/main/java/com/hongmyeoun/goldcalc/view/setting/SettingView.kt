package com.hongmyeoun.goldcalc.view.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.view.setting.cardContent.AbyssDungeon
import com.hongmyeoun.goldcalc.view.setting.cardContent.CommandRaid
import com.hongmyeoun.goldcalc.view.setting.cardContent.EpicRaid
import com.hongmyeoun.goldcalc.view.setting.cardContent.KazerothRaid
import com.hongmyeoun.goldcalc.viewModel.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.KazerothRaidVM

@Composable
fun Setting(
    cbVM: CommandBossVM = viewModel(),
    adVM: AbyssDungeonVM = viewModel(),
    kzVM: KazerothRaidVM = viewModel(),
    epVM: EpicRaidVM = viewModel()
) {
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
    }
}
