package com.hongmyeoun.goldcalc.view.homework.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM

@Composable
fun RaidHeader(viewModel: HomeworkVM) {
    AnimatedBottomBar(
        modifier = Modifier.fillMaxWidth(),
        bottomBarHeight = 50.dp,
        containerColor = LightGrayBG,
        selectedItem = viewModel.selectedTab,
        itemSize = viewModel.headerTitle.size,
        indicatorColor = Color.LightGray,
        indicatorStyle = IndicatorStyle.LINE,
        indicatorDirection = IndicatorDirection.BOTTOM
    ) {
        viewModel.headerTitle.forEachIndexed { index, title ->
            TopBarBox(
                title = title,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.moveHeader(index) }
            )
        }
    }
}

@Composable
private fun TopBarBox(
    title: String,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
