package com.hongmyeoun.goldcalc.view.profile.content.equipments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.constants.Profile
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.content.equipments.accessory.Accessory
import com.hongmyeoun.goldcalc.view.profile.content.equipments.detail.AccessoryDetail
import com.hongmyeoun.goldcalc.view.profile.content.equipments.detail.BraceletDetail
import com.hongmyeoun.goldcalc.view.profile.content.equipments.equipment.Equipment
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun Equipments(
    viewModel: ProfileVM
) {
    // 장비
    val equipment by viewModel.equipments.collectAsState()

    equipment?.let { equipmentList ->
        val equipmentVM = EquipmentVM(equipmentList)
        EquipmentView(equipmentList, equipmentVM)
    }
}

@Composable
fun EquipmentView(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM
) {
    val showAccDialog by viewModel.showAccDialog.collectAsState()
    val showBraDialog by viewModel.showBraDialog.collectAsState()

    if (showAccDialog) {
        AccessoryDetail(viewModel, characterEquipment)
    } else if (showBraDialog) {
        BraceletDetail(viewModel, characterEquipment)
    }

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        EquipmentAndAccessory(characterEquipment, viewModel)
        Extra(characterEquipment, viewModel)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun EquipmentAndAccessory(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM
) {
    Text(
        text = Profile.EQUIPMENT,
        style = titleTextStyle(),
    )
    Spacer(modifier = Modifier.height(4.dp))

    // 세트, 악세 품질 요약
    Summary(viewModel = viewModel)

    Row {
        Equipment(
            modifier = Modifier.weight(0.7f),
            characterEquipment = characterEquipment,
            viewModel = viewModel
        )

        Accessory(
            modifier = Modifier.weight(0.4f),
            characterEquipment = characterEquipment,
            viewModel = viewModel
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

// 세트, 악세 품질 요약
@Composable
fun Summary(
    viewModel: EquipmentVM
) {
    val accessoryQualityAvg by viewModel.accessoryAvgQuality.collectAsState()
    val setOption by viewModel.setOption.collectAsState()
    val totalCombatStat by viewModel.totalCombatStat.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (setOption.isNotEmpty()) {
            TextChip(text = setOption)
        }
        Spacer(modifier = Modifier.width(8.dp))

        TextChip(text = "${Profile.ACC_QUAL} $accessoryQualityAvg")
        Spacer(modifier = Modifier.width(8.dp))

        TextChip(text = "${Profile.TOTAL_STAT} $totalCombatStat")
    }
    Spacer(modifier = Modifier.height(12.dp))
}
