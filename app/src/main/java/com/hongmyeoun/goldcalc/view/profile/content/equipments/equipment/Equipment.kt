package com.hongmyeoun.goldcalc.view.profile.content.equipments.equipment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.LOSE_DAMAGE
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.LOSE_DAMAGE_SHORT
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.content.equipments.EquipmentIcon
import com.hongmyeoun.goldcalc.view.profile.content.equipments.UpgradeQualityRow
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM


@Composable
fun Equipment(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    Column(
        modifier = modifier
    ) {
        val equipment = characterEquipment.filterIsInstance<CharacterEquipment>()

        if (equipment.find { it.type == EquipmentConsts.HEAD } != null) {
            EquipmentUI(
                equipment = equipment.find { it.type == EquipmentConsts.HEAD }!!,
                viewModel = viewModel,
                isArkPassive = isArkPassive
            )
        } else {
            EquipmentIcon(
                type = EquipmentConsts.HEAD,
                viewModel = viewModel
            )
        }

        if (equipment.find { it.type == EquipmentConsts.SHOULDER } != null) {
            EquipmentUI(
                equipment = equipment.find { it.type == EquipmentConsts.SHOULDER }!!,
                viewModel = viewModel,
                isArkPassive = isArkPassive
            )
        } else {
            EquipmentIcon(
                type = EquipmentConsts.SHOULDER,
                viewModel = viewModel
            )
        }

        if (equipment.find { it.type == EquipmentConsts.CHEST } != null) {
            EquipmentUI(
                equipment = equipment.find { it.type == EquipmentConsts.CHEST }!!,
                viewModel = viewModel,
                isArkPassive = isArkPassive
            )
        } else {
            EquipmentIcon(
                type = EquipmentConsts.CHEST,
                viewModel = viewModel
            )
        }

        if (equipment.find { it.type == EquipmentConsts.PANTS } != null) {
            EquipmentUI(
                equipment = equipment.find { it.type == EquipmentConsts.PANTS }!!,
                viewModel = viewModel,
                isArkPassive = isArkPassive
            )
        } else {
            EquipmentIcon(
                type = EquipmentConsts.PANTS,
                viewModel = viewModel
            )
        }

        if (equipment.find { it.type == EquipmentConsts.GLOVES } != null) {
            EquipmentUI(
                equipment = equipment.find { it.type == EquipmentConsts.GLOVES }!!,
                viewModel = viewModel,
                isArkPassive = isArkPassive
            )
        } else {
            EquipmentIcon(
                type = EquipmentConsts.GLOVES,
                viewModel = viewModel
            )
        }

        if (equipment.find { it.type == EquipmentConsts.WEAPON } != null) {
            EquipmentUI(
                equipment = equipment.find { it.type == EquipmentConsts.WEAPON }!!,
                viewModel = viewModel,
                isArkPassive = isArkPassive
            )
        } else {
            EquipmentIcon(
                type = EquipmentConsts.WEAPON,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun EquipmentUI(
    equipment: CharacterEquipment,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    val setOptionName = viewModel.setOptionName(equipment)
    val arkPassiveOffsetValue = if (isArkPassive) Modifier.offset(x = (-8).dp, y = (-8).dp) else Modifier

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        EquipmentIcon(
            equipment = equipment,
            viewModel = viewModel,
            isArkPassive = isArkPassive,
            modifier = arkPassiveOffsetValue
        )
        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = arkPassiveOffsetValue
        ) {
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
            Column(
                modifier = arkPassiveOffsetValue
            ) {
                ElixirLevelOptionRow(
                    level = equipment.elixirFirstLevel,
                    option = equipment.elixirFirstOption,
                    viewModel = viewModel
                )
                if (equipment.elixirSecondLevel.isNotEmpty()) {
                    ElixirLevelOptionRow(
                        level = equipment.elixirSecondLevel,
                        option = equipment.elixirSecondOption,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
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
    viewModel: EquipmentVM
) {
    val optionStr = if (option == LOSE_DAMAGE) LOSE_DAMAGE_SHORT else option
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
            text = optionStr,
            style = normalTextStyle()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun EquipmentIcon(
    equipment: CharacterEquipment,
    viewModel: EquipmentVM,
    isArkPassive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    brush = viewModel.getItemBG(equipment.grade),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        ) {
            GlideImage(
                modifier = Modifier
                    .size(56.dp),
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
                                customPadding = Modifier.padding(start = 2.dp, end = 2.dp),
                                image = true,
                                yourImage = {
                                    GlideImage(
                                        modifier = Modifier
                                            .size(13.dp),
                                        model = NetworkConfig.TRANSCENDENCE_ICON,
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
                    progress = equipment.itemQuality * 0.01f,
                    color = viewModel.getQualityColor(equipment.itemQuality.toString()),
                    trackColor = ImageBG
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    text = "${equipment.itemQuality}",
                    style = normalTextStyle(),
                    textAlign = TextAlign.Center
                )
            }
        }

        if (isArkPassive) {
            GlideImage(
                modifier = Modifier
                    .size(72.dp),
                model = NetworkConfig.ARK_PASSIVE_EQUIP,
                contentDescription = "악세 테두리",
            )
        }
    }

}
