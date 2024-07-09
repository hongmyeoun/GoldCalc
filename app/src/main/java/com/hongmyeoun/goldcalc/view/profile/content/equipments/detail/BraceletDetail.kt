package com.hongmyeoun.goldcalc.view.profile.content.equipments.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.EquipmentConsts
import com.hongmyeoun.goldcalc.model.constants.Profile
import com.hongmyeoun.goldcalc.model.profile.equipment.Bracelet
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EquipmentVM

@Composable
fun BraceletDetail(
    viewModel: EquipmentVM,
    characterEquipment: List<CharacterItem>
) {
    Dialog(
        onDismissRequest = { viewModel.onBraDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(ImageBG, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = EquipmentConsts.BRACELET,
                style = titleTextStyle()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            characterEquipment.forEach {
                when (it) {
                    is Bracelet -> {
                        IconAndName(viewModel, it)

                        it.extraStats.forEach { (statName, statValue) ->
                            Stats(statName, statValue)
                        }

                        it.specialEffect.forEach { (effectName, tooltip) ->
                            Effects(effectName, tooltip)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun IconAndName(
    viewModel: EquipmentVM,
    it: Bracelet
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    brush = viewModel.getItemBG(it.grade),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        ) {
            GlideImage(
                modifier = Modifier.size(56.dp),
                model = it.itemIcon,
                contentDescription = "팔찌 이미지"
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = it.name,
            style = titleTextStyle()
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun Stats(statName: String, statValue: String) {
    Row {
        TextChip(
            text = Profile.STAT,
            borderless = true,
            customBGColor = LightGrayBG
        )
        Spacer(modifier = Modifier.width(8.dp))

        TextChip(
            text = "$statName $statValue",
            borderless = true,
            customBGColor = LightGrayBG
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun Effects(effectName: String, tooltip: String) {
    Column {
        Row {
            TextChip(
                text = Profile.EFFECT,
                borderless = true,
                customBGColor = LightGrayBG
            )
            Spacer(modifier = Modifier.width(8.dp))

            TextChip(
                text = effectName,
                borderless = true,
                customBGColor = LightGrayBG
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(LightGrayBG, RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tooltip,
                style = normalTextStyle()
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
