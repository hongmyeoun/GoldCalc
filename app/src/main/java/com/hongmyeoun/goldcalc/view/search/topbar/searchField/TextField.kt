package com.hongmyeoun.goldcalc.view.search.topbar.searchField

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TextField(
    isFocus: Boolean,
    viewModel: SearchVM,
    scrollState: LazyListState,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager,
    onGloballyPositioned: (LayoutCoordinates) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val characterName by viewModel.characterName.collectAsState()

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = CharacterEmblemBG,
                shape = viewModel.animatedShape(isFocus = isFocus)
            )
            .onFocusChanged { viewModel.focusChange(it.isFocused) }
            .onGloballyPositioned { coordinates ->
                onGloballyPositioned(coordinates)
            },
        value = characterName,
        onValueChange = { viewModel.onCharacterNameValueChange(it) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.onDone()
                scope.launch { scrollState.animateScrollToItem(0) } // 검색결과를 0으로
                keyboardController?.hide()
                focusState.clearFocus()
            }
        ),
        placeholder = { PlaceHolder(isFocus) },
        trailingIcon = if (isFocus) {
            { TrailingIcon(characterName, viewModel, keyboardController, focusState, scrollState, scope) }
        } else {
            null
        },
        shape = viewModel.animatedShape(isFocus = isFocus),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            focusedContainerColor = LightGrayTransBG,
            focusedBorderColor = Color.Unspecified,

            unfocusedContainerColor = LightGrayTransBG,
            unfocusedBorderColor = Color.Unspecified
        ),
    )

}

@Composable
private fun PlaceHolder(isFocus: Boolean) {
    if (!isFocus) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "캐릭터 검색",
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = Icons.Default.Search, contentDescription = "검색")
        }
    } else {
        Text(
            text = "캐릭터 검색",
            color = Color.LightGray,
        )
    }
}

@Composable
private fun TrailingIcon(
    characterName: String,
    viewModel: SearchVM,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager,
    scrollState: LazyListState,
    scope: CoroutineScope
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (characterName.isNotEmpty()) {
            Image(
                modifier = Modifier.clickable { viewModel.characterClear() },
                painter = painterResource(id = R.drawable.baseline_cancel_24),
                contentDescription = "이름 초기화",
                contentScale = ContentScale.Crop
            )
        }
        IconButton(
            onClick = {
                viewModel.onDone()
                scope.launch { scrollState.animateScrollToItem(0) }
                keyboardController?.hide()
                focusState.clearFocus()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.White,
                contentDescription = "검색"
            )
        }
    }
}