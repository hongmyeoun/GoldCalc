package com.hongmyeoun.goldcalc.view.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.BuildConfig
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightBlue
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM

@Composable
fun SettingUI(
    navController: NavHostController,
    viewModel: SettingVM = hiltViewModel()
) {
    Scaffold(
        topBar = { SettingTopBar(navController) },
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = ImageBG
    ) {
        SettingContent(it, viewModel)
    }
}

@Composable
private fun SettingContent(it: PaddingValues, viewModel: SettingVM) {
    val showResetDialog by viewModel.showResetDialog.collectAsState()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()

    if (showResetDialog) {
        Dialog(
            title = "숙제",
            action = "초기화",
            viewModel = viewModel,
            onConfirm = { viewModel.onHomeworkReset() }
        )
    }

    if (showDeleteDialog) {
        Dialog(
            title = "캐릭터",
            action = "일괄 삭제",
            viewModel = viewModel,
            onConfirm = { viewModel.onDeleteAll() }
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
                    onClicked = { viewModel.showResetDailog() }
                )

                SettingItem(
                    itemTitle = "캐릭터 순서 변경",
                    icon = R.drawable.baseline_sort,
                    onClicked = { }
                )

                SettingItem(
                    itemTitle = "캐릭터 일괄 삭제",
                    icon = R.drawable.baseline_delete_sweep,
                    onClicked = { viewModel.showDeleteDialog() }
                )
            }
        }

        item {
            SettingItemBox(
                title = "앱 설정"
            ) {
                SettingItem(
                    itemTitle = "캐쉬 삭제",
                    icon = R.drawable.outline_cleaning_services,
                    onClicked = { }
                )

                SettingItem(
                    itemTitle = "업데이트 확인",
                    icon = R.drawable.outline_restore,
                    onClicked = { }
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
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
        ,
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
private fun SettingTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.weight(0.5f),
            onClick = {
                navController.navigate("Main") {
                    popUpTo("Setting") {
                        inclusive = true
                    }
                }
            }
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
            text = "설정",
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

@Composable
private fun Dialog(
    title: String,
    action: String,
    viewModel: SettingVM,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = { viewModel.onDissmissRequest() }) {

        Column(
            modifier = Modifier.background(LightGrayBG, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = LightBlue,
                            fontSize = 20.sp
                        )
                    ) {
                        append(title)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append("를 ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    ) {
                        append(action)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append(" 하시겠습니까?")
                    }

                },
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDissmissRequest()
                    },
                ) {
                    Text(text = "취소")
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDissmissRequest()
                        onConfirm()
                    },
                ) {
                    Text(
                        text = action,
                        color = Color.Red
                    )
                }

            }
        }
    }
}
