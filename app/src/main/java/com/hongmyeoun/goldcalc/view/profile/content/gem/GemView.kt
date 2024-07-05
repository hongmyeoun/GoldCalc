package com.hongmyeoun.goldcalc.view.profile.content.gem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.profile.Title
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM
import com.hongmyeoun.goldcalc.viewModel.profile.GemVM

@Composable
fun Gem(
    viewModel: ProfileVM
) {
    // 보석
    val gems by viewModel.gems.collectAsState()

    gems?.let { gemList ->
        GemView(gemList)
    }
}

@Composable
fun GemView(
    gemList: List<Gem>,
    viewModel: GemVM = viewModel()
) {
    val isDetail by viewModel.isDetail.collectAsState()

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Title(
            title = "보석",
            onClick = { viewModel.onDetailClicked() }
        )

        if (isDetail) {
            Detail(gemList = gemList)
        } else {
            Simple(
                gemList = gemList,
                viewModel = viewModel
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
