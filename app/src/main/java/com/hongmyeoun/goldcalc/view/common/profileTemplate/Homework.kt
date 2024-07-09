package com.hongmyeoun.goldcalc.view.common.profileTemplate

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeworkProfile(
    it: Character,
    onAvatarClick: () -> Unit,
    onReloadClick: () -> Unit
) {
    var isBlinking by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val characterImgText = if (it.avatarImage) Homework.AVATAR else Homework.DEFAULT_IMAGE

    Box(
        modifier = Modifier
            .background(ImageBG)
            .fillMaxWidth()
    ) {
        CharImage(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .height(300.dp),
            character = it
        )

        DefaultsProfiles(character = it)

        AvatarAndReload(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onAvatarClick = {
                onAvatarClick()
                isBlinking = true
                scope.launch {
                    delay(2250)
                    isBlinking = false
                }
            },
            onReloadClick = onReloadClick
        )

        BlinkingText(
            modifier = Modifier.align(Alignment.TopEnd),
            isBlinking = isBlinking,
            text = characterImgText,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharImage(
    modifier: Modifier,
    character: Character
) {
    if (character.avatarImage) {
        GlideImage(
            modifier = modifier,
            model = character.characterImage,
            contentDescription = "캐릭터 이미지"
        )
    } else {
        GlideImage(
            modifier = modifier,
            contentScale = ContentScale.FillHeight,
            model = CharacterResourceMapper.getClassDefaultImg(character.className),
            contentDescription = "캐릭터 이미지"
        )
    }
}

@Composable
private fun DefaultsProfiles(
    character: Character
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 24.dp)
    ) {
        ServerClassName(
            serverName = character.serverName,
            className = character.className
        )
        TitleCharName(
            title = character.title,
            name = character.name
        )
        ItemLevel(level = character.itemLevel)
        Extra(character = character)
        Levels(character = character)
    }
}

@Composable
private fun AvatarAndReload(
    modifier: Modifier,
    onAvatarClick: () -> Unit,
    onReloadClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(end = 8.dp, bottom = 16.dp)
    ) {
        FloatingButton(
            icon = Icons.Default.Person,
            onClick = onAvatarClick
        )

        FloatingButton(
            icon = Icons.Default.Refresh,
            onClick = onReloadClick
        )
    }
}

@Composable
private fun FloatingButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    SmallFloatingActionButton(
        onClick = onClick,
        containerColor = LightGrayBG
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "버튼 아이콘",
            tint = Color.White
        )
    }
}

@Composable
private fun BlinkingText(
    modifier: Modifier,
    isBlinking: Boolean,
    text: String,
) {
    Column(
        modifier = modifier.padding(top = 24.dp, end = 16.dp)
    ) {
        if (isBlinking) {
            val infiniteTransition = rememberInfiniteTransition(label = Labels.Animation.FLICKER)

            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 750
                    },
                    repeatMode = RepeatMode.Reverse
                ),
                label = Labels.Animation.FLICKER
            )

            Text(
                text = text,
                color = Color.White.copy(alpha = alpha)
            )
        }
    }
}
