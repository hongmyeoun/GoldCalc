package com.hongmyeoun.goldcalc.view.setting.reorderPage

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.DarkModeGray
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReorderPageContent(it: PaddingValues, viewModel: SettingVM) {
    val view = LocalView.current

    val characterList by viewModel.characters.collectAsState()

    var list by remember { mutableStateOf(characterList) }
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }

        view.performHapticFeedback(HapticFeedbackConstants.SEGMENT_FREQUENT_TICK)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(top = 12.dp),
        state = lazyListState,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = list, key = { item -> item.name }) { character ->
            ReorderableItem(state = reorderableLazyListState, key = character.name) { isDragging ->

                SwipeDeleteAndDraggableList(
                    isDragging = isDragging,
                    onSwipeDelete = {
                        list = list.toMutableList().apply {
                            remove(character)
                        }
                        viewModel.saveReorderList(list)
                    },
                    modifier = Modifier.animateItemPlacement(),
                    draggableHandlerModifier = Modifier
                        .draggableHandle(
                            onDragStarted = {
                                view.performHapticFeedback(HapticFeedbackConstants.DRAG_START)
                            },
                            onDragStopped = {
                                view.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                                viewModel.saveReorderList(list)
                            },
                        ),
                    character = character
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeDeleteAndDraggableList(
    isDragging: Boolean,
    onSwipeDelete: () -> Unit,
    modifier: Modifier,
    draggableHandlerModifier: Modifier,
    character: Character
) {
    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
    val surfaceBGColor = if (isDragging) DarkModeGray else LightGrayBG

    val dismissState = rememberDismissState(
        positionalThreshold = { it * 0.50f },
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.Default -> {
                    false
                }

                DismissValue.DismissedToEnd -> {
                    false
                }

                DismissValue.DismissedToStart -> {
                    onSwipeDelete()
                    true
                }
            }
        }
    )

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = { },
        dismissContent = {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = surfaceBGColor,
                shadowElevation = elevation
            ) {
                CharacterListItem(
                    character = character,
                    modifier = draggableHandlerModifier,
                )
            }
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharacterListItem(
    character: Character,
    modifier: Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Crop,
                model = CharacterResourceMapper.getClassEmblem(character.className),
                contentDescription = "직업 이미지"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = character.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${character.itemLevel} ${character.className}(${character.serverName})",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        IconButton(
            modifier = modifier.weight(0.3f),
            onClick = { },
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                tint = Color.White,
                contentDescription = "Reorder"
            )
        }

    }
}
