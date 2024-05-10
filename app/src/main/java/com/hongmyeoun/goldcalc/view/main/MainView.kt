package com.hongmyeoun.goldcalc.view.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    characterListVM: CharacterListVM,
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    BackOnPressed(snackbarHostState)

    Scaffold(
        topBar = { MainAppTopBar(navController, characterListVM) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        content(
            Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun BackOnPressed(snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    val scope = rememberCoroutineScope()

    BackHandler(enabled = backPressedState) {
        if(System.currentTimeMillis() - backPressedTime <= 1000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            scope.launch {
                snackbarHostState.showSnackbar("한 번 더 누르시면 앱이 종료됩니다.")
            }
        }
        backPressedTime = System.currentTimeMillis()
    }
}
