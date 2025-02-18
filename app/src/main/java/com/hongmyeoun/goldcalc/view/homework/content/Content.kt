package com.hongmyeoun.goldcalc.view.homework.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ProfileTemplate
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.CheckLists
import com.hongmyeoun.goldcalc.viewModel.homework.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.homework.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.homework.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM
import com.hongmyeoun.goldcalc.viewModel.homework.KazerothRaidVM

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeworkContent(
    paddingValues: PaddingValues,
    viewModel: HomeworkVM,
    scrollState: LazyListState,
    snackbarHostState: SnackbarHostState,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
) {
    val character by viewModel.character.collectAsState()
    val showDetail by viewModel.showDetail.collectAsState()
    val imgUrl by viewModel.detailImageUrl.collectAsState()

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
                    onReloadClick = { viewModel.onReloadClick(character?.name, snackbarHostState) },
                    onAvatarClick = { viewModel.onAvatarClick(character) },
                    imgUrl = imgUrl
                )
            }
        }
        stickyHeader { RaidHeader(viewModel) }
        item { CheckLists(viewModel, cbVM, adVM, kzVM, epVM) }
    }
}