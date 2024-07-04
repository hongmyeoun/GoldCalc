package com.hongmyeoun.goldcalc.view.profile.content.equipments.equipment

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
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.content.equipments.UpgradeQualityRow
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun Equipment(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
) {
    Column(
        modifier = modifier
    ) {
        characterEquipment.forEach {
            when (it) {
                is CharacterEquipment -> {
                    EquipmentUI(
                        equipment = it,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun EquipmentUI(
    equipment: CharacterEquipment,
    viewModel: EquipmentDetailVM,
) {
    val setOptionName = viewModel.setOptionName(equipment)

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun EquipmentIcon(
    equipment: CharacterEquipment,
    viewModel: EquipmentDetailVM,
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
                                    model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png",
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
}
