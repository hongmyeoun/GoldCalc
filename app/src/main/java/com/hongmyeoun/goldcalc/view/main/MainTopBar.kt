package com.hongmyeoun.goldcalc.view.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM
import java.text.NumberFormat

@Composable
fun MainAppTopBar(
    navController: NavHostController,
    characterListVM: CharacterListVM,
    snackbarHostState: SnackbarHostState
) {
    Column {
        Column(
            modifier = Modifier
                .background(LightGrayBG)
                .padding(16.dp)
        ) {
            MainAppTop(navController, characterListVM, snackbarHostState)
            MainAppProgressText(characterListVM)
            MainAppProgressBar(characterListVM)
            MainAppGoldCurrent(characterListVM)
        }
    }
}

@Composable
private fun MainAppTop(
    navController: NavHostController,
    characterListVM: CharacterListVM,
    snackbarHostState: SnackbarHostState
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        MainAppNameText(modifier = Modifier.align(Alignment.CenterStart))
        MainMenuButtons(modifier = Modifier.align(Alignment.CenterEnd), navController, characterListVM, snackbarHostState)
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
                style = normalTextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { characterListVM.onClicked() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.Info,
                    tint = Color.White,
                    contentDescription = "전체 진행사항 한눈에 보기"
                )
            }
        }

        Text(
            text = "주간 골드 :",
            style = normalTextStyle(),
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.width(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = characterListVM.maxGold.formatWithCommas(),
                style = normalTextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.width(4.dp))

            GlideImage(
                modifier = Modifier.size(25.dp),
                model = goldImage(characterListVM.maxGold),
                contentDescription = "골드"
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ShowSimpleCurrent(characterListVM: CharacterListVM) {
    Dialog(onDismissRequest = { characterListVM.onDissmissRequest() }) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val maxColumnHeight = (screenHeight * 0.8f) // 화면 높이의 80%

        Column(
            modifier = Modifier
                .background(LightGrayBG, RoundedCornerShape(16.dp))
                .heightIn(max = maxColumnHeight)
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
        style = titleTextStyle(fontSize = 20.sp)
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
private fun SimpleTotal(character: Character) {
    Column(
        modifier = Modifier
            .background(
                color = ImageBG,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(2f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(36.dp))
                        .background(CharacterEmblemBG),
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(3.dp),
                        contentScale = ContentScale.Crop,
                        model = CharacterResourceMapper.getClassEmblem(character.className),
                        contentDescription = "직업군"
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = character.serverName,
                        style = normalTextStyle()
                    )

                    Text(
                        text = character.name,
                        style = normalTextStyle(fontSize = 12.sp)
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
                            modifier = Modifier.size(24.dp),
                            model = goldImage(character.weeklyGold),
                            contentDescription = "골드"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = character.weeklyGold.formatWithCommas(),
                            style = normalTextStyle(fontSize = 14.sp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            modifier = Modifier.size(24.dp),
                            model = goldImage(character.earnGold),
                            contentDescription = "골드"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = character.earnGold.formatWithCommas(),
                            style = normalTextStyle(fontSize = 12.sp)
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
                    .border(
                        width = 0.5f.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                progress = percentage,
                color = CharacterEmblemBG,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                text = percentage.toPercentage(),
                textAlign = TextAlign.End,
                style = normalTextStyle(fontSize = 12.sp)
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

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
                .border(
                    width = 0.5f.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(16.dp)
                ),
            progress = animatedProgress,
            color = CharacterEmblemBG,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            text = characterListVM.progressPercentage.toPercentage(),
            textAlign = TextAlign.End,
            color = Color.White
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
                style = normalTextStyle(),
                fontWeight = FontWeight.Light
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = goldImage(characterListVM.earnGold),
                    contentDescription = "골드"
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = characterListVM.earnGold.formatWithCommas(),
                    style = normalTextStyle(fontSize = 16.sp),
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "남은 골드",
                style = normalTextStyle(),
                fontWeight = FontWeight.Light
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = goldImage(characterListVM.remainGold),
                    contentDescription = "골드"
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = characterListVM.remainGold.formatWithCommas(),
                    style = normalTextStyle(fontSize = 16.sp),
                )
            }
        }
    }
}

@Composable
private fun MainMenuButtons(
    modifier: Modifier,
    navController: NavHostController,
    characterListVM: CharacterListVM,
    snackbarHostState: SnackbarHostState
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(35.dp),
            onClick = { navController.navigate("Search") }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.White,
                contentDescription = "검색/추가"
            )
        }

        IconButton(
            modifier = Modifier.size(35.dp),
            onClick = { characterListVM.onReset(snackbarHostState) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_settings_backup_restore_24),
                tint = Color.White,
                contentDescription = "새로고침"
            )
        }

        IconButton(
            modifier = Modifier.size(35.dp),
            onClick = { navController.navigate("Setting") }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                tint = Color.White,
                contentDescription = "설정"
            )
        }
    }
}


@Composable
fun MainAppNameText(modifier: Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.White,
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
                    color = Color.White,
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
                    color = Color.White,
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

fun Long.formatWithCommas(): String {
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

fun goldImage(gold: Int): Int {
    return when (gold) {
        in 0 until 10000 -> R.drawable.gold_coins
        in 10000 until 20000 -> R.drawable.gold_bar
        in 20000 until 50000 -> R.drawable.gold_box
        else -> R.drawable.gold_bar_many
    }
}