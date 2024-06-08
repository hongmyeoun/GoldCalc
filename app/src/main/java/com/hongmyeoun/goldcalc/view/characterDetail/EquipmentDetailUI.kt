package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastSumBy
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.HigherUpgradeColor
import com.hongmyeoun.goldcalc.ui.theme.RelicBG

@Composable
fun EquipmentDetailUI(characterEquipment: List<CharacterItem>?) {
    val accessoryTotalQuality = characterEquipment?.filterIsInstance<CharacterAccessory>()?.map { it.itemQuality }
    val accessoryQualityAvg = if (accessoryTotalQuality?.isNotEmpty() == true) {
        accessoryTotalQuality.average()
    } else {
        0.0
    }

    val setOptions = characterEquipment?.filterIsInstance<CharacterEquipment>()?.map { it.setOption }
    val setOptionGroups = setOptions?.map { it.split(" ") }?.groupBy({ it[0] }, { it[1].toInt() })
    val formattedSetOptions = setOptionGroups?.map { (option, levels) ->
        val count = levels.size
        val averageLevel = levels.average()

        val formattedLevel = if (averageLevel % 1 == 0.0) averageLevel.toInt().toString() else "%.1f".format(averageLevel)

        "$count$option Lv.$formattedLevel"
    }

    val combatStat = characterEquipment?.filterIsInstance<CharacterAccessory>()?.map { it.combatStat1 }
    val combatStatOne = combatStat?.sumOf { it.split(" ")[1].removePrefix("+").toInt() }?:0
    val combatStatTwo = characterEquipment?.filterIsInstance<CharacterAccessory>()?.get(0)?.combatStat2?.split(" ")?.get(1)?.removePrefix("+")?.toInt()?:0




    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "장비", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        TextChip(text = formattedSetOptions?.joinToString(", ") ?: "세트효과 없음")
        Spacer(modifier = Modifier.width(8.dp))
        TextChip(text = "악세 품질 $accessoryQualityAvg")
        Spacer(modifier = Modifier.width(8.dp))
        TextChip(text = "특성합 ${combatStatOne + combatStatTwo}")
//            CustomSuggestionChip("특성합 ${combatStatTotalQuality?:0}")
    }

    Row {
        Column(modifier = Modifier.weight(0.7f)) {
            characterEquipment?.let { characterEquipmentList ->
                characterEquipmentList.forEach {
                    when (it) {
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
                    when (it) {
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
                                quality = it.engraving1Lv.substringAfter("Lv. ") + it.engraving2Lv.substringAfter("Lv. ") + it.engraving3Lv.substringAfter(
                                    "Lv. "
                                )
                            )

                        }
                    }
                }
            }

        }
    }
}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun EquipmentDetails(
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
    val fontSize = if (name.length >= 3) 8.sp else 10.sp

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
            GlideImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                model = icon,
                contentDescription = "장비 아이콘",
            )
            TextChip(text = name, customTextSize = fontSize)
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
@OptIn(ExperimentalGlideComposeApi::class)
private fun TranscendenceLevelRow(level: String, multiple: String, upgrade: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (level.isNotEmpty()) {
            GlideImage(
                model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png",
                contentDescription = "초월 아이콘"
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
private fun ElixirTextChip(
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

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun AccessoryDetails(
    icon: String,
    name: String,
    grade: String,
    quality: String
) {
    val itemBG = if (grade == "고대") AncientBG else RelicBG
    val fontSize = if (name.length >= 3) 8.sp else 10.sp

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
                contentDescription = "악세서리 이미지"
            )
            TextChip(text = name, customTextSize = fontSize)
        }
        Spacer(modifier = Modifier.width(4.dp))
        UpgradeQualityRow(quality)
    }
}

@Composable
private fun QualityTextChip(
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
