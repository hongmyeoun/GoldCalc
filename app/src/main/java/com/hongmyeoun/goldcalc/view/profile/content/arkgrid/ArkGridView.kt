package com.hongmyeoun.goldcalc.view.profile.content.arkgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.profile.arkGrid.ArkGrid
import com.hongmyeoun.goldcalc.model.profile.arkGrid.ArkGridCoreAndGemsTooltips
import com.hongmyeoun.goldcalc.model.profile.arkGrid.ArkGridEffects
import com.hongmyeoun.goldcalc.model.profile.arkGrid.ArkGridSlot
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEnlightenment
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.RareTextColor
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.Title
import com.hongmyeoun.goldcalc.viewModel.profile.ArkGridVM
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun ArkGrid(
    viewModel: ProfileVM
) {
    val arkGrid by viewModel.arkGrid.collectAsState()
    val arkGridDetail by viewModel.arkGridDetail.collectAsState()

    arkGrid?.let {
        arkGridDetail?.let { details ->
            ArkGridView(arkGrid = it, arkGridDetail = details)
        }
    }
}

@Composable
fun ArkGridView(
    arkGrid: ArkGrid,
    arkGridDetail: List<ArkGridCoreAndGemsTooltips>,
    viewModel: ArkGridVM = viewModel()
) {
    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .noRippleClickable { viewModel.onDetailClicked() }
    ) {
        Title(
            title = "아크그리드",
            onClick = { viewModel.onDetailClicked() }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            arkGrid.slots?.forEach {
                CoreSimple(
                    core = it,
                    viewModel = viewModel
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            arkGrid.effects?.forEach {
                GemEffects(gemEffect = it)
            }
        }
    }
}

@Composable
private fun GemEffects(gemEffect: ArkGridEffects) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(34.dp))
            Text(
                text = gemEffect.name,
                color = Color.White
            )
        }
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Lv.${gemEffect.level}",
                color = RareTextColor
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoreSimple(
    core: ArkGridSlot,
    viewModel: ArkGridVM
) {
    val coreNameColor = viewModel.getGradeBG(core.grade)

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            GlideImage(
                model = core.icon,
                contentDescription = "코어 이미지"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = core.name,
                color = coreNameColor
            )
        }
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${core.point}P",
                color = ArkPassiveEnlightenment
            )
        }
    }
}