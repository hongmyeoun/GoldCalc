package com.hongmyeoun.goldcalc.view.main.characterCard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.main.formatWithCommas
import com.hongmyeoun.goldcalc.view.main.goldImage
import com.hongmyeoun.goldcalc.view.main.toPercentage
import com.hongmyeoun.goldcalc.viewModel.main.CharacterCardVM

@Composable
fun CharacterCardTop(
    navController: NavHostController,
    viewModel: CharacterCardVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleCharacterInfo(
            navController = navController,
            modifier = Modifier.weight(2.5f),
            viewModel = viewModel
        )
        SimpleProgressInfo(
            modifier = Modifier.weight(1.3f),
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SimpleCharacterInfo(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: CharacterCardVM
) {
    val character by viewModel.character.collectAsState()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(36.dp))
                .background(CharacterEmblemBG),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                modifier = Modifier.size(46.dp).padding(6.dp),
                contentScale = ContentScale.Crop,
                model = CharacterResourceMapper.getClassEmblem(character.className),
                contentDescription = "직업군"
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = character.serverName,
                    style = normalTextStyle()
                )
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    modifier = Modifier.size(15.dp),
                    onClick = { navController.navigate(Screen.Homework.moveWithName(character.name)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        tint = Color.White,
                        contentDescription = "설정",
                    )
                }
            }

            Text(
                text = character.name,
                color = Color.White
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = character.className,
                    style = normalTextStyle()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = character.itemLevel,
                    style = normalTextStyle()
                )
            }
        }
    }
}

@Composable
fun SimpleProgressInfo(
    modifier: Modifier,
    viewModel: CharacterCardVM
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        ProgressGold(viewModel)

        Spacer(modifier = Modifier.height(10.dp))

        SimpleProgressBar(viewModel)
    }

}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProgressGold(
    viewModel: CharacterCardVM
) {
    val character by viewModel.character.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = goldImage(character.weeklyGold),
                    contentDescription = "골드"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = character.weeklyGold.formatWithCommas(),
                    style = normalTextStyle(fontSize = 16.sp),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = goldImage(viewModel.totalGold.value),
                    contentDescription = "골드"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = viewModel.totalGold.value.formatWithCommas(),
                    style = normalTextStyle(fontSize = 16.sp),
                )
            }
        }
    }
}


@Composable
fun SimpleProgressBar(viewModel: CharacterCardVM) {
    val progressPercentage by viewModel.progressPercentage.collectAsState()
    val animatedProgress = animateFloatAsState(
        targetValue = progressPercentage,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Box(
        contentAlignment = Alignment.Center
    ) {

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .border(0.3f.dp, Color.Gray, RoundedCornerShape(8.dp)),
            progress = animatedProgress,
            color = CharacterEmblemBG,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "진행도",
            style = normalTextStyle(fontSize = 8.sp),
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 2.dp),
            text = viewModel.progressPercentage.value.toPercentage(),
            style = normalTextStyle(fontSize = 8.sp),
        )
    }

}