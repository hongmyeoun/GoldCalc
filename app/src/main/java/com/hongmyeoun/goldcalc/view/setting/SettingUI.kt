package com.hongmyeoun.goldcalc.view.setting

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.BuildConfig
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.setting.SettingModel.clearAppCache
import com.hongmyeoun.goldcalc.model.setting.SettingModel.getCacheSize
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightBlue
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SettingUI(
    navController: NavHostController,
    viewModel: SettingVM = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val editOderPage by viewModel.editOrderPage.collectAsState()

    Scaffold(
        topBar = { if (editOderPage) EditOrderPageTopBar(viewModel = viewModel) else SettingTopBar(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = ImageBG
    ) {
        if (editOderPage) {
            EditOrderPageContent(it, viewModel)
        } else {
            SettingContent(it, viewModel, snackbarHostState)
        }
    }
}

@Composable
private fun SettingContent(
    it: PaddingValues,
    viewModel: SettingVM,
    snackbarHostState: SnackbarHostState,
) {
    val showResetDialog by viewModel.showResetDialog.collectAsState()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()


    if (showResetDialog) {
        Dialog(
            title = "숙제",
            action = "초기화",
            viewModel = viewModel,
            onConfirm = { viewModel.onHomeworkReset() }
        )
    }

    if (showDeleteDialog) {
        Dialog(
            title = "캐릭터",
            action = "일괄 삭제",
            viewModel = viewModel,
            onConfirm = { viewModel.onDeleteAll() }
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            SettingItemBox(
                title = "캐릭터"
            ) {
                SettingItem(
                    itemTitle = "숙제 초기화",
                    icon = R.drawable.baseline_settings_backup_restore_24,
                    onClicked = { viewModel.showResetDailog() }
                )

                SettingItem(
                    itemTitle = "캐릭터 순서 변경",
                    icon = R.drawable.baseline_sort,
                    onClicked = { viewModel.openEditOrderPage() }
                )

                SettingItem(
                    itemTitle = "캐릭터 일괄 삭제",
                    icon = R.drawable.baseline_delete_sweep,
                    onClicked = { viewModel.showDeleteDialog() }
                )
            }
        }

        item {
            SettingItemBox(
                title = "앱 설정"
            ) {
                SettingItemCacheClear(
                    icon = R.drawable.outline_cleaning_services,
                    snackbarHostState = snackbarHostState
                )

                SettingItem(
                    itemTitle = "업데이트 확인",
                    icon = R.drawable.outline_restore,
                    onClicked = { }
                )
            }
        }

        item {
            SettingItemBox(
                title = "앱 버전"
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                    text = BuildConfig.VERSION_NAME,
                    style = normalTextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}

@Composable
private fun SettingItemBox(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        text = title,
        style = titleTextStyle(fontSize = 15.sp)
    )
    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content()
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SettingItem(
    itemTitle: String,
    icon: Int,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = Color.White,
            contentDescription = "아이콘"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = itemTitle,
            style = normalTextStyle(fontSize = 15.sp)
        )
    }
}

@Composable
private fun SettingItemCacheClear(
    icon: Int,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    var cacheSize by remember { mutableStateOf(getCacheSize(context)) }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = cacheSize > 0) {
                clearAppCache(context) {
                    doneSnackbar(
                        snackbarHostState = snackbarHostState,
                        scope = scope,
                        text = "캐쉬가 정리되었습니다."
                    )
                    cacheSize = getCacheSize(context)
                }
            }
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = Color.White,
                contentDescription = "아이콘"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "캐쉬 삭제",
                style = normalTextStyle(fontSize = 15.sp)
            )
        }

        Text(
            text = "${cacheSize / 1024} KB",
            style = normalTextStyle(color = Color.Gray, fontSize = 13.sp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun SettingTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.weight(0.5f),
            onClick = {
                navController.navigate("Main") {
                    popUpTo("Setting") {
                        inclusive = true
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "뒤로",
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            text = "설정",
            style = titleTextStyle(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .width(32.dp)
                .weight(0.5f)
        )
    }
}

@Composable
private fun EditOrderPageTopBar(
    viewModel: SettingVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.weight(0.5f),
            onClick = { viewModel.onSave() }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "뒤로",
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            text = "캐릭터 순서 변경",
            style = titleTextStyle(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .width(32.dp)
                .weight(0.5f)
        )
    }
}


@Composable
private fun Dialog(
    title: String,
    action: String,
    viewModel: SettingVM,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = { viewModel.onDissmissRequest() }) {

        Column(
            modifier = Modifier.background(LightGrayBG, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = LightBlue,
                            fontSize = 20.sp
                        )
                    ) {
                        append(title)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append("를 ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    ) {
                        append(action)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append(" 하시겠습니까?")
                    }

                },
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDissmissRequest()
                    },
                ) {
                    Text(text = "취소")
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDissmissRequest()
                        onConfirm()
                    },
                ) {
                    Text(
                        text = action,
                        color = Color.Red
                    )
                }

            }
        }
    }
}


fun doneSnackbar(
    snackbarHostState: SnackbarHostState,
    text: String,
    scope: CoroutineScope
) {
    scope.launch {
        val job = launch {
            snackbarHostState.showSnackbar(message = text)
        }
        delay(2000L)
        job.cancel()
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditOrderPageContent(it: PaddingValues, viewModel: SettingVM) {
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
            .padding(top = 12.dp)
        ,
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list, key = { item -> item.name }) {
            ReorderableItem(reorderableLazyListState, key = it.name) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = LightGrayBG,
                    shadowElevation = elevation
                ) {
                    CharacterListItem(character = it) { modifier ->
                        IconButton(
                            modifier = modifier.draggableHandle(
                                onDragStarted = {
                                    view.performHapticFeedback(HapticFeedbackConstants.DRAG_START)
                                },
                                onDragStopped = {
                                    view.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                                    viewModel.dragStopSave(list)
                                },
                            ),
                            onClick = {  },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                tint = Color.White,
                                contentDescription = "Reorder"
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterListItem(
    character: Character,
    iconUI: @Composable (Modifier) -> Unit
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

        iconUI(Modifier.weight(0.3f))
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun ListPreview() {
//    DragAndDropList()
}