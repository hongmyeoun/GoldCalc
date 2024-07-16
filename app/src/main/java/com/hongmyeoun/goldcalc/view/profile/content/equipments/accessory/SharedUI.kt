package com.hongmyeoun.goldcalc.view.profile.content.equipments.accessory

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.profile.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EquipmentIcon(
    accessory: CharacterAccessory,
    viewModel: EquipmentVM,
    isArkPassive: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    brush = viewModel.getItemBG(accessory.grade),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        ) {
            GlideImage(
                modifier = Modifier
                    .size(56.dp),
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
                if (!accessory.arkPassivePoint.isNullOrEmpty()) {
                    Box {
                        Column {
                            TextChip(
                                text = "${viewModel.arkPoint(accessory.arkPassivePoint)}",
                                borderless = true,
                                customBGColor = BlackTransBG,
                                customPadding = Modifier.padding(start = 2.dp, end = 2.dp),
                                image = true,
                                yourImage = {
                                    GlideImage(
                                        modifier = Modifier
                                            .size(13.dp),
                                        model = R.drawable.icon_arkpassive_enlightenment,
                                        contentDescription = "초월 아이콘"
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                }
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }

                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .align(Alignment.BottomCenter),
                    progress = accessory.itemQuality * 0.01f,
                    color = viewModel.getQualityColor(accessory.itemQuality.toString()),
                    trackColor = ImageBG
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    text = "${accessory.itemQuality}",
                    style = normalTextStyle(),
                    textAlign = TextAlign.Center
                )
            }
        }

        if (isArkPassive) {
            GlideImage(
                modifier = Modifier
                    .size(72.dp),
                model = NetworkConfig.ARK_PASSIVE_ACC,
                contentDescription = "악세 테두리",
            )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EquipmentIcon(
    abilityStone: AbilityStone,
    viewModel: EquipmentVM,
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                brush = viewModel.getItemBG(abilityStone.grade),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
    ) {
        GlideImage(
            modifier = Modifier
                .size(56.dp),
            model = abilityStone.itemIcon,
            contentDescription = "장비 아이콘",
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(2.dp)
        ) {
            TextChip(
                text = EquipmentConsts.ABILITY_STONE_SHORT,
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
                if (abilityStone.engraving1Lv != null) {
                    TextChip(
                        text = "${abilityStone.engraving1Lv}",
                        borderless = true,
                        customBGColor = viewModel.getStoneColor(abilityStone.engraving1Lv.toString()),
                        customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                        customRoundedCornerSize = 5.dp
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                }
                if (abilityStone.engraving2Lv != null) {
                    TextChip(
                        text = "${abilityStone.engraving2Lv}",
                        borderless = true,
                        customBGColor = viewModel.getStoneColor(abilityStone.engraving2Lv.toString()),
                        customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                        customRoundedCornerSize = 5.dp
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                }
                if (abilityStone.engraving3Lv != null) {
                    TextChip(
                        text = "${abilityStone.engraving3Lv}",
                        borderless = true,
                        customBGColor = viewModel.getStoneColor(abilityStone.engraving3Lv.toString()),
                        customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                        customRoundedCornerSize = 5.dp
                    )
                }
            }
        }
    }
}
