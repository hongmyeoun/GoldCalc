package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.viewModel.charDetail.CombatStatDetailVM

@Composable
fun CombatStatDetailUI(
    modifier: Modifier,
    charDetail: CharacterDetail,
    viewModel: CombatStatDetailVM = viewModel()
) {
    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
//        EngravingDescription(skillEngravings, viewModel)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CombatStatSimple(
            charDetail = charDetail,
            default = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        CombatStatSimple(
            charDetail = charDetail,
            default = false
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CombatStatSimple(
    charDetail: CharacterDetail,
    default: Boolean
) {
    val title = if (default) "기본 특성" else "전투 특성"
    val typeList = if (default) listOf("공격력", "최대 생명력") else listOf("치명", "특화", "제압", "신속", "인내", "숙련")
    val maxItem = if (default) 1 else 2

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlackTransBG)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = titleTextStyle(fontSize = 13.sp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        FlowRow(
            maxItemsInEachRow = maxItem
        ) {
            charDetail.stats.forEach { stat ->
                if (stat.type in typeList) {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            text = stat.type,
                            style = normalTextStyle(color = OrangeQual, fontSize = 12.sp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            text = stat.value,
                            style = normalTextStyle(fontSize = 12.sp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        Icon(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).size(16.dp),
            imageVector = Icons.Default.KeyboardArrowDown,
            tint = Color.White,
            contentDescription = "더보기"
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}
