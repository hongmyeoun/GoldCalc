package com.hongmyeoun.goldcalc.view.characterDetail.equipment

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.Bracelet
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.characterDetail.TextChip
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@OptIn(ExperimentalGlideComposeApi::class)
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
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .background(LightGrayTransBG)
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExtraEquipmentsBracelet(
                modifier = Modifier.weight(1.5f),
                characterEquipment = characterEquipment,
                viewModel = viewModel,
            )

            Divider(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            ExtraEquipmentsElixir(
                modifier = Modifier.weight(1f),
                characterEquipment = characterEquipment,
                viewModel = viewModel,
            )


            Divider(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            ExtraTrans(
                modifier = Modifier.weight(1f),
                characterEquipment = characterEquipment,
                viewModel = viewModel
            )
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
        Row(
            modifier = Modifier
                .background(LightGrayTransBG)
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                Text(text = "팔찌", style = titleTextStyle())
                Row {
                    GlideImage(
                        loading = placeholder(painterResource(id = R.drawable.sm_item_01_172)),
                        model = "",
                        contentDescription = "팔찌이미지"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Row {
                            Text(text = "신속 99", style = normalTextStyle())
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "특화 99", style = normalTextStyle())
                        }
                        Row {
                            TextChip(
                                text = "강타",
                                customTextSize = 8.sp,
                                customRoundedCornerSize = 8.dp,
                                borderless = true
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            TextChip(
                                text = "약점 노출",
                                customTextSize = 8.sp,
                                customRoundedCornerSize = 8.dp,
                                borderless = true
                            )
                        }
                    }
                }
            }

            Divider(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "엘릭서", style = titleTextStyle())
                Row {
                    Column {
                        Text(text = "합계 41", style = normalTextStyle())
                        TextChip(
                            text = "진군 2단계",
                            customTextSize = 8.sp,
                            customRoundedCornerSize = 8.dp,
                            borderless = true
                        )
                    }
                }
            }

            Divider(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "초월", style = titleTextStyle())
                Row {
                    Column {
                        Text(text = "합계 102", style = normalTextStyle())
                        TextChip(
                            text = "평균 5.8단계",
                            customTextSize = 8.sp,
                            customRoundedCornerSize = 8.dp,
                            borderless = true
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExtraEquipmentsBracelet(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
) {
    Column(modifier = modifier) {
        Text(text = "팔찌", style = titleTextStyle())
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            characterEquipment.forEach {
                when(it) {
                    is Bracelet -> {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(
                                    brush = viewModel.getItemBG(it.grade),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clip(RoundedCornerShape(4.dp))
                        ) {
                            GlideImage(
                                modifier = Modifier.size(34.dp),
                                model = it.itemIcon,
                                contentDescription = "팔찌이미지"
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            if (it.stats.isNotEmpty()) {
                                Row {
                                    it.stats.forEach { (statName, stat) ->
                                        Text(
                                            text = "$statName $stat",
                                            style = normalTextStyle()
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            if (it.specialEffect.isNotEmpty()) {
                                Row {
                                    it.specialEffect.forEach { (effectName, _) ->
                                        TextChip(
                                            text = effectName,
                                            customRoundedCornerSize = 8.dp,
                                            borderless = true
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ExtraEquipmentsElixir(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
) {
    Column(modifier = modifier) {
        Text(
            text = "엘릭서",
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "합계 ${viewModel.sumElixirLevel(characterEquipment)}",
                    style = normalTextStyle()
                )
                Spacer(modifier = Modifier.height(4.dp))

                TextChip(
                    text = viewModel.elixirSetOption(characterEquipment),
                    customTextSize = 8.sp,
                    customRoundedCornerSize = 8.dp,
                    borderless = true
                )
            }
        }
    }
}

@Composable
fun ExtraTrans(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
) {
    Column(modifier = modifier) {
        Text(
            text = "초월",
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "합계 ${viewModel.sumTransLevel(characterEquipment)}",
                    style = normalTextStyle()
                )
                Spacer(modifier = Modifier.height(4.dp))

                TextChip(
                    text = "평균 ${viewModel.avgTransLevel(characterEquipment)}단계",
                    customTextSize = 8.sp,
                    customRoundedCornerSize = 8.dp,
                    borderless = true
                )
            }
        }
    }

}