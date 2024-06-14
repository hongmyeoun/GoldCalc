package com.hongmyeoun.goldcalc.view.characterDetail.equipment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.characterDetail.TextChip
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun EquipmentDetailUI(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
) {
    val accessoryQualityAvg by viewModel.accessoryAvgQuality.collectAsState()
    val setOption by viewModel.setOption.collectAsState()
    val totalCombatStat by viewModel.totalCombatStat.collectAsState()

    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
        AccessoryDetailDialog(viewModel, characterEquipment)
    }

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
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .noRippleClickable { viewModel.onClicked() }
            ) {
                characterEquipment.forEach {
                    when (it) {
                        is CharacterAccessory -> {
                            AccessorySimple(
                                accessory = it,
                                viewModel = viewModel
                            )
                        }

                        is AbilityStone -> {
                            AccessorySimple(
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
private fun AccessoryDetailDialog(
    viewModel: EquipmentDetailVM,
    characterEquipment: List<CharacterItem>
) {
    Dialog(
        onDismissRequest = { viewModel.onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .background(ImageBG, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "장신구",
                style = titleTextStyle()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            characterEquipment.forEach {
                when (it) {
                    is CharacterAccessory -> {
                        AccessoryDetail(
                            accessory = it,
                            viewModel = viewModel
                        )
                    }

                    is AbilityStone -> {
                        AccessoryDetail(
                            abilityStone = it,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun UpgradeQualityRow(
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

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {

    }
}



