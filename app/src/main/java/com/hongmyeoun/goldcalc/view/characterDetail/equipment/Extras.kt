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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.Bracelet
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.characterDetail.TextChip
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun ExtraSimple(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
) {
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
                    customRoundedCornerSize = 8.dp,
                    borderless = true
                )
            }
        }
    }
}

@Composable
fun BraceletDetailDialog(
    viewModel: EquipmentDetailVM,
    characterEquipment: List<CharacterItem>
) {
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
                        BraDetailTop(viewModel, it)

                        it.extraStats.forEach { (statName, statValue) ->
                            BraDetailStats(statName, statValue)
                        }

                        it.specialEffect.forEach { (effectName, tooltip) ->
                            BraDetailEffects(effectName, tooltip)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun BraDetailTop(
    viewModel: EquipmentDetailVM,
    it: Bracelet
) {
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
}

@Composable
private fun BraDetailStats(statName: String, statValue: String) {
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

@Composable
private fun BraDetailEffects(effectName: String, tooltip: String) {
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
