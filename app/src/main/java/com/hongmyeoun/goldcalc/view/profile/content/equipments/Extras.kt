package com.hongmyeoun.goldcalc.view.profile.content.equipments

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.NO_BRACELET
import com.hongmyeoun.goldcalc.model.constants.viewConst.Profile
import com.hongmyeoun.goldcalc.model.profile.equipment.Bracelet
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM

@Composable
fun Extra(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM
) {
    Row(
        modifier = Modifier
            .background(LightGrayTransBG)
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExtraBracelet(
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

        ExtraElixir(
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
fun ExtraBracelet(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM
) {
    val bracelet = characterEquipment.filterIsInstance<Bracelet>()

    Column(
        modifier = modifier.noRippleClickable { viewModel.onBraClicked() }
    ) {
        Text(
            text = EquipmentConsts.BRACELET,
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (bracelet.find { it.type == EquipmentConsts.BRACELET } != null) {
                val it = bracelet.find { it.type == EquipmentConsts.BRACELET }!!
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
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        ) {
                            it.specialEffect.forEach { (effectName, tooltip) ->
                                if (effectName in EquipmentConsts.BRACELET_SPECIAL_EFFECT_LIST) {
                                    val effectOptionLevel = viewModel.braceletOptionLevel(effectName, tooltip)

                                    TextChip(
                                        text = effectName,
                                        customBGColor = viewModel.grindOptionColor(effectOptionLevel),
                                        customRoundedCornerSize = 8.dp,
                                        borderless = true
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                            }
                        }
                    }
                }
            } else {
                GlideImage(
                    modifier = Modifier.size(34.dp),
                    model = viewModel.nullEquipmentIcon(EquipmentConsts.BRACELET),
                    contentDescription = "팔찌 이미지"
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = NO_BRACELET,
                    style = titleTextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}

@Composable
fun ExtraElixir(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM
) {
    Column(modifier = modifier) {
        Text(
            text = Profile.ELIXIR,
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${Profile.TOTAL} ${viewModel.sumElixirLevel(characterEquipment)}",
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
    viewModel: EquipmentVM
) {
    Column(modifier = modifier) {
        Text(
            text = Profile.TRANSCENDENT,
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${Profile.TOTAL} ${viewModel.sumTransLevel(characterEquipment)}",
                    style = normalTextStyle()
                )
                Spacer(modifier = Modifier.height(4.dp))

                TextChip(
                    text = "${Profile.AVG} ${viewModel.avgTransLevel(characterEquipment)}단계",
                    customRoundedCornerSize = 8.dp,
                    borderless = true
                )
            }
        }
    }
}
