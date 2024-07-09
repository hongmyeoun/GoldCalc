package com.hongmyeoun.goldcalc.view.home.topbar.progressText

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.ImageReturn
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.constants.Home
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.home.HomeVM

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProgressText(viewModel: HomeVM) {
    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
        SimpleCurrent(viewModel)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Home.PROGRESS_HW,
                style = normalTextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { viewModel.onClicked() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.Info,
                    tint = Color.White,
                    contentDescription = "전체 진행사항 한눈에 보기"
                )
            }
        }

        Text(
            text = Home.WEEKLY_GOLD,
            style = normalTextStyle(),
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.width(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = viewModel.maxGold.formatWithCommas(),
                style = normalTextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.width(4.dp))

            GlideImage(
                modifier = Modifier.size(25.dp),
                model = ImageReturn.goldImage(viewModel.maxGold),
                contentDescription = "골드"
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
