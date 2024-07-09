package com.hongmyeoun.goldcalc.view.setting.settingPage.content

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongmyeoun.goldcalc.BuildConfig
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.ButtonText
import com.hongmyeoun.goldcalc.model.constants.Home
import com.hongmyeoun.goldcalc.model.constants.Profile
import com.hongmyeoun.goldcalc.model.constants.Setting
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
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
            title = Home.HW,
            action = ButtonText.INIT,
            viewModel = viewModel,
            onConfirm = { viewModel.onHomeworkReset() }
        )
    }

    if (showDeleteCharListDialog) {
        Dialog(
            title = Profile.CHARACTER,
            action = ButtonText.DELETE_ALL,
            viewModel = viewModel,
            onConfirm = { viewModel.onDeleteAllCharList() }
        )
    }

    if (showDeleteHistoriesDialog) {
        Dialog(
            title = Setting.SEARCH_HISTORY,
            action = ButtonText.DELETE_ALL,
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
            ItemBox(
                title = Profile.CHARACTER
            ) {
                Item(
                    itemTitle = "${Home.HW} ${ButtonText.INIT}",
                    icon = R.drawable.baseline_settings_backup_restore_24,
                    onClicked = { viewModel.showResetDialog() }
                )

                Item(
                    itemTitle = Setting.CHARACTER_REORDER,
                    icon = R.drawable.baseline_sort,
                    onClicked = { viewModel.openReorderPage() }
                )
            }
        }

        item {
            ItemBox(
                title = ButtonText.DELETE
            ) {
                Item(
                    itemTitle = Profile.CHARACTER,
                    icon = R.drawable.baseline_delete_sweep,
                    onClicked = { viewModel.showDeleteCharListDialog() }
                )

                Item(
                    itemTitle = Setting.SEARCH_HISTORY,
                    icon = R.drawable.baseline_manage_history,
                    onClicked = { viewModel.showDeleteHistoryDialog() }
                )
            }
        }

        item {
            ItemBox(
                title = Setting.APP_SETTING
            ) {
                CacheClear(
                    icon = R.drawable.outline_cleaning_services,
                    snackbarHostState = snackbarHostState,
                    viewModel = viewModel
                )

                Item(
                    itemTitle = Setting.UPDATE_CHECK,
                    icon = R.drawable.outline_restore,
                    onClicked = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
                        context.startActivity(intent)
                    }
                )
            }
        }

        item {
            ItemBox(
                title = Setting.VERSION
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