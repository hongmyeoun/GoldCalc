package com.hongmyeoun.goldcalc.view.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.LoadingScreen
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterInfo
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterScreen(navController: NavHostController, viewModel: SearchVM = viewModel()) {
    val characterName by viewModel.characterName.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSearch by viewModel.isSearch.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val characterList by viewModel.characterList.collectAsState()

    val context = LocalContext.current

    var isFocus by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        keyboardController?.hide()
                        focusState.clearFocus()
                    }
                )
            }
            .background(MaterialTheme.colorScheme.background)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocus = it.isFocused },
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
            placeholder = {
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
            },
            trailingIcon =
            if (isFocus) {
                {
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
                                contentDescription = "검색"
                            )
                        }
                    }
                }
            } else {
                null
            }
        )

        // 가져온 캐릭터 정보를 화면에 표시
        when {
            isLoading -> {
                LoadingScreen()
            }
            errorMessage == "네트워크 오류" -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_wifi_off_24),
                        contentDescription = "Disconnect"
                    )
                    Text(
                        text = errorMessage!!,
                        color = Color(0xFFCFCFCF)
                    )
                }
            }
            isSearch && characterList.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "\"${viewModel.tempCharName.value}\"(은)는 없는 결과입니다.",
//                        color = Color(0xFFCFCFCF)
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
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
                        }
                    }
                    if (characterList.size >= 2) {
                        stickyHeader { HeaderText("원정대 캐릭터") }
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
        }
    }
}

@Composable
private fun HeaderText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = text
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterListItem(character: CharacterInfo, isFirstItem: Boolean, navController: NavHostController, isDark: Boolean = isSystemInDarkTheme()) {
    val textColor = if (isDark) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("CharDetail/${character.characterName}") },
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
                color = textColor
            )
            Text(
                text = "${character.itemMaxLevel} ${character.characterClassName}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPreview() {
    var characterName by remember { mutableStateOf("gg") }

    var isFocus by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onFocusChanged {
                isFocus = it.isFocused
            },
        value = characterName,
        onValueChange = { characterName = it },
        placeholder = {
            if (!isFocus) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "캐릭터 검색")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.Search, contentDescription = "검색")
                }
            } else {
                Text(text = "캐릭터 검색")
            }
        },
        trailingIcon =
        if (isFocus) {
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (characterName.isNotEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_cancel_24),
                            contentDescription = "이름 초기화",
                            contentScale = ContentScale.Crop
                        )
                    }
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            focusState.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "검색"
                        )
                    }
                }
            }
        } else {
            null
        }
    )
}