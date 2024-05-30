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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.searchedInfo.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@OptIn(ExperimentalGlideComposeApi::class)
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
                                        name = it.type,
                                        levelOrGrade = it.itemLevel,
                                        quality = "${it.itemQuality}",
                                        elixir1Lv = it.elixirFirstLevel,
                                        elixir1Op = it.elixirFirstOption,
                                        elixir2Lv = it.elixirSecondLevel,
                                        elixir2Op = it.elixirSecondOption,
                                        level = it.transcendenceLevel,
                                        multiple = "x${it.transcendenceTotal}",
                                        upgrade = it.highUpgradeLevel
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
                                        levelOrGrade = it.grade,
                                        quality = "${it.itemQuality}"
                                    )
                                }
                                is AbilityStone -> {
                                    AccessoryDetails(
                                        icon = it.itemIcon,
                                        name = "스톤",
                                        levelOrGrade = it.grade,
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

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    GoldCalcTheme {
//        Column(modifier = Modifier.fillMaxSize()) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(text = "장비", fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.width(8.dp))
//                CustomSuggestionChip("배신 333333")
//                Spacer(modifier = Modifier.width(8.dp))
//                CustomSuggestionChip("악세 품질 68")
//                Spacer(modifier = Modifier.width(8.dp))
//                CustomSuggestionChip("특성합 1875")
//            }
//            EquipmentAndAccessoryDetails(
//                eqName = "무기 +20",
//                eqLG = "1635",
//                eqQal = "89",
//                eqUp = "+10",
//                accName = "목걸이",
//                accLG = "고대",
//                accQal = "71"
//            )
//            EquipmentAndAccessoryDetails(
//                eqName = "투구 +20",
//                eqLG = "1640",
//                eqQal = "96",
//                eqUp = "+15",
//                accName = "귀걸이",
//                accLG = "고대",
//                accQal = "70",
//                elixir1Lv = "5",
//                elixir1Op = "선각자 (질서)",
//                elixir2Lv = "1",
//                elixir2Op = "무력화",
//                level = "7",
//                multiple = "x20",
//            )
//            EquipmentAndAccessoryDetails(
//                eqName = "견갑 +20",
//                eqLG = "1637",
//                eqQal = "92",
//                eqUp = "+12",
//                accName = "귀걸이",
//                accLG = "고대",
//                accQal = "71",
//                elixir1Lv = "4",
//                elixir1Op = "공격력",
//                elixir2Lv = "5",
//                elixir2Op = "무기 공격력",
//                level = "7",
//                multiple = "x21",
//            )
//            EquipmentAndAccessoryDetails(
//                eqName = "상의 +20",
//                eqLG = "1640",
//                eqQal = "94",
//                eqUp = "+15",
//                accName = "반지",
//                accLG = "고대",
//                accQal = "57",
//                elixir1Lv = "2",
//                elixir1Op = "최대 생명력",
//                elixir2Lv = "5",
//                elixir2Op = "지능",
//                level = "7",
//                multiple = "x20",
//            )
//            EquipmentAndAccessoryDetails(
//                eqName = "하의 +20",
//                eqLG = "1640",
//                eqQal = "90",
//                eqUp = "+15",
//                accName = "반지",
//                accLG = "고대",
//                accQal = "57",
//                elixir1Lv = "1",
//                elixir1Op = "지능",
//                elixir2Lv = "4",
//                elixir2Op = "아군 강화",
//                level = "7",
//                multiple = "x21",
//            )
//            EquipmentAndAccessoryDetails(
//                eqName = "장갑 +20",
//                eqLG = "1635",
//                eqQal = "94",
//                eqUp = "+10",
//                accName = "스톤",
//                accLG = "유물",
//                accQal = "68",
//                elixir1Lv = "4",
//                elixir1Op = "선각자 (혼돈)",
//                elixir2Lv = "2",
//                elixir2Op = "마나",
//                level = "7",
//                multiple = "x20",
//            )
//
//        }
//
//    }
//}
//
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
    levelOrGrade: String,
    quality: String
) {
    Row {
        GlideImage(
            modifier = Modifier.size(24.dp),
            model = icon,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(4.dp))
        NameQualityRow(name, levelOrGrade, quality)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun EquipmentDetails(
    icon: String,
    name: String,
    levelOrGrade: String,
    quality: String,
    elixir1Lv: String,
    elixir1Op: String,
    elixir2Lv: String,
    elixir2Op: String,
    level: String,
    multiple: String,
    upgrade: String,
) {
    Row {
        GlideImage(
            modifier = Modifier.size(48.dp),
            model = icon,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            NameQualityRow(name, levelOrGrade, quality)
            if (level.isNotEmpty() || upgrade.isNotEmpty()) {
                TranscendenceLevelRow(level, multiple, upgrade)
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
                model = R.drawable.ico_tooltip_transcendence,
                loading = placeholder(painter = painterResource(id = R.drawable.ico_tooltip_transcendence)),
                contentDescription = ""
            )
            Text(text = level, fontSize = 10.sp)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = multiple, fontSize = 10.sp)
            Spacer(modifier = Modifier.width(2.dp))
        }
        if (upgrade.isNotEmpty()) {
            Text(text = upgrade, fontSize = 10.sp)
        }
    }
}

@Composable
private fun NameQualityRow(name: String, levelOrGrade: String, quality: String) {
    val nameText = if (name.length == 2) "$name   " else name
    Row(
        modifier = Modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = nameText,
            fontSize = 10.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(2.dp))
        TextChip(levelOrGrade)
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
            fontSize = 10.sp,
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