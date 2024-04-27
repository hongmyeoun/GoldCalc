package com.hongmyeoun.goldcalc.view.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.DarkModeGray
import com.hongmyeoun.goldcalc.ui.theme.MokokoGreen
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM
import java.text.NumberFormat

@Composable
fun MainAppTopBar(navController: NavHostController, characterListVM: CharacterListVM) {
    Column {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MainAppTop(navController)
            MainAppProgressText(characterListVM)
            MainAppProgressBar(characterListVM)
            MainAppGoldCurrent(characterListVM)
        }
        Divider()
    }
}

@Composable
private fun MainAppTop(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainAppNameText(modifier = Modifier.weight(2f))
        MainMenuButtons(navController)
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainAppProgressText(characterListVM: CharacterListVM) {
    val showDialog by characterListVM.showDialog.collectAsState()

    if (showDialog) {
        ShowSimpleCurrent(characterListVM)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "숙제 진행도",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(12.dp),
                onClick = { characterListVM.onClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "전체 진행사항 한눈에 보기"
                )
            }
        }

        Text(
            text = "주간 골드 :",
            fontSize = 10.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.width(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = characterListVM.maxGold.formatWithCommas(),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            GlideImage(
                modifier = Modifier.size(18.dp),
                model = R.drawable.gold_coins,
                contentDescription = "골드"
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ShowSimpleCurrent(characterListVM: CharacterListVM, isDark: Boolean = isSystemInDarkTheme()) {
    Dialog(onDismissRequest = { characterListVM.onDissmissRequest() }) {
        val bgColor = if (isDark) DarkModeGray else Color.White
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val maxColumnHeight = (screenHeight * 0.8f) // 화면 높이의 80%

        Column(
            modifier = Modifier
                .background(bgColor, RoundedCornerShape(16.dp))
                .height(maxColumnHeight)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowSimpleCurrentTop()
            ShowSimpleCurrentContents(characterListVM)
        }
    }
}

@Composable
private fun ShowSimpleCurrentTop() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "숙제 현황",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun ShowSimpleCurrentContents(characterListVM: CharacterListVM) {
    val characterList by characterListVM.characters.collectAsState()
    LazyColumn {
        items(characterList, key = { item -> item.name }) {
            SimpleTotal(it)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun SimpleTotal(character: Character, isDark: Boolean = isSystemInDarkTheme()) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier.size(25.dp),
                contentScale = ContentScale.Crop,
                model = CharacterResourceMapper.getClassEmblem(isDark, character.className),
                contentDescription = "직업군"
            )

            Spacer(modifier = Modifier.width(5.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = character.serverName,
                    fontSize = 10.sp
                )

                Text(
                    text = character.name,
                    fontSize = 12.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = character.className,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = character.itemLevel,
                        fontSize = 10.sp
                    )
                }
            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier.size(18.dp),
                        model = R.drawable.gold_coins,
                        contentDescription = "골드"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = character.weeklyGold.formatWithCommas(),
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier.size(18.dp),
                        model = R.drawable.gold_coin,
                        contentDescription = "골드"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = character.earnGold.formatWithCommas(),
                        fontSize = 16.sp
                    )
                }
            }
        }

    }

    Spacer(modifier = Modifier.height(6.dp))

    val percentage = if (character.weeklyGold != 0) character.earnGold.toFloat() / character.weeklyGold else 0.0f
    Box(contentAlignment = Alignment.Center) {
        LinearProgressIndicator(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .border(1.dp, MokokoGreen),
            progress = percentage,
            color = MokokoGreen,
            trackColor = Color.Transparent
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            text = percentage.toPercentage(),
            textAlign = TextAlign.End,
            fontSize = 12.sp
        )
    }

    Spacer(modifier = Modifier.height(6.dp))
    Divider()
    Spacer(modifier = Modifier.height(6.dp))

}

@Composable
private fun MainAppProgressBar(characterListVM: CharacterListVM) {
    val animatedProgress = animateFloatAsState(
        targetValue = characterListVM.progressPercentage,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Box {
        LinearProgressIndicator(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth()
                .border(1.dp, MokokoGreen),
            progress = animatedProgress,
            color = MokokoGreen,
            trackColor = Color.Transparent
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            text = characterListVM.progressPercentage.toPercentage(),
            textAlign = TextAlign.End
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MainAppGoldCurrent(characterListVM: CharacterListVM) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "얻은 골드",
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = R.drawable.gold_coins,
                    contentDescription = "골드"
                )
                Text(
                    text = characterListVM.earnGold.formatWithCommas(),
                    fontSize = 16.sp
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "남은 골드",
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = R.drawable.gold_coins,
                    contentDescription = "골드"
                )
                Text(
                    text = characterListVM.remainGold.formatWithCommas(),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun MainMenuButtons(navController: NavHostController) {
    IconButton(
        onClick = { navController.navigate("Search") }
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "검색/추가"
        )
    }

    IconButton(
        onClick = { navController.navigate("Main") { popUpTo("Main") { inclusive = true } } }
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "새로고침"
        )
    }

    IconButton(
        onClick = { /*TODO*/ }
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "설정"
        )
    }
}


@Composable
fun MainAppNameText(modifier: Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontSize = 30.sp
                )
            ) {
                append("로")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
            ) {
                append("아")
            }

            withStyle(
                style = SpanStyle(
                    color = Color(0xffA6FF4D),
                    fontSize = 30.sp
                )
            ) {
                append("골")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
            ) {
                append("드 계산")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 30.sp
                )
            ) {
                append("기")
            }

        },
        modifier = modifier
    )
}


fun Int.formatWithCommas(): String {
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(this)
}

fun String.formatWithCommas(): String {
    val number = this.toIntOrNull() ?: return this
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(number)
}

fun Float.toPercentage(): String {
    return "${(this * 100).toInt()}%"
}