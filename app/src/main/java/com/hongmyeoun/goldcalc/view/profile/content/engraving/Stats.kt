package com.hongmyeoun.goldcalc.view.profile.content.engraving

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.viewModel.charDetail.CombatStatDetailVM

@Composable
fun Stats(
    modifier: Modifier,
    profile: CharacterDetail,
    viewModel: CombatStatDetailVM = viewModel()
) {
    val showDefaultDialog by viewModel.showDefaultDialog.collectAsState()
    val showCombatDialog by viewModel.showCombatDialog.collectAsState()

    if (showDefaultDialog) {
        Details(
            profile = profile,
            viewModel = viewModel,
            default = true
        )
    } else if (showCombatDialog) {
        Details(
            profile = profile,
            viewModel = viewModel,
            default = false
        )
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Simple(
            charDetail = profile,
            default = true,
            onClick = { viewModel.onDefaultClicked() }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Simple(
            charDetail = profile,
            default = false,
            onClick = { viewModel.onCombatClicked() }
        )
    }
}

// TODO: STRING
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Simple(
    charDetail: CharacterDetail,
    default: Boolean,
    onClick: () -> Unit
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
            modifier = Modifier.noRippleClickable { onClick() },
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
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .size(16.dp),
            imageVector = Icons.Default.KeyboardArrowDown,
            tint = Color.White,
            contentDescription = "더보기"
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

// TODO: STRING
@Composable
fun Details(
    profile: CharacterDetail,
    viewModel: CombatStatDetailVM,
    default: Boolean
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxColumnHeight = (screenHeight * 0.8f) // 화면 높이의 80%

    val title = if (default) "기본 특성" else "전투 특성"
    val typeList = if (default) listOf("공격력", "최대 생명력") else listOf("치명", "특화", "제압", "신속", "인내", "숙련")

    Dialog(
        onDismissRequest = { viewModel.onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .background(ImageBG, RoundedCornerShape(16.dp))
                .heightIn(max = maxColumnHeight)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = titleTextStyle()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            profile.stats.forEach { stat ->
                if (stat.type in typeList) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stat.type,
                            style = normalTextStyle(color = OrangeQual, fontSize = 15.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Column(
                        modifier = Modifier
                            .background(LightGrayBG, RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        stat.tooltip.forEach { description ->
                            if (!description.contains("않습니다.")) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Text(
                                        modifier = Modifier.padding(end = 4.dp),
                                        text = "·",
                                        style = normalTextStyle(fontSize = 12.sp)
                                    )

                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = viewModel.removeHTMLTags(description),
                                        style = normalTextStyle(fontSize = 10.sp),
                                        lineHeight = 14.sp,
                                    )
                                }
                            }
                            if (description != stat.tooltip.last()) {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}