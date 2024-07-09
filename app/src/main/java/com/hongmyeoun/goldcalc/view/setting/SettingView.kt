package com.hongmyeoun.goldcalc.view.setting

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.setting.reorderPage.ReorderBottomBar
import com.hongmyeoun.goldcalc.view.setting.reorderPage.ReorderPageContent
import com.hongmyeoun.goldcalc.view.setting.reorderPage.ReorderPageTopBar
import com.hongmyeoun.goldcalc.view.setting.settingPage.SettingTopBar
import com.hongmyeoun.goldcalc.view.setting.settingPage.content.SettingContent
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SettingView(
    navController: NavHostController,
    viewModel: SettingVM = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val reorderPage by viewModel.reorderPage.collectAsState()

    BackOnPress(reorderPage, viewModel)

    Scaffold(
        topBar = { if (reorderPage) ReorderPageTopBar(viewModel = viewModel) else SettingTopBar(navController) },
        bottomBar = { if (reorderPage) ReorderBottomBar(viewModel, snackbarHostState) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = ImageBG
    ) {
        Crossfade(
            targetState = reorderPage,
            label = Labels.Crossfade.Loading
        ) { isReorderPage ->
            if (isReorderPage) {
                ReorderPageContent(it, viewModel)
            } else {
                SettingContent(it, viewModel, snackbarHostState)
            }
        }
    }
}

@Composable
fun BackOnPress(reorderPage: Boolean, viewModel: SettingVM) {
    if (reorderPage) {
        BackHandler {
            viewModel.closeReorderPage()
        }
    }
}
