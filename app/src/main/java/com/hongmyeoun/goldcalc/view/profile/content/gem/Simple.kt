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
    val (deal, coolTime) = viewModel.countDealGem(gemList)
    GemCount(
        viewModel = viewModel,
        gemList = gemList
    )

    Row {
        val (dealItemCount, coolItemCount) = viewModel.calcMaxItemsInEachRow(deal, coolTime)
        Column {
            FlowRow(
                maxItemsInEachRow = dealItemCount
            ) {
                gemList.filter { it.type in EquipmentConsts.DEAL_GEM_LIST }.forEach {
                    GemSimple(it, viewModel)
                }
            }
        }
        Column {
            FlowRow(
                maxItemsInEachRow = coolItemCount
            ) {
                gemList.filter { it.type in EquipmentConsts.COOLTIME_GEM_LIST }.forEach {
                    GemSimple(it, viewModel)
                }
            }
        }
    }
}

@Composable
private fun GemCount(
    viewModel: GemVM,
    gemList: List<Gem>
) {
    val totalAttack = viewModel.totalIncrease(gemList)

    val t4Deal = viewModel.t4Deal(gemList)
    val t4Cool = viewModel.t4Cool(gemList)
    val t3Deal = viewModel.t3Deal(gemList)
    val t3Cool = viewModel.t3Cool(gemList)
    val t2Deal = viewModel.t2Deal(gemList)
    val t2Cool = viewModel.t2Cool(gemList)

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (totalAttack.isNotEmpty()) {
            TextChip(text = "공격력 +$totalAttack")
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (t4Deal > 1) {
            TextChip(text = "${EquipmentConsts.DEAL_GEM_4_TIER} x$t4Deal")
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (t4Cool > 1) {
            TextChip(text = "${EquipmentConsts.COOLTIME_GEM_4_TIER} x$t4Cool")
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (t3Deal > 1) {
            TextChip(text = "${EquipmentConsts.DEAL_GEM_3_TIER} x$t3Deal")
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (t3Cool > 1) {
            TextChip(text = "${EquipmentConsts.COOLTIME_GEM_3_TIER} x$t3Cool")
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (t2Deal > 1) {
            TextChip(text = "${EquipmentConsts.DEAL_GEM_2_TIER} x$t2Deal")
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (t2Cool > 1) {
            TextChip(text = "${EquipmentConsts.COOLTIME_GEM_2_TIER} x$t2Cool")
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
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
