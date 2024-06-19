package com.hongmyeoun.goldcalc.view.characterDetail.equipment

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.characterDetail.TextChip
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleBoldWhite12
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun AccessorySimple(
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

@Composable
fun AccessoryDetail(
    abilityStone: AbilityStone,
    viewModel: EquipmentDetailVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        EquipmentIcon(
            abilityStone = abilityStone,
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = abilityStone.name,
                style = titleBoldWhite12()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = abilityStone.hpBonus,
                style = normalTextStyle(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                TextChip(
                    text = "${viewModel.getSimpleEngraving(abilityStone.engraving1Op)} ${abilityStone.engraving1Lv}",
                    customBGColor = LightGrayBG,
                    borderless = true
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextChip(
                    text = "${viewModel.getSimpleEngraving(abilityStone.engraving2Op)} ${abilityStone.engraving2Lv}",
                    customBGColor = LightGrayBG,
                    borderless = true
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextChip(
                    text = "${abilityStone.engraving3Op} ${abilityStone.engraving3Lv}",
                    customBGColor = RedQual,
                    borderless = true
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun EquipmentIcon(
    abilityStone: AbilityStone,
    viewModel: EquipmentDetailVM,
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
                if (abilityStone.engraving1Lv.isNotEmpty()) {
                    TextChip(
                        text = abilityStone.engraving1Lv,
                        borderless = true,
                        customBGColor = viewModel.getStoneColor(abilityStone.engraving1Lv),
                        customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                        customRoundedCornerSize = 5.dp
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                }
                if (abilityStone.engraving2Lv.isNotEmpty()) {
                    TextChip(
                        text = abilityStone.engraving2Lv,
                        borderless = true,
                        customBGColor = viewModel.getStoneColor(abilityStone.engraving2Lv),
                        customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                        customRoundedCornerSize = 5.dp
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                }
                if (abilityStone.engraving3Lv.isNotEmpty()) {
                    TextChip(
                        text = abilityStone.engraving3Lv,
                        borderless = true,
                        customBGColor = viewModel.getStoneColor(abilityStone.engraving3Lv),
                        customPadding = Modifier.padding(start = 4.75.dp, end = 4.75.dp, top = 2.dp, bottom = 2.dp),
                        customRoundedCornerSize = 5.dp
                    )
                }
            }
        }
    }
}
