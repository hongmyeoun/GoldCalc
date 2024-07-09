package com.hongmyeoun.goldcalc.view.profile.content.gem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.viewModel.profile.GemVM

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Simple(
    gemList: List<Gem>,
    viewModel: GemVM,
) {
    val (annihilation, crimsonFlame) = viewModel.countAnnihilationGem(gemList)
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextChip(text = "${EquipmentConsts.DEAL_GEM_3_TIER} x$annihilation")
        Spacer(modifier = Modifier.width(8.dp))
        TextChip(text = "${EquipmentConsts.COOLTIME_GEM_3_TIER} x$crimsonFlame")
    }
    Spacer(modifier = Modifier.height(4.dp))

    Row {
        val (annMaxItemCount, criMaxItemCount) = viewModel.calcMaxItemsInEachRow(annihilation, crimsonFlame)
        Column {
            FlowRow(
                maxItemsInEachRow = annMaxItemCount
            ) {
                gemList.filter { it.type == EquipmentConsts.DEAL_GEM_3_TIER }.forEach {
                    GemSimple(it, viewModel)
                }
            }
        }
        Column {
            FlowRow(
                maxItemsInEachRow = criMaxItemCount
            ) {
                gemList.filter { it.type == EquipmentConsts.COOLTIME_GEM_3_TIER }.forEach {
                    GemSimple(it, viewModel)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GemSimple(it: Gem, viewModel: GemVM) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(4.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(44.dp)
                .background(
                    brush = viewModel.getItemBG(it.grade),
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
                .padding(4.dp),
            model = it.gemIcon,
            contentDescription = "보석",
        )
        Text(text = "${it.level}", fontSize = 10.sp, color = Color.White)
    }
}
