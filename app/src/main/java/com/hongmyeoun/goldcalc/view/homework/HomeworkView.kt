package com.hongmyeoun.goldcalc.view.homework

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.view.homework.bottomBar.HomeworkBottomBar
import com.hongmyeoun.goldcalc.view.homework.content.HomeworkContent
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.HomeworkVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import kotlinx.coroutines.delay

@Composable
fun HomeworkView(
    navController: NavHostController,
    characterRepository: CharacterRepository,
    charName: String
) {
    val viewModel = remember { HomeworkVM(characterRepository, charName) }
    val character by viewModel.character.collectAsState()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1000)
        isLoading = false
    }

    Crossfade(
        targetState = isLoading,
        label = "로딩 Crossfade"
    ) { loading ->
        if (loading) {
            LoadingScreen()
        } else {
            val cbVM = remember { CommandBossVM(character) }
            val adVM = remember { AbyssDungeonVM(character) }
            val kzVM = remember { KazerothRaidVM(character) }
            val epVM = remember { EpicRaidVM(character) }

            Homework(
                viewModel = viewModel,
                cbVM = cbVM,
                adVM = adVM,
                kzVM = kzVM,
                epVM = epVM,
                navController = navController
            )
        }
    }
}

@Composable
private fun Homework(
    viewModel: HomeworkVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    navController: NavHostController
) {
    LaunchedEffect(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold, viewModel.plusGold, viewModel.minusGold, viewModel.selectedTab) {
        viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)
    }

    val scrollState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ImageBG,
        topBar = { HomeworkTopBar(viewModel, navController, scrollState) },
        bottomBar = { HomeworkBottomBar(viewModel, cbVM, adVM, kzVM, epVM, navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        HomeworkContent(paddingValues, viewModel, scrollState, snackbarHostState, cbVM, adVM, kzVM, epVM)
    }
}