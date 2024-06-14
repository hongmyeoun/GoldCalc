package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.RedQual
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
                            EquipmentDetails(
                                equipment = it,
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
                                accessory = it,
                                viewModel = viewModel
                            )
                        }

                        is AbilityStone -> {
                            AccessoryDetails(
                                abilityStone = it,
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
fun EquipmentDetails(
    equipment: CharacterEquipment,
    viewModel: EquipmentDetailVM,
) {
    val setOptionName = viewModel.setOptionName(equipment)

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        EquipmentIcon(
            equipment = equipment,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            UpgradeQualityRow(
                viewModel = viewModel,
                grade = equipment.grade,
                upgrade = equipment.upgradeLevel,
                itemLevel = equipment.itemLevel,
            )
            if (equipment.transcendenceLevel.isNotEmpty() || equipment.highUpgradeLevel.isNotEmpty() || setOptionName.isNotEmpty()) {
                TranscendenceLevelRow(
                    level = equipment.transcendenceLevel,
                    upgrade = equipment.highUpgradeLevel,
                    setOption = setOptionName
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        if (equipment.elixirFirstLevel.isNotEmpty()) {
            Column {
                ElixirLevelOptionRow(
                    level = equipment.elixirFirstLevel,
                    option = equipment.elixirFirstOption,
                    viewModel = viewModel
                )
                ElixirLevelOptionRow(
                    level = equipment.elixirSecondLevel,
                    option = equipment.elixirSecondOption,
                    viewModel = viewModel
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun UpgradeQualityRow(
    viewModel: EquipmentDetailVM,
    grade: String,
    upgrade: String = "",
    itemLevel: String = "",
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
                    color = viewModel.getGradeBG(grade)
                ),
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (itemLevel.isNotEmpty()) {
            TextChip(itemLevel)
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (upgrade.isEmpty()) {
            TextChip(
                text = grade,
                borderless = true,
                customPadding = Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                customBGColor = viewModel.getGradeBG(grade, false)
            )
        }
    }
}

@Composable
private fun TranscendenceLevelRow(level: String, upgrade: String, setOption: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (level.isNotEmpty()) {
            Text(
                text = "Lv.$level",
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
            customBGColor = viewModel.getElixirColor(level)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = option,
            style = normalTextStyle()
        )
    }
}

@Composable
fun AccessoryDetails(
    accessory: CharacterAccessory,
    viewModel: EquipmentDetailVM,
) {
    Row {
        EquipmentIcon(
            accessory = accessory,
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.width(4.dp))
        Column {
            UpgradeQualityRow(
                viewModel = viewModel,
                grade = accessory.grade
            )
            if (accessory.combatStat1.isNotEmpty()) {
                Column {
                    Text(
                        text = accessory.combatStat1,
                        style = normalTextStyle()
                    )
                    if (accessory.combatStat2.isNotEmpty() && accessory.type == "목걸이") {
                        Text(
                            text = accessory.combatStat2,
                            style = normalTextStyle()
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun AccessoryDetails(
    abilityStone: AbilityStone,
    viewModel: EquipmentDetailVM,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        EquipmentIcon(
            abilityStone = abilityStone,
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.width(4.dp))
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = abilityStone.engraving1Op,
                style = normalTextStyle()
            )
            Text(
                text = abilityStone.engraving2Op,
                style = normalTextStyle()
            )
            Text(
                text = abilityStone.engraving3Op,
                style = normalTextStyle(RedQual)
            )
        }

    }
    Spacer(modifier = Modifier.height(4.dp))
}


@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EquipmentIcon(
    equipment: CharacterEquipment,
    viewModel: EquipmentDetailVM,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                brush = viewModel.getItemBG(equipment.grade),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
    ) {
        GlideImage(
            modifier = Modifier
                .size(48.dp),
            model = equipment.itemIcon,
            contentDescription = "장비 아이콘",
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(2.dp)
        ) {
            TextChip(
                text = equipment.type,
                borderless = true,
                customBGColor = BlackTransBG,
                customRoundedCornerSize = 8.dp,
                customTextSize = 8.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            if (equipment.transcendenceLevel.isNotEmpty()) {
                Box {
                    Column {
                        TextChip(
                            text = equipment.transcendenceTotal,
                            borderless = true,
                            customBGColor = BlackTransBG,
                            customTextSize = 8.sp,
                            customPadding = Modifier.padding(start = 2.dp, end = 2.dp),
                            image = true,
                            yourImage = {
                                GlideImage(
                                    modifier = Modifier
                                        .size(11.dp),
                                    model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png",
                                    contentDescription = "초월 아이콘"
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .align(Alignment.BottomCenter)
                ,
                progress = equipment.itemQuality * 0.01f,
                color = viewModel.getQualityColor(equipment.itemQuality.toString()),
                trackColor = ImageBG
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                ,
                text = "${equipment.itemQuality}",
                style = smallTextStyle(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EquipmentIcon(
    accessory: CharacterAccessory,
    viewModel: EquipmentDetailVM,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                brush = viewModel.getItemBG(accessory.grade),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
    ) {
        GlideImage(
            modifier = Modifier
                .size(48.dp),
            model = accessory.itemIcon,
            contentDescription = "장비 아이콘",
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(2.dp)
        ) {
            TextChip(
                text = accessory.type,
                borderless = true,
                customBGColor = BlackTransBG,
                customRoundedCornerSize = 8.dp,
                customTextSize = 8.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .align(Alignment.BottomCenter)
                ,
                progress = accessory.itemQuality * 0.01f,
                color = viewModel.getQualityColor(accessory.itemQuality.toString()),
                trackColor = ImageBG
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                ,
                text = "${accessory.itemQuality}",
                style = smallTextStyle(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EquipmentIcon(
    abilityStone: AbilityStone,
    viewModel: EquipmentDetailVM,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                brush = viewModel.getItemBG(abilityStone.grade),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
    ) {
        GlideImage(
            modifier = Modifier
                .size(48.dp),
            model = abilityStone.itemIcon,
            contentDescription = "장비 아이콘",
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(2.dp)
        ) {
            TextChip(
                text = "스톤",
                borderless = true,
                customBGColor = BlackTransBG,
                customRoundedCornerSize = 8.dp,
                customTextSize = 8.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Row {
                TextChip(
                    text = abilityStone.engraving1Lv,
                    borderless = true,
                    customTextSize = 8.sp,
                    customBGColor = viewModel.getStoneColor(abilityStone.engraving1Lv),
                    customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                    customRoundedCornerSize = 5.dp
                )
                Spacer(modifier = Modifier.width(1.dp))
                TextChip(
                    text = abilityStone.engraving2Lv,
                    borderless = true,
                    customTextSize = 8.sp,
                    customBGColor = viewModel.getStoneColor(abilityStone.engraving2Lv),
                    customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                    customRoundedCornerSize = 5.dp
                )
                Spacer(modifier = Modifier.width(1.dp))
                TextChip(
                    text = abilityStone.engraving3Lv,
                    borderless = true,
                    customTextSize = 8.sp,
                    customBGColor = viewModel.getStoneColor(abilityStone.engraving3Lv),
                    customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                    customRoundedCornerSize = 5.dp
                )
            }
        }
    }
}