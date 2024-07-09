package com.hongmyeoun.goldcalc.view.setting.settingPage.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.constants.Setting
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM

@Composable
fun CacheClear(
    icon: Int,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingVM
) {
    val context = LocalContext.current

    val cacheSize by viewModel.cacheSize.collectAsState()

    LaunchedEffect(cacheSize) {
        viewModel.getCacheSize(context)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = cacheSize > 0) {
                viewModel.onDeleteAllHistories()
                viewModel.cacheDelete(context, snackbarHostState)
            }
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = Color.White,
                contentDescription = "아이콘"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = Setting.CACHE_DELETE,
                style = normalTextStyle(fontSize = 15.sp)
            )
        }

        Text(
            text = "${(cacheSize / 1024).formatWithCommas()} KB",
            style = normalTextStyle(color = Color.Gray, fontSize = 13.sp),
            textAlign = TextAlign.End
        )
    }
}
