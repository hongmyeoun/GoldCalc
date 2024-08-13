package com.hongmyeoun.goldcalc.view.search.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.ErrorMessage
import com.hongmyeoun.goldcalc.model.constants.viewConst.Search
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.lostArkApi.SearchedCharacter
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun SearchResults(
    viewModel: SearchVM,
    navController: NavHostController,
    scrollState: LazyListState,
) {
    val characterList by viewModel.characterList.collectAsState()
    val charName by viewModel.characterName.collectAsState()

    if (characterList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            state = scrollState
        ) {
            stickyHeader { HeaderText(Search.RESULT) }
            item {
                val firstCharacter = characterList[0]
                ResultItem(
                    character = firstCharacter,
                    isFirstItem = true,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (characterList.size >= 2) {
                stickyHeader { HeaderText("${Search.SAME_ACCOUNT_CHAR} (${characterList.size - 1})") }
                items(characterList.drop(1), key = { item -> item.characterName }) {
                    ResultItem(
                        character = it,
                        isFirstItem = false,
                        navController = navController
                    )
                }
            }
        }
    }

    if (characterList.isEmpty() && charName.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = ErrorMessage.noResult(charName),
                color = Color(0xFFCFCFCF)
            )
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
fun ResultItem(
    character: SearchedCharacter,
    isFirstItem: Boolean,
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.Profile.moveWithName(character.characterName)) }
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
