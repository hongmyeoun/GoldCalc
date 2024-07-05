package com.hongmyeoun.goldcalc.view.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.search.content.SearchContent
import com.hongmyeoun.goldcalc.view.search.topbar.SearchTopBar
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
fun SearchView(
    navController: NavHostController,
    viewModel: SearchVM = hiltViewModel()
) {
    val isFocus by viewModel.isFocus.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    BackOnPress(
        isFocus = isFocus,
        keyboardController = keyboardController,
        focusState = focusState,
        navController = navController,
    )

    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            SearchTopBar(
                viewModel = viewModel,
                isFocus = isFocus,
                scrollState = scrollState,
                keyboardController = keyboardController,
                focusState = focusState
            )
        },
        containerColor = ImageBG
    ) { paddingValues ->
        SearchContent(
            paddingValues = paddingValues,
            viewModel = viewModel,
            navController = navController,
            scrollState = scrollState,
            onTap = {
                keyboardController?.hide()
                focusState.clearFocus()
            }
        )
    }
}

@Composable
private fun BackOnPress(
    isFocus: Boolean,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager,
    navController: NavHostController
) {
    BackHandler {
        if (isFocus) {
            keyboardController?.hide()
            focusState.clearFocus()
        } else {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Search.route) {
                    inclusive = true
                }
            }
        }
    }
}
