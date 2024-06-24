package com.hongmyeoun.goldcalc.view.search

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
fun SearchUI(
    navController: NavHostController,
    viewModel: SearchVM = viewModel()
) {
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
            .background(ImageBG)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SearchTextField(
            viewModel = viewModel,
            keyboardController = keyboardController,
            focusState = focusState
        )
        SearchResult(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
private fun SearchTextField(
    viewModel: SearchVM,
    keyboardController: SoftwareKeyboardController?,
    focusState: FocusManager
) {
    val characterName by viewModel.characterName.collectAsState()
    var isFocus by remember { mutableStateOf(false) }
    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .padding(8.dp)
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
        placeholder = { SearchPlaceHolder(isFocus) },
        trailingIcon = if (isFocus) {
            { SearchTrailingIcon(characterName, viewModel, context, keyboardController, focusState) }
        } else {
            null
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            focusedContainerColor = LightGrayTransBG,
            focusedBorderColor = CharacterEmblemBG,

            unfocusedContainerColor = LightGrayTransBG,
            unfocusedBorderColor = CharacterEmblemBG
        ),
    )
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
        isLoading -> { LoadingScreen() }
        errorMessage == "네트워크 오류" -> { NetworkError(errorMessage) }
        isSearch && characterList.isEmpty() -> { SearchError(viewModel) }
        else -> { SearchResults(characterList, navController) }
    }
}

@Composable
private fun NetworkError(errorMessage: String?) {
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

@Composable
private fun SearchError(viewModel: SearchVM) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\"${viewModel.tempCharName.value}\"(은)는 없는 결과입니다.",
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
            stickyHeader { HeaderText("같은 계정내 캐릭터") }
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
fun CharacterListItem(
    character: CharacterInfo,
    isFirstItem: Boolean,
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("CharDetail/${character.characterName}") }
            .padding(8.dp)
        ,
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
                text = "${character.itemMaxLevel} ${character.characterClassName}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(ImageBG)) {

    }
    var text by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    var isFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = LightGrayBG,
            focusedBorderColor = CharacterEmblemBG,

            unfocusedContainerColor = LightGrayBG,
            unfocusedBorderColor = CharacterEmblemBG
        ),
//        colors = TextFieldDefaults.colors(
//            focusedContainerColor = LightGrayBG,
//            focusedIndicatorColor = Color.Transparent,
//
//            unfocusedContainerColor = LightGrayBG,
//            unfocusedIndicatorColor = Color.Transparent
//        ),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .onFocusChanged { isFocus = it.isFocused }
        ,
        value = text,
        onValueChange = { text = it },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusState.clearFocus()
            }
        ),
        placeholder = { SearchPlaceHolder(isFocus) },
        trailingIcon = if (isFocus) {
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (text.isNotEmpty()) {
                        Image(
                            modifier = Modifier.clickable { text = "" },
                            painter = painterResource(id = R.drawable.baseline_cancel_24),
                            contentDescription = "이름 초기화",
                            contentScale = ContentScale.Crop
                        )
                    }
                    IconButton(
                        onClick = {
//                            viewModel.onDone(context)
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