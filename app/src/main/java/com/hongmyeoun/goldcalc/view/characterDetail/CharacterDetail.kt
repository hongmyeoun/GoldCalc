package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.VeryLightGrayTransBG
import com.hongmyeoun.goldcalc.view.characterDetail.equipment.EquipmentDetailUI
import com.hongmyeoun.goldcalc.view.goldCheck.setting.CharacterDetailSimpleUI
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun CharacterDetailUI(
    charName: String,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(LightGrayBG),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.weight(0.5f),
                    onClick = {
                        navController.navigate("Search") {
                            popUpTo("CharDetail/{charName}") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "뒤로",
                        tint = Color.White
                    )
                }

                Text(
                    modifier = Modifier.fillMaxWidth().weight(3f),
                    text = "캐릭터 상세",
                    style = titleTextStyle(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(32.dp).weight(0.5f))
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        CharacterDetailScreen(charName = charName, paddingValues = it)
    }
}

@Composable
fun CharacterDetailScreen(
    charName: String,
    paddingValues: PaddingValues,
    viewModel: CharDetailVM = hiltViewModel()
) {
    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getCharDetails(context, charName)
        viewModel.isSavedName(charName)
    }

    val characterDetail by viewModel.characterDetail.collectAsState()
    val engravings by viewModel.engravings.collectAsState()
    val equipment by viewModel.equipments.collectAsState()
    val gems by viewModel.gems.collectAsState()
    val cards by viewModel.cards.collectAsState()
    val cardsEffects by viewModel.cardsEffects.collectAsState()
    val skills by viewModel.skills.collectAsState()

    val isSaved by viewModel.isSaved.collectAsState()

    characterDetail?.let { charDetail ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
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
                Row {
                    val rowWeight = Modifier.weight(1f)
                    CombatStatDetailUI(rowWeight, charDetail)
                    Spacer(modifier = Modifier.width(8.dp))

                    engravings?.let { skillEngravings ->
                        EngravingDetailUI(rowWeight, skillEngravings)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                equipment?.let { equipmentList ->
                    val equipmentVM = EquipmentDetailVM(equipmentList)
                    EquipmentDetailUI(equipmentList, equipmentVM)
                }

                gems?.let { gemList ->
                    GemDetailUI(gemList)
                }

                cards?.let { cardList ->
                    CardDetailUI(cardsEffects, cardList)
                }

                skills?.let { skillsList ->
                    SkillDetailUI(characterDetail, skillsList, gems)
                }
            }
        }
    }

}

@Composable
fun Levels(characterDetail: CharacterDetail) {
    Row {
        CharLevel("전투", characterDetail.characterLevel.toString())
        Spacer(modifier = Modifier.width(16.dp))

        CharLevel("원정대", characterDetail.expeditionLevel.toString())
    }
}

@Composable
fun Levels(character: Character) {
    Row {
        CharLevel("전투", character.characterLevel.toString())
        Spacer(modifier = Modifier.width(16.dp))

        CharLevel("원정대", character.expeditionLevel.toString())
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
fun Extra(character: Character) {
    ExtraInfo("길드", character.guildName)
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo("영지", "Lv.${character.townLevel} ${character.townName}")
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo("PVP", character.pvpGradeName)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ExtraInfo(extraTitle: String, extraDetail: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextChip(text = extraTitle, borderless = true, customBGColor = VeryLightGrayTransBG)
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
        TextChip(
            text = serverName,
            borderless = true,
            customTextSize = 12.sp,
            customBGColor = VeryLightGrayTransBG
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
    customBGColor: Color = Color.Black,
    brush: Boolean = false,
    customBGBrush: Brush = RelicBG,
    fixedWidth: Boolean = false,
    customWidthSize: Dp = 40.dp,
    customRoundedCornerSize: Dp = 4.dp,
    image: Boolean = false,
    yourImage: @Composable (() -> Unit)? = null
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

    val customBG = if (!brush) {
        Modifier
            .background(
                color = customBGColor,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    } else {
        Modifier
            .background(
                brush = customBGBrush,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .then(customWidth)
            .then(customBG)
            .then(customPadding)
    ) {
        if (!image) {
            Text(
                text = text,
                fontSize = customTextSize,
                color = Color.White
            )
        } else {
            if (yourImage != null) {
                yourImage()
            }
            Text(
                text = text,
                fontSize = customTextSize,
                color = Color.White
            )
        }
    }
}
