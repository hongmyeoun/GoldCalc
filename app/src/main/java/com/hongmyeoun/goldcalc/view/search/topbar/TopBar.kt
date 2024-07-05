package com.hongmyeoun.goldcalc.view.search.topbar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.hongmyeoun.goldcalc.view.search.topbar.searchField.SearchField
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
fun SearchTopBar(
    viewModel: SearchVM,
    isFocus: Boolean,
    scrollState: LazyListState,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager
) {
    val selectedName by viewModel.longPressName.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
        WarningDialog(
            viewModel = viewModel,
            title = selectedName
        )
    }

    SearchField(
        viewModel = viewModel,
        isFocus = isFocus,
        scrollState = scrollState,
        keyboardController = keyboardController,
        focusState = focusState
    )
}