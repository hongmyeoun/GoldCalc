package com.hongmyeoun.goldcalc.view.goldCheck.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ProfileTemplate
import com.hongmyeoun.goldcalc.view.goldCheck.RaidCard
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.AbyssDungeon
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.CommandRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.EpicRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.KazerothRaid
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GoldSettingContent(
    paddingValues: PaddingValues,
    viewModel: GoldSettingVM,
    scrollState: LazyListState,
    snackbarHostState: SnackbarHostState,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
) {
    val character by viewModel.character.collectAsState()
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    val showDetail by viewModel.showDetail.collectAsState()

    if (isLoading) {
        LoadingScreen()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        state = scrollState
    ) {
        item {
            AnimatedVisibility(
                visible = showDetail,
                enter = expandVertically(animationSpec = tween(100, easing = LinearEasing)),
                exit = shrinkVertically(animationSpec = tween(100, easing = LinearEasing))
            ) {
                ProfileTemplate(
                    character = character,
                    onReloadClick = { viewModel.onReloadClick(context, character?.name, snackbarHostState) },
                    onAvatarClick = { viewModel.onAvatarClick(character) }
                )
            }
        }
        stickyHeader { RaidHeader(viewModel) }
        item { GoldSetting(viewModel, cbVM, adVM, kzVM, epVM) }
    }

}


@Composable
private fun RaidHeader(viewModel: GoldSettingVM) {
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
        if (title == "어비스 던전") {
            Text(
                text = title,
                style = normalTextStyle(fontSize = 12.sp)
            )
        } else {
            Text(
                text = title,
                color = Color.White
            )
        }
    }
}


@Composable
private fun GoldSetting(
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
) {
    Crossfade(targetState = viewModel.selectedTab) { selectedTab ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> {
                    RaidCard(
                        raidImg = R.drawable.command_icon,
                        totalGold = cbVM.totalGold
                    ) {
                        CommandRaid(viewModel = cbVM)
                    }
                }

                1 -> {
                    RaidCard(
                        raidImg = R.drawable.abyss_dungeon_icon,
                        totalGold = adVM.totalGold,
                    ) {
                        AbyssDungeon(viewModel = adVM)
                    }
                }

                2 -> {
                    RaidCard(
                        raidImg = R.drawable.kazeroth_icon,
                        totalGold = kzVM.totalGold
                    ) {
                        KazerothRaid(viewModel = kzVM)
                    }
                }

                3 -> {
                    RaidCard(
                        raidImg = R.drawable.epic_icon,
                        totalGold = epVM.totalGold
                    ) {
                        EpicRaid(viewModel = epVM)
                    }
                }

                4 -> {
                    ETCGold(
                        viewModel = viewModel,
                        onDone = { viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold) },
                    )
                }
            }
        }
    }
}

@Composable
fun ETCGold(
    viewModel: GoldSettingVM,
    onDone: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.plusGold,
            onValueChange = { viewModel.plusGoldValue(it) },
            label = { Text(text = "추가 골드") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    keyboardController?.hide()
                    focusState.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,

                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,

                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.minusGold,
            onValueChange = { viewModel.minusGoldValue(it) },
            label = { Text(text = "사용 골드") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    keyboardController?.hide()
                    focusState.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,

                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,

                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White
            )
        )
    }

}
