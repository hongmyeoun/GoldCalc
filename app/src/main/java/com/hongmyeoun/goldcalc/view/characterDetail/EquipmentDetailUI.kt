package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.BlueQual
import com.hongmyeoun.goldcalc.ui.theme.EstherBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.ui.theme.PurpleQual
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import com.hongmyeoun.goldcalc.ui.theme.YellowQual

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
    val combatStatOne = combatStat?.sumOf { it.split(" ")[1].removePrefix("+").toInt() } ?: 0
    val combatStatTwo =
        characterEquipment?.filterIsInstance<CharacterAccessory>()?.get(0)?.combatStat2?.split(" ")?.get(1)?.removePrefix("+")?.toInt() ?: 0
    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        Text(
            text = "장비",
            style = titleTextStyle(),
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextChip(text = formattedSetOptions?.joinToString(", ") ?: "세트효과 없음")
            Spacer(modifier = Modifier.width(8.dp))
            TextChip(text = "악세 품질 $accessoryQualityAvg")
            Spacer(modifier = Modifier.width(8.dp))
            TextChip(text = "특성합 ${combatStatOne + combatStatTwo}")
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Column(modifier = Modifier.weight(0.7f)) {
                characterEquipment?.let { characterEquipmentList ->
                    characterEquipmentList.forEach {
                        when (it) {
                            is CharacterEquipment -> {
                                val setOption = it.setOption.split(" ").first()
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
                                    higherUpgrade = it.highUpgradeLevel,
                                    setOption = setOption
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
                                    quality = "${it.itemQuality}",
                                    combatStat1 = it.combatStat1,
                                    combatStat2 = it.combatStat2
                                )
                            }

                            is AbilityStone -> {
                                val abilityStone =
                                    it.engraving1Lv.substringAfter("Lv. ") + it.engraving2Lv.substringAfter("Lv. ") + it.engraving3Lv.substringAfter("Lv. ")
                                AccessoryDetails(
                                    icon = it.itemIcon,
                                    name = "스톤",
                                    grade = it.grade,
                                    quality = abilityStone,
                                    engraving1 = it.engraving1Op,
                                    engraving2 = it.engraving2Op
                                )
                            }
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
    setOption: String,
) {
    val fontSize = if (name.length >= 3) 8.sp else 10.sp

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = getItemBG(grade),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            GlideImage(
                modifier = Modifier
                    .size(48.dp),
                model = icon,
                contentDescription = "장비 아이콘",
            )
            TextChip(text = name, customTextSize = fontSize)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            UpgradeQualityRow(quality, upgrade, itemLevel)
            if (transcendenceLevel.isNotEmpty() || higherUpgrade.isNotEmpty()) {
                TranscendenceLevelRow(transcendenceLevel, transcendenceMultiple, higherUpgrade, setOption)
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
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun getItemBG(grade: String): Brush {
    val itemBG = when (grade) {
        "에스더" -> EstherBG
        "고대" -> AncientBG
        "유물" -> RelicBG
        else -> LegendaryBG
    }
    return itemBG
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
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = Color.White
                ),
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (itemLevel.isNotEmpty()) {
            TextChip(itemLevel)
            Spacer(modifier = Modifier.width(4.dp))
        }
        TextChip(
            text = quality,
            borderless = true,
            customPadding = Modifier.padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
            customBG = getQualityColor(quality)
        )
    }
}

private fun getQualityColor(quality: String): Color {
    return if (quality.toInt() >= 100) {
        OrangeQual
    } else if (quality.toInt() in 90 until 100) {
        PurpleQual
    } else if (quality.toInt() in 70 until 90) {
        BlueQual
    } else if (quality.toInt() in 30 until 70) {
        GreenQual
    } else if (quality.toInt() in 10 until 30) {
        YellowQual
    } else {
        RedQual
    }
}

private fun getElixirColor(level: String): Color {
    return when (level.toInt()) {
        5 -> RelicColor
        4 -> OrangeQual
        3 -> PurpleQual
        2 -> BlueQual
        else -> GreenQual
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun TranscendenceLevelRow(level: String, multiple: String, upgrade: String, setOption: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (level.isNotEmpty()) {
            GlideImage(
                model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png",
                contentDescription = "초월 아이콘"
            )
            Text(
                text = "Lv.$level",
                style = normalTextStyle()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "x$multiple",
                style = normalTextStyle()
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (upgrade.isNotEmpty()) {
            Text(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height + 1.sp.toPx()
                    drawLine(
                        color = GreenQual,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                text = "+$upgrade",
                style = normalTextStyle()
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (setOption.isNotEmpty()) {
            Text(
                text = setOption,
                style = normalTextStyle()
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
        TextChip(
            text = level,
            borderless = true,
            customPadding = Modifier.padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
            customBG = getElixirColor(level)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = option,
            style = normalTextStyle()
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun AccessoryDetails(
    icon: String,
    name: String,
    grade: String,
    quality: String,
    combatStat1: String = "",
    combatStat2: String = "",
    engraving1: String = "",
    engraving2: String = "",
) {
    val fontSize = if (name.length >= 3) 8.sp else 10.sp

    Row {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = getItemBG(grade),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            GlideImage(
                modifier = Modifier
                    .size(48.dp)
                ,
                model = icon,
                contentDescription = "악세서리 이미지"
            )
            TextChip(text = name, customTextSize = fontSize)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            UpgradeQualityRow(quality)
            if (combatStat1.isNotEmpty()) {
                Column {
                    Text(
                        text = combatStat1,
                        style = normalTextStyle()
                    )
                    if (combatStat2.isNotEmpty() && name == "목걸이") {
                        Text(
                            text = combatStat2,
                            style = normalTextStyle()
                        )
                    }
                }
            }
            if (name == "스톤") {
                Column {
                    Text(
                        text = engraving1,
                        style = normalTextStyle()
                    )
                    Text(
                        text = engraving2,
                        style = normalTextStyle()
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}