package com.hongmyeoun.goldcalc.view.profile.content.equipments.accessory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.content.equipments.UpgradeQualityRow
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.charDetail.EquipmentDetailVM

@Composable
fun Accessory(
    modifier: Modifier,
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentDetailVM
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
                        viewModel = viewModel
                    )
                }

                is AbilityStone -> {
                    AbilityStoneUI(
                        abilityStone = it,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun AccessoryUI(
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
fun AbilityStoneUI(
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
