package com.hongmyeoun.goldcalc.view.home.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.home.topbar.progressText.ProgressText
import com.hongmyeoun.goldcalc.viewModel.home.HomeVM

@Composable
fun HomeTopBar(
    navController: NavHostController,
    viewModel: HomeVM,
    snackbarHostState: SnackbarHostState
) {
    Column(
        modifier = Modifier
            .background(LightGrayBG)
            .padding(16.dp)
    ) {
        TitleAndMenu(navController, viewModel, snackbarHostState)
        ProgressText(viewModel)
        ProgressBar(viewModel)
        CurrentGold(viewModel)
    }
}