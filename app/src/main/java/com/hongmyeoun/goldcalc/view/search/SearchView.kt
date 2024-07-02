package com.hongmyeoun.goldcalc.view.search

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.LoadingScreen
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterInfo
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
fun SearchUI(
    navController: NavHostController,
    viewModel: SearchVM = hiltViewModel()
) {
    val isFocus by viewModel.isFocus.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    BackHandler {
        if (isFocus) {
            keyboardController?.hide()
            focusState.clearFocus()
        } else {
            navController.navigate("Main") {
                popUpTo("Search") {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SearchTextField(
                viewModel = viewModel,
                isFocus = isFocus,
                keyboardController = keyboardController,
                focusState = focusState
            )
        },
        containerColor = ImageBG
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            keyboardController?.hide()
                            focusState.clearFocus()
                        }
                    )
                }
                .background(ImageBG)
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SearchResult(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
private fun SearchTextField(
    viewModel: SearchVM,
    isFocus: Boolean,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager
) {
    val characterName by viewModel.characterName.collectAsState()
    val context = LocalContext.current

    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Rect.Zero) }

    val selectedName by viewModel.longPressName.collectAsState()

    val histories by viewModel.hisotries.collectAsState()

    val bottomStart by animateDpAsState(targetValue = if (isFocus) 0.dp else 16.dp, animationSpec = tween(durationMillis = 300), label = "")
    val bottomEnd by animateDpAsState(targetValue = if (isFocus) 0.dp else 16.dp, animationSpec = tween(durationMillis = 300), label = "")

    val borderShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = bottomStart, bottomEnd = bottomEnd)

    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
        DeleteSearchHistoryDialog(
            viewModel = viewModel,
            title = selectedName
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = CharacterEmblemBG,
                    shape = borderShape
                )
                .onFocusChanged { viewModel.focusChange(it.isFocused) }
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.boundsInParent()
                },
            value = characterName,
            onValueChange = { viewModel.onCharacterNameValueChange(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.onDone(context)
                    keyboardController?.hide()
                    focusState.clearFocus()
                }
            ),
            placeholder = { SearchPlaceHolder(isFocus) },
            trailingIcon = if (isFocus) {
                { SearchTrailingIcon(characterName, viewModel, context, keyboardController, focusState) }
            } else {
                null
            },
            shape = borderShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,

                focusedContainerColor = LightGrayTransBG,
                focusedBorderColor = Color.Unspecified,

                unfocusedContainerColor = LightGrayTransBG,
                unfocusedBorderColor = Color.Unspecified
            ),
        )

        Popup(
            offset = IntOffset(x = 0, y = textFieldSize.height.toInt()),
        ) {
            AnimatedVisibility(visible = isFocus) {
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
                                            keyboardController?.hide()
                                            focusState.clearFocus()
                                            viewModel.onCharacterNameValueChange(history.charName)
                                            viewModel.onDone(context)
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
                                style = normalTextStyle(fontSize = 12.sp)
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
}

@Composable
private fun SearchPlaceHolder(isFocus: Boolean) {
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
private fun SearchTrailingIcon(
    characterName: String,
    viewModel: SearchVM,
    context: Context,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager
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
                viewModel.onDone(context)
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

@Composable
private fun SearchResult(viewModel: SearchVM, navController: NavHostController) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isSearch by viewModel.isSearch.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val characterList by viewModel.characterList.collectAsState()

    when {
        isLoading -> {
            LoadingScreen()
        }

        errorMessage != null -> {
            NetworkError(errorMessage)
        }

        isSearch && characterList.isEmpty() -> {
            SearchError(viewModel)
        }

        else -> {
            SearchResults(characterList, navController)
        }
    }
}

@Composable
private fun NetworkError(errorMessage: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage == "네트워크 오류") {
            Image(
                painter = painterResource(id = R.drawable.baseline_wifi_off_24),
                contentDescription = "Disconnect"
            )
        }
        Text(
            text = errorMessage!!,
            color = Color(0xFFCFCFCF)
        )
    }
}

@Composable
private fun SearchError(viewModel: SearchVM) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\"${viewModel.tempCharName.value}\"(은)는 없는 결과입니다.",
            style = normalTextStyle(color = Color(0xFFCFCFCF), fontSize = 15.sp),
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SearchResults(
    characterList: List<CharacterInfo>,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (characterList.isNotEmpty()) {
            stickyHeader { HeaderText("검색 결과") }
            item {
                val firstCharacter = characterList[0]
                CharacterListItem(
                    character = firstCharacter,
                    isFirstItem = true,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (characterList.size >= 2) {
            stickyHeader { HeaderText("같은 계정내 캐릭터 (${characterList.size - 1})") }
            items(characterList.drop(1), key = { item -> item.characterName }) {
                CharacterListItem(
                    character = it,
                    isFirstItem = false,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun HeaderText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp, topEnd = 32.dp, bottomEnd = 4.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = titleTextStyle(fontSize = 15.sp)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterListItem(
    character: CharacterInfo,
    isFirstItem: Boolean,
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("CharDetail/${character.characterName}") }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop,
            model = CharacterResourceMapper.getCharacterThumbURL(character.characterClassName),
            contentDescription = "직업 이미지"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = character.characterName,
                fontWeight = if (isFirstItem) FontWeight.Bold else FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = "${character.itemMaxLevel} ${character.characterClassName}(${character.serverName})",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun DeleteSearchHistoryDialog(
    viewModel: SearchVM,
    title: String,
) {
    Dialog(onDismissRequest = { viewModel.onDismissRequest() }) {

        Column(
            modifier = Modifier.background(LightGrayBG, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = title, style = titleTextStyle())
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "검색 기록에서 삭제하시겠습니까?", color = Color.White)
            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 0.5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.LightGray
                    )
                ) {
                    Text(text = "취소")
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                )

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.deleteHistory()
                    },
                ) {
                    Text(
                        text = "삭제",
                        color = Color.Red
                    )
                }

            }
        }
    }
}
