package com.hongmyeoun.goldcalc.view.addScreen.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun AddScreenContent(
    paddingValues: PaddingValues,
    viewModel: AddScreenVM
) {
    Column(
        modifier = Modifier
            .background(ImageBG)
            .padding(paddingValues)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 닉네임
        NicknameField(viewModel)

        // 아이템 레벨
        ItemLevelField(viewModel)

        ServerClassSelect(viewModel)
    }
}
