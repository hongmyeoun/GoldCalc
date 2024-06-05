package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@Composable
fun CharacterDetailScreen(charName: String, viewModel: CharDetailVM = hiltViewModel()) {
    val context = LocalContext.current
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = characterDetail?.characterImage,
            contentDescription = null,
        )
        Text(text = "${characterDetail?.characterClassName ?: "ERROR"} ${characterDetail?.characterName ?: "ERROR"} : Lv. ${characterDetail?.itemMaxLevel ?: 0}")
        Button(
            onClick = {
                val avatarImage = !characterDetail?.characterImage.isNullOrEmpty()

                val character = Character(
                    name = characterDetail!!.characterName,
                    itemLevel = characterDetail!!.itemMaxLevel,
                    serverName = characterDetail!!.serverName,
                    className = characterDetail!!.characterClassName,

                    guildName = characterDetail!!.guildName,
                    title = characterDetail!!.title,
                    characterLevel = characterDetail!!.characterLevel,
                    expeditionLevel = characterDetail!!.expeditionLevel,
                    pvpGradeName = characterDetail!!.pvpGradeName,
                    townLevel = characterDetail!!.townLevel,
                    townName = characterDetail!!.townName,
                    characterImage = characterDetail?.characterImage,
                    avatarImage = avatarImage
                )
                viewModel.saveCharacter(character)
            },
            enabled = !viewModel.isSaved
        ) {
            Text(text = "가져오기")
        }
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


@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {

    }
}


@Composable
fun CustomSuggestionChip(labelText: String) {
    SuggestionChip(
        shape = MaterialTheme.shapes.small,
        enabled = false,
        onClick = { /*TODO*/ },
        label = { Text(text = labelText) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            disabledContainerColor = Color.Transparent,
            disabledLabelColor = Color.Black
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            disabledBorderColor = Color.Black
        )
    )
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
