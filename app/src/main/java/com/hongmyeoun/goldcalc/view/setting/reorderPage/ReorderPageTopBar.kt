package com.hongmyeoun.goldcalc.view.setting.reorderPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.constants.Setting
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM

@Composable
fun ReorderPageTopBar(
    viewModel: SettingVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.weight(0.5f),
            onClick = { viewModel.closeReorderPage() }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "뒤로",
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            text = Setting.CHARACTER_REORDER,
            style = titleTextStyle(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .width(32.dp)
                .weight(0.5f)
        )
    }
}
