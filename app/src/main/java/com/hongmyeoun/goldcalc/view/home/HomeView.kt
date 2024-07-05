package com.hongmyeoun.goldcalc.view.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.view.home.content.HomeContent
import com.hongmyeoun.goldcalc.view.home.topbar.HomeTopBar
import com.hongmyeoun.goldcalc.viewModel.home.HomeVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    viewModel: HomeVM = hiltViewModel(),
    navController: NavHostController,
    characterRepository: CharacterRepository,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val characterList by viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    BackOnPressed(snackbarHostState)

    Scaffold(
        topBar = { HomeTopBar(navController, viewModel, snackbarHostState) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        HomeContent(
            paddingValues = paddingValues,
            characterList = characterList,
            characterRepository = characterRepository,
            navController = navController,
            isLoading = isLoading,
        )
    }
}

@Composable
fun BackOnPressed(snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    var backPressedTime = 0L
    val scope = rememberCoroutineScope()

    BackHandler {
        if (System.currentTimeMillis() - backPressedTime <= 2000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            scope.launch {
                val job = launch {
                    snackbarHostState.showSnackbar(message = "한 번 더 누르시면 앱이 종료됩니다.")
                }
                delay(2000L)
                job.cancel()
            }
        }
        backPressedTime = System.currentTimeMillis()
    }
}
