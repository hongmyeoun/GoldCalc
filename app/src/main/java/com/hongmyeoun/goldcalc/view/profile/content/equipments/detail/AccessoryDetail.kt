package com.hongmyeoun.goldcalc.view.profile.content.equipments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.constants.viewConst.Profile
import com.hongmyeoun.goldcalc.model.profile.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.content.equipments.accessory.EquipmentIcon
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleBoldWhite12
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM

@Composable
fun AccessoryDetail(
    viewModel: EquipmentVM,
    characterEquipment: List<CharacterItem>
) {
    Dialog(
        onDismissRequest = { viewModel.onAccDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(ImageBG, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = Profile.ACC,
                style = titleTextStyle()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            characterEquipment.forEach {
                when (it) {
                    is CharacterAccessory -> {
                        DetailUI(
                            accessory = it,
                            viewModel = viewModel
                        )
                    }

                    is AbilityStone -> {
                        DetailUI(
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
private fun DetailUI(
    accessory: CharacterAccessory,
    viewModel: EquipmentVM
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = accessory.name,
                    style = titleBoldWhite12()
                )
                Spacer(modifier = Modifier.width(8.dp))

                if (accessory.arkPassivePoint != null) {
                    TextChip(
                        text = accessory.arkPassivePoint,
                        customBGColor = LightGrayBG,
                        borderless = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            if (accessory.combatStat1 != null) {
                Row {
                    Text(
                        text = accessory.combatStat1,
                        style = normalTextStyle(fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    if (accessory.combatStat2 != null && accessory.type == EquipmentConsts.NECKLACE) {
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
            } else if (accessory.grindEffect != null) {
                Box(
                    modifier = Modifier
                        .background(LightGrayBG, RoundedCornerShape(4.dp))
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = accessory.grindEffect,
                        style = normalTextStyle()
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun DetailUI(
    abilityStone: AbilityStone,
    viewModel: EquipmentVM
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
