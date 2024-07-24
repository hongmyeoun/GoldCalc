package com.hongmyeoun.goldcalc.view.addScreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.view.addScreen.bottomBar.AddScreenBottomBar
import com.hongmyeoun.goldcalc.view.addScreen.content.AddScreenContent
import com.hongmyeoun.goldcalc.view.addScreen.topBar.AddScreenTopBar
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun AddScreenView(
    navController: NavHostController,
    viewModel: AddScreenVM = hiltViewModel()
) {
    Scaffold(
        topBar = { AddScreenTopBar(navController) },
        bottomBar = { AddScreenBottomBar(navController, viewModel) },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        AddScreenContent(paddingValues, viewModel)
    }
}
