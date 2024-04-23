package com.hongmyeoun.goldcalc.view.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM

@Composable
fun MainScreen(
    characterListVM: CharacterListVM,
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = { MainAppTopBar(navController, characterListVM) }
    ) {
        content(
            Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

