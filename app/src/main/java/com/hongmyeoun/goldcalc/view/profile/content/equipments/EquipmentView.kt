package com.hongmyeoun.goldcalc.view.profile.content.equipments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.hongmyeoun.goldcalc.model.constants.viewConst.Profile
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
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
    viewModel: ProfileVM,
    arkPassive: ArkPassive
) {
    // 장비
    val equipment by viewModel.equipments.collectAsState()
    val isArkPassive = arkPassive.isArkPassive

    equipment?.let { equipmentList ->
        val equipmentVM = EquipmentVM(equipmentList)
        EquipmentView(
            characterEquipment = equipmentList,
            viewModel = equipmentVM,
            isArkPassive = isArkPassive
        )
    }
}

@Composable
fun EquipmentView(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    val showAccDialog by viewModel.showAccDialog.collectAsState()
    val showBraDialog by viewModel.showBraDialog.collectAsState()

    if (showAccDialog) {
        AccessoryDetail(viewModel, characterEquipment, isArkPassive)
    } else if (showBraDialog) {
        BraceletDetail(viewModel, characterEquipment)
    }

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        EquipmentAndAccessory(
            characterEquipment = characterEquipment,
            viewModel = viewModel,
            isArkPassive = isArkPassive
        )
        Extra(characterEquipment, viewModel)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun EquipmentAndAccessory(
    characterEquipment: List<CharacterItem>,
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    Text(
        text = Profile.EQUIPMENT,
        style = titleTextStyle(),
    )
    Spacer(modifier = Modifier.height(4.dp))

    // 세트, 악세 품질 요약
    Summary(
        viewModel = viewModel,
        isArkPassive = isArkPassive
    )

    Row {
        Equipment(
            modifier = Modifier.weight(0.7f),
            characterEquipment = characterEquipment,
            viewModel = viewModel,
            isArkPassive = isArkPassive
        )

        Accessory(
            modifier = Modifier.weight(0.4f),
            characterEquipment = characterEquipment,
            viewModel = viewModel,
            isArkPassive = isArkPassive
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

// 세트, 악세 품질 요약
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Summary(
    viewModel: EquipmentVM,
    isArkPassive: Boolean
) {
    val accessoryQualityAvg by viewModel.accessoryAvgQuality.collectAsState()
    val setOption by viewModel.setOption.collectAsState()
    val totalCombatStat by viewModel.totalCombatStat.collectAsState()

    FlowRow(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        if (setOption.isNotEmpty() && !isArkPassive) {
            TextChip(text = setOption)
            Spacer(modifier = Modifier.width(8.dp))
        }

        TextChip(text = "${Profile.ACC_QUAL} $accessoryQualityAvg")
        Spacer(modifier = Modifier.width(8.dp))

        if (!isArkPassive) {
            TextChip(text = "${Profile.TOTAL_STAT} $totalCombatStat")
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
