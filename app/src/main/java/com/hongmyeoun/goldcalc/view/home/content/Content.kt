package com.hongmyeoun.goldcalc.view.home.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    characterList: List<Character>,
    characterRepository: CharacterRepository,
    navController: NavHostController,
    isLoading: Boolean,
    isListView: Boolean
) {
    if (isListView) {
        ListContent(
            paddingValues = paddingValues,
            characterList = characterList,
            characterRepository = characterRepository,
            navController = navController,
            isLoading = isLoading
        )
    } else {
        HorizontalPageContent(
            paddingValues = paddingValues,
            characterList = characterList,
            characterRepository = characterRepository,
            navController = navController,
            isLoading = isLoading
        )
    }
}
