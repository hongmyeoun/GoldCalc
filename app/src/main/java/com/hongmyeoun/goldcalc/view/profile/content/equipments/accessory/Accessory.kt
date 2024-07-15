package com.hongmyeoun.goldcalc.view.profile.content.equipments.accessory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.profile.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.content.equipments.UpgradeQualityRow
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM

@Composable
fun Accessory(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    Column(
        modifier = modifier
            .noRippleClickable { viewModel.onAccClicked() }
    ) {
        characterEquipment.forEach {
            when (it) {
                is CharacterAccessory -> {
                    AccessoryUI(
                        accessory = it,
                        viewModel = viewModel,
                        isArkPassive = isArkPassive
                    )
                }

                is AbilityStone -> {
                    AbilityStoneUI(
                        abilityStone = it,
                        viewModel = viewModel,
                        isArkPassive = isArkPassive
                    )
                }
            }
        }
    }
}

@Composable
fun AccessoryUI(
    accessory: CharacterAccessory,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    val offsetValue = if (isArkPassive) (-6).dp else 0.dp
    Row {
        EquipmentIcon(
            accessory = accessory,
            viewModel = viewModel,
            isArkPassive = isArkPassive,
            modifier = Modifier.offset(x = (-8).dp, y = (-8).dp)
        )
        Spacer(modifier = Modifier.width(4.dp))


        Column(
            modifier = Modifier
                .offset(x = offsetValue)
        ) {
            UpgradeQualityRow(
                viewModel = viewModel,
                grade = accessory.grade
            )
            if (!accessory.combatStat1.isNullOrEmpty()) {
                val textColor = if (isArkPassive) Color(0xFF787878) else Color.White
                Column {
                    Text(
                        text = accessory.combatStat1,
                        style = normalTextStyle(textColor)
                    )
                    if (!accessory.combatStat2.isNullOrEmpty() && accessory.type == EquipmentConsts.NECKLACE) {
                        Text(
                            text = accessory.combatStat2,
                            style = normalTextStyle(textColor)
                        )
                    }
                }
            } else if (!accessory.grindEffect.isNullOrEmpty()) {
                Column {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = viewModel.grindEffectSize(accessory.grindEffect),
                        style = normalTextStyle()
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun AbilityStoneUI(
    abilityStone: AbilityStone,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
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
            EngravingStr(
                viewModel = viewModel,
                engravingName = abilityStone.engraving1Op,
                engravingLv = abilityStone.engraving1Lv,
                isArkPassive = isArkPassive
            )

            EngravingStr(
                viewModel = viewModel,
                engravingName = abilityStone.engraving2Op,
                engravingLv = abilityStone.engraving2Lv,
                isArkPassive = isArkPassive
            )

            EngravingStr(
                viewModel = viewModel,
                engravingName = abilityStone.engraving3Op,
                engravingLv = abilityStone.engraving3Lv,
                isArkPassive = isArkPassive,
                isLast = true
            )
        }

    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun EngravingStr(
    viewModel: EquipmentVM,
    engravingName: String,
    engravingLv: Int?,
    isArkPassive: Boolean,
    isLast: Boolean = false,
    lastSpacer: Boolean = false
) {
    val textColor = if (isLast) RedQual else Color.White
    val img = if (isLast) R.drawable.img_penalty_ability_stone else R.drawable.img_awaken_ability_stone

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = viewModel.getSimpleEngraving(engravingName),
            style = normalTextStyle(color = textColor)
        )
        Spacer(modifier = Modifier.width(4.dp))

        if (viewModel.arkPassiveEngLv(engravingLv) != null && isArkPassive) {
            GlideImage(
                modifier = Modifier.size(15.dp),
                model = img,
                contentDescription = "돌 각성 포인트"
            )
            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = if (isLast) viewModel.arkPassiveEngPenLv(engravingLv)!! else viewModel.arkPassiveEngLv(engravingLv)!!,
                style = normalTextStyle(color = textColor)
            )
        }
    }

    if (!isLast || lastSpacer) {
        Spacer(modifier = Modifier.height(2.dp))
    }
}
