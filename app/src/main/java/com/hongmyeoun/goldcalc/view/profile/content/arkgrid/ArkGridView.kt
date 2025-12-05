package com.hongmyeoun.goldcalc.view.profile.content.arkgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.profile.arkGrid.ArkGrid
import com.hongmyeoun.goldcalc.model.profile.arkGrid.ArkGridCoreAndGemsTooltips
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEnlightenment
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
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

        arkGrid.slots.forEach {
            CoreSimple(
                icon = it.icon,
                name = it.name,
                grade = it.grade,
                point = it.point,
                viewModel = viewModel
            )
        }

        arkGrid.effects.forEach {
            Row {
                Text(
                    text = it.name
                )
                Text(
                    text = "${it.level}"
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoreSimple(
  icon: String,
  name: String,
  grade: String,
  point: Int,
  viewModel: ArkGridVM
) {
    val coreNameColor = viewModel.getGradeBG(grade)

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            model = icon,
            contentDescription = ""
        )
        Text(
            text = name,
            color = coreNameColor
        )
        Text(
            text = "$point",
            color = ArkPassiveEnlightenment
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArkGridViewPreview() {
    Column {
        Text(text = "test")
    }
}