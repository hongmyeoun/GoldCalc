package com.hongmyeoun.goldcalc.view.home.content.contentItem

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.viewModel.home.HomeContentVM

@Composable
fun ContentItem(
    navController: NavHostController,
    cardViewModel: HomeContentVM,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            .background(
                color = BlackTransBG,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Crossfade(
            targetState = isLoading,
            label = Labels.Crossfade.Loading
        ) {
            Column {
                if (it) {
                    LoadingScreen(isBackground = false)
                } else {
                    ContentItemTop(
                        navController = navController,
                        viewModel = cardViewModel
                    )

                    HomeworkProgress(
                        viewModel = cardViewModel,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
