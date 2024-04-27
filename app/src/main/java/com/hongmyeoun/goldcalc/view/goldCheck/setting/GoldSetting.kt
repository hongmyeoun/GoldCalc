package com.hongmyeoun.goldcalc.view.goldCheck.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun GoldSetting(
    navController: NavHostController,
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
) {
    LaunchedEffect(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold, viewModel.plusGold, viewModel.minusGold, viewModel.selectedTab) {
        viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { GoldSettingTopBar(viewModel, navController) },
        bottomBar = { GoldSettingBottomBar(viewModel, cbVM, adVM, kzVM, epVM, navController) }
    ) { paddingValues ->
        GoldSettingContent(paddingValues, viewModel, cbVM, adVM, kzVM, epVM)
    }

}
