package com.hongmyeoun.goldcalc.view.home.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.home.content.contentItem.ContentItem
import com.hongmyeoun.goldcalc.viewModel.home.HomeContentVM

@Composable
fun ListContent(
    paddingValues: PaddingValues,
    characterList: List<Character>,
    characterRepository: CharacterRepository,
    navController: NavHostController,
    isLoading: Boolean,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBG)
            .padding(paddingValues)
    ) {
        items(characterList, key = { item -> item.name }) {
            val characterName = it.name
            val characterCardVM = remember { HomeContentVM(characterRepository, characterName) }

            ContentItem(
                navController = navController,
                cardViewModel = characterCardVM,
                isLoading = isLoading,
                isListView = true
            )
        }
    }
}