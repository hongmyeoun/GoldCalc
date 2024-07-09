package com.hongmyeoun.goldcalc.view.search.topbar.searchField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM
import kotlinx.coroutines.launch

@Composable
fun Histories(
    textFieldSize: Rect,
    isFocus: Boolean,
    viewModel: SearchVM,
    scrollState: LazyListState,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager
) {
    val scope = rememberCoroutineScope()

    val histories by viewModel.histories.collectAsState()

    Popup(
        offset = IntOffset(x = 0, y = textFieldSize.height.toInt()),
    ) {
        AnimatedVisibility(
            visible = isFocus,
            enter = fadeIn(animationSpec = tween(durationMillis = 100)) + expandVertically(animationSpec = tween(durationMillis = 250)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100)) + shrinkVertically(animationSpec = tween(durationMillis = 250))
        ) {
            LazyColumn(
                modifier = Modifier
                    .heightIn(max = 360.dp)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .background(
                        color = ImageBG.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = CharacterEmblemBG,
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .clip(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
            ) {
                items(histories, key = { item -> item.charName }) { history ->
                    Row(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        viewModel.unFocus()
                                        viewModel.onCharacterNameValueChange(history.charName)
                                        viewModel.onDone()
                                        scope.launch { scrollState.animateScrollToItem(0) }
                                        keyboardController?.hide()
                                        focusState.clearFocus()
                                    },
                                    onLongPress = {
                                        viewModel.showDialog(history.charName)
                                    }
                                )
                            }
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.weight(0.2f),
                            painter = painterResource(id = R.drawable.outline_restore),
                            tint = Color.White,
                            contentDescription = "기록"
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            modifier = Modifier.weight(1f),
                            text = history.charName,
                            style = normalTextStyle(fontSize = 14.sp)
                        )

                        IconButton(
                            onClick = { viewModel.onCharacterNameValueChange(history.charName) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_north_west),
                                tint = Color.White,
                                contentDescription = "text 변환"
                            )
                        }
                    }
                }
            }
        }
    }
}