package com.hongmyeoun.goldcalc.view.home.content

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.R
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

        ListFloatingButton(paddingValues)

    } else {
        var nowRotated by remember { mutableStateOf(false) }

        HorizontalPageContent(
            paddingValues = paddingValues,
            characterList = characterList,
            characterRepository = characterRepository,
            navController = navController,
            isLoading = isLoading,
            nowRotate = { nowRotated = it }
        )

        if (!nowRotated) {
            HorizonFloatingButton(paddingValues)
        }
    }
}

@Composable
private fun ListFloatingButton(paddingValues: PaddingValues) {
    var expanded by remember { mutableStateOf(false) }
    
    val width by animateDpAsState(
        targetValue = if (expanded) 48.dp * 3f else 48.dp,
        label = "좌로 커짐"
    )

    val height = 48.dp
    
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(bottom = 32.dp, end = 32.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier
                .size(width, height)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(32.dp)
                )
                .clip(RoundedCornerShape(32.dp))
            ,
        ) {
            if (expanded) {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person_search),
                        tint = ImageBG,
                        contentDescription = "검색 추가"
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = ImageBG,
                        contentDescription = "그냥 추가"
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = ImageBG,
                        contentDescription = "닫기"
                    )
                }
            } else {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = ImageBG,
                        contentDescription = "열기"
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizonFloatingButton(paddingValues: PaddingValues) {
    var expanded by remember { mutableStateOf(false) }

    val width by animateDpAsState(
        targetValue = if (expanded) 48.dp * 3f else 48.dp,
        label = "좌로 커짐"
    )

    val height = 48.dp

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp, bottom = 48.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .size(width, height)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(32.dp)
                )
                .clip(RoundedCornerShape(32.dp))
            ,
        ) {
            if (expanded) {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person_search),
                        tint = ImageBG,
                        contentDescription = "검색 추가"
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = ImageBG,
                        contentDescription = "닫기"
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = ImageBG,
                        contentDescription = "그냥 추가"
                    )
                }
            } else {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = ImageBG,
                        contentDescription = "열기"
                    )
                }
            }
        }
    }
}
