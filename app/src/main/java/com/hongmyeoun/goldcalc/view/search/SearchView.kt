package com.hongmyeoun.goldcalc.view.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterScreen(navController: NavHostController, viewModel: SearchVM = viewModel()) {
    val characterName by viewModel.characterName.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val characterList by viewModel.characterList.collectAsState()

    val context = LocalContext.current

    var isFocus by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        keyboardController?.hide()
                        focusState.clearFocus()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .onFocusChanged {
                    isFocus = it.isFocused
                },
            value = characterName,
            onValueChange = { viewModel.onCharacterNameValueChange(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (characterName.isNotEmpty()) {
                        viewModel.onDone(context)
                    }
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
                    IconButton(
                        onClick = {
                            if (characterName.isNotEmpty()) {
                                viewModel.onDone(context)
                            }
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
            } else {
                null
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 가져온 캐릭터 정보를 화면에 표시
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(characterList, key = { item -> item.characterName }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("CharDetail/${it.characterName}") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            modifier = Modifier.size(48.dp),
                            contentScale = ContentScale.Crop,
                            model = CharacterResourceMapper.getCharacterThumbURL(it.characterClassName),
                            contentDescription = "직업 이미지"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = it.characterName,
                                fontWeight = FontWeight(550)
                            )
                            Text(
                                text = "${it.itemMaxLevel} ${it.characterClassName}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPreview() {
    var characterName by remember { mutableStateOf("") }

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
                IconButton(
                    onClick = {
                        keyboardController?.hide()
                        focusState.clearFocus()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "검색")
                }
            }
        } else {
            null
        }
    )

}