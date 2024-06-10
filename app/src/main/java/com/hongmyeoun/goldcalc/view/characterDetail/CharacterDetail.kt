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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.goldCheck.setting.CharacterDetailSimpleUI
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@Composable
fun CharacterDetailScreen(charName: String, viewModel: CharDetailVM = hiltViewModel()) {
    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()
    var characterDetail by remember { mutableStateOf<CharacterDetail?>(null) }
    var characterEquipment by remember { mutableStateOf<List<CharacterItem>?>(null) }
    var characterGem by remember { mutableStateOf<List<Gem>?>(null) }
    var characterCards by remember { mutableStateOf<List<Cards>?>(null) }
    var characterCardsEffects by remember { mutableStateOf<List<CardEffects>?>(null) }
    var characterSkills by remember { mutableStateOf<List<Skills>?>(null) }

    LaunchedEffect(Unit) {
        characterDetail = APIRemote.getCharDetail(context, charName)
        characterEquipment = APIRemote.getCharEquipment(context, charName)
        characterGem = APIRemote.getCharGem(context, charName)
        APIRemote.getCharCard(context, charName)?.let { (cards, effects) ->
            characterCards = cards
            characterCardsEffects = effects
        }
        characterSkills = APIRemote.getCharSkill(context, charName)

        viewModel.isSavedName(charName)
    }

    characterDetail?.let { charDetail ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ImageBG)
                .verticalScroll(verticalScrollState)
        ) {
            CharacterDetailSimpleUI(
                characterDetail = charDetail,
                onGetClick = {
                    val avatarImage = charDetail.characterImage.isNotEmpty()

                    val character = Character(
                        name = charDetail.characterName,
                        itemLevel = charDetail.itemMaxLevel,
                        serverName = charDetail.serverName,
                        className = charDetail.characterClassName,
                        guildName = charDetail.guildName,
                        title = charDetail.title,
                        characterLevel = charDetail.characterLevel,
                        expeditionLevel = charDetail.expeditionLevel,
                        pvpGradeName = charDetail.pvpGradeName,
                        townLevel = charDetail.townLevel,
                        townName = charDetail.townName,
                        characterImage = charDetail.characterImage,
                        avatarImage = avatarImage
                    )
                    viewModel.saveCharacter(character)
                },
                getButtonEnabled = !viewModel.isSaved
            )
            Column(modifier = Modifier.fillMaxSize()) {
                EquipmentDetailUI(characterEquipment)

                characterGem?.let { gemList ->
                    GemDetailUI(gemList)
                }

                characterCards?.let { cardList ->
                    CardDetailUI(characterCardsEffects, cardList)
                }

                characterSkills?.let { skills ->
                    SkillDetailUI(characterDetail, skills)
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {
    }
}

@Composable
fun Levels(characterDetail: CharacterDetail) {
    Row {
        CharLevel("아이템", characterDetail.itemMaxLevel)
        Spacer(modifier = Modifier.width(8.dp))

        CharLevel("전투", characterDetail.characterLevel.toString())
        Spacer(modifier = Modifier.width(8.dp))

        CharLevel("원정대", characterDetail.expeditionLevel.toString())
    }
}

@Composable
private fun CharLevel(title: String, level: String) {

    Column {
        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
        Text(text = extraDetail?:"-", fontSize = 10.sp, color = Color.White)
    }
}

@Composable
fun TitleCharName(title: String?, name:String) {
    Spacer(modifier = Modifier.height(12.dp))
    title?.let {
        Text(text = title, fontSize = 10.sp, color = Color.White)
    }
    Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ServerClassName(serverName: String, className: String) {
    Row {
        TextChip(text = serverName)
        Spacer(modifier = Modifier.width(8.dp))
        TextChip(text = className)
    }
}

@Composable
fun TextChip(
    text: String,
    customTextSize: TextUnit = 10.sp,
    borderless: Boolean = false,
    customPadding: Modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
) {
    val modifier = if (!borderless) {
        Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
    } else {
        Modifier
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
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
