package com.hongmyeoun.goldcalc.view.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM
import java.text.NumberFormat

@Composable
fun MainAppTopBar(navController: NavHostController, characterListVM: CharacterListVM) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        MainAppTop(navController)
        MainAppProgressText(characterListVM)
        MainAppProgressBar(characterListVM)
        MainAppGoldCurrent(characterListVM)
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
                onClick = { /*TODO*/ }
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
                .border(1.dp, Color(0xffA6FF4D)),
            progress = animatedProgress,
            color = Color(0xffA6FF4D),
            trackColor = Color.Transparent
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            text = "${(characterListVM.progressPercentage * 100).toInt()}%",
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