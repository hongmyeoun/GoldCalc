package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.goldCheck.setting.CharacterDetailSimpleUI
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@Composable
fun CharacterDetailScreen(
    charName: String,
    viewModel: CharDetailVM = hiltViewModel()
) {
    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getCharDetails(context, charName)
        viewModel.isSavedName(charName)
    }

    val characterDetail by viewModel.characterDetail.collectAsState()
    val equipment by viewModel.equipments.collectAsState()
    val gems by viewModel.gems.collectAsState()
    val cards by viewModel.cards.collectAsState()
    val cardsEffects by viewModel.cardsEffects.collectAsState()
    val skills by viewModel.skills.collectAsState()

    val isSaved by viewModel.isSaved.collectAsState()

    characterDetail?.let { charDetail ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ImageBG)
                .padding(8.dp)
                .verticalScroll(verticalScrollState)
        ) {
            CharacterDetailSimpleUI(
                characterDetail = charDetail,
                onGetClick = { viewModel.saveCharDetailToLocal(charDetail) },
                getButtonEnabled = !isSaved
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                equipment?.let { equipmentList ->
                    EquipmentDetailUI(equipmentList)
                }

                gems?.let { gemList ->
                    GemDetailUI(gemList)
                }

                cards?.let { cardList ->
                    CardDetailUI(cardsEffects, cardList)
                }

                skills?.let { skillsList ->
                    SkillDetailUI(characterDetail, skillsList)
                }
            }
        }
    }

}


//@OptIn(ExperimentalGlideComposeApi::class)
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    GoldCalcTheme {
//    }
//}

@Composable
fun Levels(characterDetail: CharacterDetail) {
    Row {
        CharLevel("전투", characterDetail.characterLevel.toString())
        Spacer(modifier = Modifier.width(16.dp))

        CharLevel("원정대", characterDetail.expeditionLevel.toString())
    }
}

@Composable
private fun CharLevel(title: String, level: String) {
    Column {
        Text(
            text = title,
            style = titleBoldWhite12()
        )
        if (title == "아이템") {
            val dot = "."
            val beforeDot = level.substringBefore(dot)
            val afterDot = level.substringAfter(dot)
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    ) {
                        append(beforeDot)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    ) {
                        append(dot)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    ) {
                        append(afterDot)
                    }
                }
            )
        } else {
            Text(text = "Lv.$level", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemLevel(level: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        GlideImage(
            modifier = Modifier.size(24.dp),
            model = "https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_12.png",
            contentScale = ContentScale.FillHeight,
            contentDescription = "아이템 레벨"
        )
        val dot = "."
        val beforeDot = level.substringBefore(dot)
        val afterDot = level.substringAfter(dot)
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                ) {
                    append(beforeDot)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                ) {
                    append(dot)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                ) {
                    append(afterDot)
                }
            }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun Extra(characterDetail: CharacterDetail) {
    ExtraInfo("길드", characterDetail.guildName)
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo("영지", "Lv.${characterDetail.townLevel} ${characterDetail.townName}")
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo("PVP", characterDetail.pvpGradeName)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ExtraInfo(extraTitle: String, extraDetail: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextChip(text = extraTitle)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = extraDetail ?: "-",
            style = normalTextStyle()
        )
    }
}

@Composable
fun TitleCharName(title: String?, name: String) {
    Spacer(modifier = Modifier.height(12.dp))
    title?.let {
        Text(
            text = title,
            style = normalTextStyle()
        )
    }
    Text(
        text = name,
        style = titleTextStyle()
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun ServerClassName(serverName: String, className: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = serverName,
            style = titleBoldWhite12()
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = className,
            style = normalTextStyle()
        )
    }
}

@Composable
fun TextChip(
    text: String,
    customTextSize: TextUnit = 10.sp,
    borderless: Boolean = false,
    customPadding: Modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp),
    customBG: Color = Color.Black,
    fixedWidth: Boolean = false,
    customWidthSize: Dp = 40.dp,
    customRoundedCornerSize: Dp = 4.dp
) {
    val modifier = if (!borderless) {
        Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    } else {
        Modifier
    }

    val customWidth = if (!fixedWidth) {
        Modifier
    } else {
        Modifier.width(customWidthSize)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .then(customWidth)
            .background(
                color = customBG,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
            .then(customPadding)
    ) {
        Text(
            text = text,
            fontSize = customTextSize,
            color = Color.White
        )
    }
}
