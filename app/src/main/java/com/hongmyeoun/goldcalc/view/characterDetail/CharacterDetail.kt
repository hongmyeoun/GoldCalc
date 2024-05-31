package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.HigherUpgradeColor
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
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
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "장비", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                CustomSuggestionChip("배신 333333")
                Spacer(modifier = Modifier.width(8.dp))
                CustomSuggestionChip("악세 품질 68")
                Spacer(modifier = Modifier.width(8.dp))
                CustomSuggestionChip("특성합 1875")
            }

            Row {

                Column(modifier = Modifier.weight(0.7f)) {
                    characterEquipment?.let { characterEquipmentList ->
                        characterEquipmentList.forEach {
                            when(it) {
                                is CharacterEquipment -> {
                                    EquipmentDetails(
                                        icon = it.itemIcon,
                                        grade = it.grade,
                                        name = it.type,
                                        upgrade = it.upgradeLevel,
                                        itemLevel = it.itemLevel,
                                        quality = "${it.itemQuality}",
                                        elixir1Lv = it.elixirFirstLevel,
                                        elixir1Op = it.elixirFirstOption,
                                        elixir2Lv = it.elixirSecondLevel,
                                        elixir2Op = it.elixirSecondOption,
                                        transcendenceLevel = it.transcendenceLevel,
                                        transcendenceMultiple = it.transcendenceTotal,
                                        higherUpgrade = it.highUpgradeLevel
                                    )
                                }
                            }
                        }
                    }
                }
                Column(modifier = Modifier.weight(0.4f)) {
                    characterEquipment?.let { characterEquipmentList ->
                        characterEquipmentList.forEach {
                            when(it) {
                                is CharacterAccessory -> {
                                    AccessoryDetails(
                                        icon = it.itemIcon,
                                        name = it.type,
                                        grade = it.grade,
                                        quality = "${it.itemQuality}"
                                    )
                                }
                                is AbilityStone -> {
                                    AccessoryDetails(
                                        icon = it.itemIcon,
                                        name = "스톤",
                                        grade = it.grade,
                                        quality = "68"
                                    )

                                }
                            }
                        }
                    }

                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            EquipmentDetails(
                icon = "",
                name = "투구",
                grade = "고대",
                upgrade = "+20",
                itemLevel = "1640",
                quality = "96",
                elixir1Lv = "4",
                elixir1Op = "진군 (질서)",
                elixir2Lv = "5",
                elixir2Op = "무기 공격력",
                transcendenceLevel = "7",
                transcendenceMultiple = "x20",
                higherUpgrade = "15"
            )
        }

    }
}

//@Composable
//fun EquipmentAndAccessoryDetails(
//    eqName :String,
//    eqLG: String,
//    eqQal: String,
//    eqUp: String,
//    elixir1Lv: String = "",
//    elixir1Op: String = "",
//    elixir2Lv: String = "",
//    elixir2Op: String = "",
//    level: String = "",
//    multiple: String = "",
//    accName: String,
//    accLG: String,
//    accQal: String,
//){
//    Row {
//        EquipmentDetails(
//            icon = "",
//            name = eqName,
//            levelOrGrade = eqLG,
//            quality = eqQal,
//            elixir1Lv = elixir1Lv,
//            elixir1Op = elixir1Op,
//            elixir2Lv = elixir2Lv,
//            elixir2Op = elixir2Op,
//            level = level,
//            multiple = multiple,
//            upgrade = eqUp
//        )
//        AccessoryDetails(
//            name = accName,
//            levelOrGrade = accLG,
//            quality = accQal,
//        )
//    }
//
//}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun AccessoryDetails(
    icon: String,
    name: String,
    grade: String,
    quality: String
) {
    val itemBG = if (grade == "고대") AncientBG else RelicBG

    Row {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = itemBG,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            GlideImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                model = icon,
                contentDescription = ""
            )
            TextChip(text = name)
        }
        Spacer(modifier = Modifier.width(4.dp))
        UpgradeQualityRow(quality)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun EquipmentDetails(
    icon: String,
    grade: String,
    name: String,
    upgrade: String,
    itemLevel: String,
    quality: String,
    elixir1Lv: String,
    elixir1Op: String,
    elixir2Lv: String,
    elixir2Op: String,
    transcendenceLevel: String,
    transcendenceMultiple: String,
    higherUpgrade: String,
) {
    val itemBG = if (grade == "고대") AncientBG else RelicBG

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = itemBG,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
//        GlideImage(
//            modifier = Modifier.size(48.dp),
//            model = icon,
//            contentDescription = "",
//        )
            GlideImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                model = icon,
//                loading = placeholder(painterResource(id = R.drawable.sm_item_01_172)),
                contentDescription = "",
            )
            TextChip(text = name)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            UpgradeQualityRow(quality, upgrade, itemLevel)
            if (transcendenceLevel.isNotEmpty() || higherUpgrade.isNotEmpty()) {
                TranscendenceLevelRow(transcendenceLevel, transcendenceMultiple, higherUpgrade)
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        if (elixir1Lv.isNotEmpty()) {
            Column {
                ElixirLevelOptionRow(elixir1Lv, elixir1Op)
                ElixirLevelOptionRow(elixir2Lv, elixir2Op)
            }
        }
    }

}

@Composable
private fun ElixirLevelOptionRow(level: String, option: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElixirTextChip(level)
        Text(text = option, fontSize = 10.sp)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun TranscendenceLevelRow(level: String, multiple: String, upgrade: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (level.isNotEmpty()) {
            GlideImage(
                model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png",
//                loading = placeholder(painter = painterResource(id = R.drawable.ico_tooltip_transcendence)),
                contentDescription = ""
            )
            Text(text = "Lv.$level", fontSize = 10.sp)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "x$multiple", fontSize = 10.sp)
            Spacer(modifier = Modifier.width(2.dp))
        }
        if (upgrade.isNotEmpty()) {
            Text(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = HigherUpgradeColor,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                text = "+$upgrade",
                fontSize = 10.sp,
            )
        }
    }
}

@Composable
private fun UpgradeQualityRow(quality: String, upgrade: String = "", itemLevel: String = "") {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (upgrade.isNotEmpty()) {
            Text(
                text = upgrade,
                fontSize = 10.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        if (itemLevel.isNotEmpty()) {
            TextChip(itemLevel)
        }
        QualityTextChip(quality)
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
) {
    val textSize = if (text.length == 3) 8.sp else 10.sp

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = textSize,
            color = Color.White
        )
    }
}

@Composable
fun QualityTextChip(
    text: String,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.White
        )
    }
}


@Composable
fun ElixirTextChip(
    text: String,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.White
        )
    }
}