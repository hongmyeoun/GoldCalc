package com.hongmyeoun.goldcalc.view.home.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.ui.theme.ImageBG

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
            isLoading = isLoading,
        )
    }

    val floatingPadding = if (isListView) Modifier.padding(bottom = 32.dp, end = 32.dp) else Modifier.padding(start = 32.dp, end = 32.dp, bottom = 48.dp)

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .then(floatingPadding),
        contentAlignment = if (isListView) Alignment.BottomEnd else Alignment.BottomCenter
    ) {
        FloatingActionButton(
            modifier = Modifier.size(48.dp),
            onClick = { /*TODO*/ },
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = ImageBG
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "추가")
        }
    }
}
