package com.hongmyeoun.goldcalc.view.profile.content.skill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleBoldWhite12
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.viewModel.charDetail.SkillDetailVM

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Simple(skills: List<Skills>, gemList: List<Gem>?, viewModel: SkillDetailVM) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            skills.forEach {
                if (it.level > 3) {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(
                                color = LightGrayBG,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(4.dp)
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SkillIcon(it, true)
                        Spacer(modifier = Modifier.width(4.dp))

                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Tripods(it)
                            Spacer(modifier = Modifier.height(4.dp))

                            gemList?.let { gemList ->
                                RuneAndGem(it, gemList, viewModel)
                            }
                        }
                    }
                }
                if (it == skills.last()) {
                    if (skills.size % 2 == 1) {
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RuneAndGem(
    it: Skills,
    gemList: List<Gem>,
    viewModel: SkillDetailVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        it.rune?.let { rune ->
            TextChip(
                text = rune.name,
                customBGColor = viewModel.getGradeBG(rune.grade),
                borderless = true
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (it.gem) {
            val equipGemList = gemList.filter { gem -> gem.skill == it.name }
            equipGemList.forEach { gem ->
                val gemInfo = viewModel.gemInfo(gem)

                val padding = if (gemInfo.length >= 3 && equipGemList.size > 1) {
                    Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                } else {
                    Modifier.padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
                }
                TextChip(text = gemInfo, borderless = true, customPadding = padding)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
private fun Tripods(it: Skills) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = it.name,
            style = titleBoldWhite12()
        )
    }
}
