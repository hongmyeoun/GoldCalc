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
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
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

    val showAccDialog by viewModel.showAccDialog.collectAsState()
    val showBraDialog by viewModel.showBraDialog.collectAsState()

    if (showAccDialog) {
        AccessoryDetailDialog(viewModel, characterEquipment)
    } else if (showBraDialog) {
        Dialog(
            onDismissRequest = { viewModel.onBraDismissRequest() }
        ) {
            Column(
                modifier = Modifier
                    .background(ImageBG, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "팔찌",
                    style = titleTextStyle()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                characterEquipment.forEach {
                    when (it) {
                        is Bracelet -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(
                                            brush = viewModel.getItemBG(it.grade),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                ) {
                                    GlideImage(
                                        modifier = Modifier.size(56.dp),
                                        model = it.itemIcon,
                                        contentDescription = "팔찌 이미지"
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = it.name,
                                    style = titleTextStyle()
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            it.extraStats.forEach { (statName, statValue) ->
                                Row {
                                    TextChip(
                                        text = "특성",
                                        borderless = true,
                                        customBGColor = LightGrayBG
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))

                                    TextChip(
                                        text = "$statName $statValue",
                                        borderless = true,
                                        customBGColor = LightGrayBG
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            it.specialEffect.forEach { (effectName, tooltip) ->
                                Column {
                                    Row {
                                        TextChip(
                                            text = "효과",
                                            borderless = true,
                                            customBGColor = LightGrayBG
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))

                                        TextChip(
                                            text = effectName,
                                            borderless = true,
                                            customBGColor = LightGrayBG
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .background(LightGrayBG, RoundedCornerShape(4.dp))
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = tooltip,
                                            style = normalTextStyle()
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
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
                    .noRippleClickable { viewModel.onAccClicked() }
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
        onDismissRequest = { viewModel.onAccDismissRequest() }
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
        Dialog(
            onDismissRequest = { }
        ) {
            Column(
                modifier = Modifier
                    .background(ImageBG, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "팔찌",
                    style = titleTextStyle()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                brush = AncientBG,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    ) {
                        GlideImage(
                            modifier = Modifier.size(56.dp),
                            loading = placeholder(painterResource(id = R.drawable.sm_item_01_172)),
                            model = "",
                            contentDescription = "팔찌 이미지"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "찬란한 영웅의 팔찌",
                        style = titleTextStyle()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    TextChip(
                        text = "특성",
                        borderless = true,
                        customBGColor = LightGrayTransBG
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    TextChip(
                        text = "신속 99",
                        borderless = true,
                        customBGColor = LightGrayTransBG
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    TextChip(
                        text = "특성",
                        borderless = true,
                        customBGColor = LightGrayTransBG
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    TextChip(
                        text = "특화 99",
                        borderless = true,
                        customBGColor = LightGrayTransBG
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    Row {
                        TextChip(
                            text = "효과",
                            borderless = true,
                            customBGColor = LightGrayTransBG
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        TextChip(
                            text = "강타",
                            borderless = true,
                            customBGColor = LightGrayTransBG
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .background(LightGrayTransBG, RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "치명타 적중 후 4초 동안 제압이 100, 숙련이 100 증가한다.",
                            style = normalTextStyle()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    Row {
                        TextChip(
                            text = "효과",
                            borderless = true,
                            customBGColor = LightGrayTransBG
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        TextChip(
                            text = "약점 노출",
                            borderless = true,
                            customBGColor = LightGrayTransBG
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .background(LightGrayTransBG, RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "몬스터에게 공격 적중 시 8초 동안 대상의 치명타 저항을 1.8% 감소시킨다. '약점 노출' 효과는 하나의 대상에게 최대 1개만 적용된다. (60레벨 초과 몬스터에게는 효과 감소)",
                            style = normalTextStyle()
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
    Column(
        modifier = modifier.noRippleClickable { viewModel.onBraClicked() }
    ) {
        Text(text = "팔찌", style = titleTextStyle())
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            characterEquipment.forEach {
                when (it) {
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