package com.hongmyeoun.goldcalc.view.search.topbar.searchField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
fun SearchField(
    viewModel: SearchVM,
    isFocus: Boolean,
    scrollState: LazyListState,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager
) {
    var textFieldSize by remember { mutableStateOf(Rect.Zero) }

    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
    ) {
        TextField(
            isFocus = isFocus,
            viewModel = viewModel,
            scrollState = scrollState,
            keyboardController = keyboardController,
            focusState = focusState,
            onGloballyPositioned = { coordinates ->
                textFieldSize = coordinates.boundsInParent()
            }
        )
        Histories(
            textFieldSize = textFieldSize,
            isFocus = isFocus,
            viewModel = viewModel,
            scrollState = scrollState,
            keyboardController = keyboardController,
            focusState = focusState
        )
    }
}