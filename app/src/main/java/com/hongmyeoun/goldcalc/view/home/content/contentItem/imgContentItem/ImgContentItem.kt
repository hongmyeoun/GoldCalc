package com.hongmyeoun.goldcalc.view.home.content.contentItem.imgContentItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.ImageReturn
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ItemLevelOrCombatPower
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.home.HomeContentVM

@Composable
fun ImgContent(
    character: Character,
    cardViewModel: HomeContentVM,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
    ) {
        ImgContentTop(character, cardViewModel, navController)
        CharacterImg(character)
        ImgContentBottom(character)
    }
}

@Composable
private fun ImgContentTop(
    character: Character,
    cardViewModel: HomeContentVM,
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            text = character.serverName,
            style = titleTextStyle(fontSize = 15.sp)
        )

        Row(
            modifier = Modifier.align(Alignment.TopEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.size(35.dp),
                onClick = { cardViewModel.onAvatarClick(character) }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    tint = Color.White,
                    contentDescription = "이미지 변경"
                )
            }

            IconButton(
                modifier = Modifier.size(35.dp),
                onClick = { navController.navigate(Screen.Homework.moveWithName(character.name)) }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    tint = Color.White,
                    contentDescription = "설정",
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun CharacterImg(character: Character) {
    Box {
        if (character.avatarImage) {
            GlideImage(
                model = character.characterImage,
                contentScale = ContentScale.FillWidth,
                contentDescription = "캐릭터 이미지"
            )
        } else {
            Box(
                modifier = Modifier
                    .background(
                        color = LightGrayBG.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                GlideImage(
                    model = CharacterResourceMapper.getClassDefaultImg(character.className),
                    contentScale = ContentScale.Inside,
                    contentDescription = "캐릭터 이미지"
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImgContentBottom(character: Character) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            if (character.title != null) {
                Text(
                    text = character.title,
                    style = normalTextStyle(fontSize = 11.sp)
                )
            }
            Text(
                text = character.name,
                style = titleTextStyle()
            )
            Text(
                text = character.className,
                style = normalTextStyle(fontSize = 13.sp)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Column {
                Text(
                    text = "아이템 레벨",
                    style = normalTextStyle(fontSize = 11.sp)
                )
                ItemLevelOrCombatPower(
                    level = character.itemLevel,
                    noSpace = true
                )

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier.size(25.dp),
                        model = ImageReturn.goldImage(character.weeklyGold),
                        contentDescription = "골드"
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = character.weeklyGold.formatWithCommas(),
                        style = titleTextStyle(fontSize = 15.sp),
                    )
                }
            }
        }
    }
}