package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.searchedInfo.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.Bracelet
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterItem
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@Composable
fun CharacterDetailScreen(charName: String, viewModel: CharDetailVM = hiltViewModel()) {
    val context = LocalContext.current
    var characterDetail by remember { mutableStateOf<CharacterDetail?>(null) }
    var characterEquipment by remember { mutableStateOf<List<CharacterItem>?>(null) }

    LaunchedEffect(Unit) {
        characterDetail = APIRemote.getCharDetail(context, charName)
        characterEquipment = APIRemote.getCharEquipment(context, charName)
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
        characterEquipment?.let {
            it.forEach { characterItem ->
                when (characterItem) {
                    is CharacterEquipment -> Text(text = characterItem.type)
                    is CharacterAccessory -> Text(text = characterItem.name)
                    is AbilityStone -> Text(text = characterItem.name)
                    is Bracelet -> Text(text = characterItem.name)
                }
            }
        }
    }

}