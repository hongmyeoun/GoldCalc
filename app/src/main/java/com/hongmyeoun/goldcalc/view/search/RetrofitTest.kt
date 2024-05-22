package com.hongmyeoun.goldcalc.view.search

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.GsonBuilder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterInfo
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.lostArkApi.LostArkApiService
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterScreen(navController: NavHostController) {
    var characterName by remember { mutableStateOf("") }
    var characterInfo by remember { mutableStateOf<List<CharacterInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
            onValueChange = { characterName = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    isLoading = true
                    // Retrofit을 사용하여 서버에서 데이터 가져오기
                    scope.launch(Dispatchers.IO) {
                        val info = getCharacter(context, characterName)
                        characterInfo = info ?: emptyList()
                        isLoading = false
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
                            isLoading = true
                            // Retrofit을 사용하여 서버에서 데이터 가져오기
                            scope.launch(Dispatchers.IO) {
                                val info = getCharacter(context, characterName)
                                characterInfo = info ?: emptyList()
                                isLoading = false
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
                items(characterInfo, key = { item -> item.characterName }) {
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

@Composable
fun CharacterDetailScreen(charName: String, viewModel: CharDetailVM = hiltViewModel()) {
    val context = LocalContext.current
    var characterDetail by remember { mutableStateOf<CharacterDetail?>(null) }

    LaunchedEffect(Unit) {
        characterDetail = getCharDetail(context, charName)
        viewModel.isSavedName(charName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = characterDetail?.characterImage,
            contentDescription = null,
        )
        Text(text = "${characterDetail?.characterClassName ?: "ERROR"} ${characterDetail?.characterName ?: "ERROR"} : Lv. ${characterDetail?.itemMaxLevel ?: 0}")
        Button(
            onClick = {
                val avatarImage = !characterDetail?.characterImage.isNullOrEmpty()

                val character = Character(
                    name = characterDetail!!.characterName,
                    itemLevel = characterDetail!!.itemMaxLevel,
                    serverName = characterDetail!!.serverName,
                    className = characterDetail!!.characterClassName,

                    guildName = characterDetail!!.guildName,
                    title = characterDetail!!.title,
                    characterLevel = characterDetail!!.characterLevel,
                    expeditionLevel = characterDetail!!.expeditionLevel,
                    pvpGradeName = characterDetail!!.pvpGradeName,
                    townLevel = characterDetail!!.townLevel,
                    townName = characterDetail!!.townName,
                    characterImage = characterDetail?.characterImage,
                    avatarImage = avatarImage
                )
                viewModel.saveCharacter(character)
            },
            enabled = !viewModel.isSaved
        ) {
            Text(text = "가져오기")
        }
    }

}

suspend fun getCharDetail(context: Context, characterName: String): CharacterDetail? {
    return withContext(Dispatchers.IO) {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(context, R.string.lost_ark_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(authorizationHeader(context))
            .build()

        val lostArkApiService = retrofit.create(LostArkApiService::class.java)

        try {
            val response = lostArkApiService.getCharacterDetail(characterName).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.d("실패", "서버 응답 실패: ${response.code()}")
                null
            }
        } catch (e: IOException) {
            Log.d("실패", "네트워크 오류: $e")
            null
        }
    }
}

suspend fun getCharacter(context: Context, characterName: String): List<CharacterInfo>? {
    return withContext(Dispatchers.IO) {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(context, R.string.lost_ark_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(authorizationHeader(context))
            .build()

        val lostArkApiService = retrofit.create(LostArkApiService::class.java)

        try {
            val response = lostArkApiService.getCharacters(characterName).execute()
            if (response.isSuccessful) {
                var characterInfoList = response.body()
                characterInfoList = characterInfoList?.sortedByDescending { it.itemMaxLevel }
                characterInfoList
            } else {
                // 실패 처리
                Log.d("실패", "서버 응답 실패: ${response.code()}")
                null
            }
        } catch (e: IOException) {
            // 네트워크 오류 처리
            Log.d("실패", "네트워크 오류: $e")
            null
        }
    }
}

fun authorizationHeader(context: Context): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", getString(context, R.string.lost_ark_api))
            .build()
        chain.proceed(request)
    }
    return httpClient.build()
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