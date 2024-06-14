package com.hongmyeoun.goldcalc.view.characterDetail.equipment

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
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.characterDetail.TextChip
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleBoldWhite12
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun AccessorySimple(
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
fun AccessoryDetail(
    accessory: CharacterAccessory,
    viewModel: EquipmentDetailVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        EquipmentIcon(
            accessory = accessory,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = accessory.name,
                style = titleBoldWhite12()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = accessory.combatStat1,
                    style = normalTextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (accessory.type == "목걸이") {
                    Text(
                        text = accessory.combatStat2,
                        style = normalTextStyle(fontSize = 12.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                TextChip(
                    text = viewModel.simplyEngravingName(accessory.engraving1),
                    customBGColor = LightGrayBG,
                    borderless = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextChip(
                    text = viewModel.simplyEngravingName(accessory.engraving2),
                    customBGColor = LightGrayBG,
                    borderless = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextChip(
                    text = accessory.engraving3,
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
    accessory: CharacterAccessory,
    viewModel: EquipmentDetailVM,
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

}
