package com.hongmyeoun.goldcalc.view.setting.settingPage

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.hongmyeoun.goldcalc.BuildConfig
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.view.setting.common.Dialog
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM

@Composable
fun SettingContent(
    it: PaddingValues,
    viewModel: SettingVM,
    snackbarHostState: SnackbarHostState,
) {
    val showResetDialog by viewModel.showResetDialog.collectAsState()
    val showDeleteCharListDialog by viewModel.showDeleteCharListDialog.collectAsState()
    val showDeleteHistoriesDialog by viewModel.showDeleteHistoryDialog.collectAsState()

    val context = LocalContext.current

    if (showResetDialog) {
        Dialog(
            title = "숙제",
            action = "초기화",
            viewModel = viewModel,
            onConfirm = { viewModel.onHomeworkReset() }
        )
    }

    if (showDeleteCharListDialog) {
        Dialog(
            title = "캐릭터",
            action = "일괄 삭제",
            viewModel = viewModel,
            onConfirm = { viewModel.onDeleteAllCharList() }
        )
    }

    if (showDeleteHistoriesDialog) {
        Dialog(
            title = "검색기록",
            action = "일괄 삭제",
            viewModel = viewModel,
            onConfirm = { viewModel.onDeleteAllHistories() }
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            SettingItemBox(
                title = "캐릭터"
            ) {
                SettingItem(
                    itemTitle = "숙제 초기화",
                    icon = R.drawable.baseline_settings_backup_restore_24,
                    onClicked = { viewModel.showResetDialog() }
                )

                SettingItem(
                    itemTitle = "캐릭터 순서 변경",
                    icon = R.drawable.baseline_sort,
                    onClicked = { viewModel.openReorderPage() }
                )
            }
        }

        item {
            SettingItemBox(
                title = "삭제"
            ) {
                SettingItem(
                    itemTitle = "캐릭터",
                    icon = R.drawable.baseline_delete_sweep,
                    onClicked = { viewModel.showDeleteCharListDialog() }
                )

                SettingItem(
                    itemTitle = "검색기록",
                    icon = R.drawable.baseline_manage_history,
                    onClicked = { viewModel.showDeleteHistoryDialog() }
                )
            }
        }

        item {
            SettingItemBox(
                title = "앱 설정"
            ) {
                SettingItemCacheClear(
                    icon = R.drawable.outline_cleaning_services,
                    snackbarHostState = snackbarHostState,
                    viewModel = viewModel
                )

                SettingItem(
                    itemTitle = "업데이트 확인",
                    icon = R.drawable.outline_restore,
                    onClicked = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
                        context.startActivity(intent)
                    }
                )
            }
        }

        item {
            SettingItemBox(
                title = "앱 버전"
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                    text = BuildConfig.VERSION_NAME,
                    style = normalTextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}

@Composable
private fun SettingItemBox(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        text = title,
        style = titleTextStyle(fontSize = 15.sp)
    )
    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content()
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SettingItem(
    itemTitle: String,
    icon: Int,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = Color.White,
            contentDescription = "아이콘"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = itemTitle,
            style = normalTextStyle(fontSize = 15.sp)
        )
    }
}

@Composable
private fun SettingItemCacheClear(
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
                text = "캐시 삭제",
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
