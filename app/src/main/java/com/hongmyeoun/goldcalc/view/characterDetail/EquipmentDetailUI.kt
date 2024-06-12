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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun EquipmentDetailUI(
    characterEquipment: List<CharacterItem>,
) {
    val viewModel = EquipmentDetailVM(characterEquipment)

    val accessoryQualityAvg by viewModel.accessoryAvgQuality.collectAsState()
    val setOption by viewModel.setOption.collectAsState()
    val totalCombatStat by viewModel.totalCombatStat.collectAsState()

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = "장비",
            style = titleTextStyle(),
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextChip(text = setOption)
            Spacer(modifier = Modifier.width(8.dp))
            TextChip(text = "악세 품질 $accessoryQualityAvg")
            Spacer(modifier = Modifier.width(8.dp))
            TextChip(text = "특성합 $totalCombatStat")
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Column(modifier = Modifier.weight(0.7f)) {
                characterEquipment.forEach {
                    when (it) {
                        is CharacterEquipment -> {
                            val setOptionName = viewModel.setOptionName(it)
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
                                setOption = setOptionName,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
            Column(modifier = Modifier.weight(0.4f)) {
                characterEquipment.forEach {
                    when (it) {
                        is CharacterAccessory -> {
                            AccessoryDetails(
                                icon = it.itemIcon,
                                name = it.type,
                                grade = it.grade,
                                quality = "${it.itemQuality}",
                                combatStat1 = it.combatStat1,
                                combatStat2 = it.combatStat2,
                                viewModel = viewModel
                            )
                        }

                        is AbilityStone -> {
                            val abilityStone = viewModel.abilityStone(it)
                            AccessoryDetails(
                                icon = it.itemIcon,
                                name = "스톤",
                                grade = it.grade,
                                quality = abilityStone,
                                engraving1 = it.engraving1Op,
                                engraving2 = it.engraving2Op,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun EquipmentDetails(
    viewModel: EquipmentDetailVM,
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
                    brush = viewModel.getItemBG(grade),
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
            UpgradeQualityRow(
                quality = quality,
                upgrade = upgrade,
                itemLevel = itemLevel,
                viewModel = viewModel
            )
            if (transcendenceLevel.isNotEmpty() || higherUpgrade.isNotEmpty()) {
                TranscendenceLevelRow(transcendenceLevel, transcendenceMultiple, higherUpgrade, setOption)
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        if (elixir1Lv.isNotEmpty()) {
            Column {
                ElixirLevelOptionRow(
                    level = elixir1Lv,
                    option = elixir1Op,
                    viewModel = viewModel
                )
                ElixirLevelOptionRow(
                    level = elixir2Lv,
                    option = elixir2Op,
                    viewModel = viewModel
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun UpgradeQualityRow(
    quality: String,
    viewModel: EquipmentDetailVM,
    upgrade: String = "",
    itemLevel: String = ""
) {
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
            customBG = viewModel.getQualityColor(quality)
        )
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
private fun ElixirLevelOptionRow(
    level: String,
    option: String,
    viewModel: EquipmentDetailVM
) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextChip(
            text = level,
            borderless = true,
            customPadding = Modifier.padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
            customBG = viewModel.getElixirColor(level)
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
    viewModel: EquipmentDetailVM,
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
                    brush = viewModel.getItemBG(grade),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            GlideImage(
                modifier = Modifier
                    .size(48.dp),
                model = icon,
                contentDescription = "악세서리 이미지"
            )
            TextChip(text = name, customTextSize = fontSize)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            UpgradeQualityRow(
                quality = quality,
                viewModel = viewModel
            )
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