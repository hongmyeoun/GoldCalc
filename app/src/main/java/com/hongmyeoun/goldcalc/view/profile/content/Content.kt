package com.hongmyeoun.goldcalc.view.profile.content

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hongmyeoun.goldcalc.model.constants.ErrorMessage.RECONNECT
import com.hongmyeoun.goldcalc.model.constants.ErrorMessage.SEASON_3_NO_RECENT_CONNECT
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.model.lostArkApi.SearchedCharacterDetail
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ProfileTemplate
import com.hongmyeoun.goldcalc.view.profile.content.arkPassive.ArkPassiveNode
import com.hongmyeoun.goldcalc.view.profile.content.card.Card
import com.hongmyeoun.goldcalc.view.profile.content.engraving.Engraving
import com.hongmyeoun.goldcalc.view.profile.content.equipments.Equipments
import com.hongmyeoun.goldcalc.view.profile.content.gem.Gem
import com.hongmyeoun.goldcalc.view.profile.content.skill.Skill
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM
import kotlinx.coroutines.delay

@Composable
fun ProfileContent(
    charName: String,
    paddingValues: PaddingValues,
    viewModel: ProfileVM = hiltViewModel()
) {
    val verticalScrollState = rememberScrollState()

    var isLoading by remember { mutableStateOf(true) }
    var isDelay by remember { mutableStateOf(true) }

    LaunchedEffect(charName) {
        viewModel.getCharDetails(charName)
        viewModel.isSavedName(charName)
        delay(2000L)
        isDelay = false
    }

    // 캐릭터 정보
    val charProfile by viewModel.charProfile.collectAsState()
    val arkPassive by viewModel.arkPassive.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    LaunchedEffect(charProfile, arkPassive) {
        if (charProfile != null && arkPassive != null) {
            snapshotFlow { true }.collect {
                isLoading = false
            }
        }
    }

    charProfile?.let { profile ->
        Crossfade(targetState = isLoading, label = Labels.Crossfade.Loading) { loading ->
            if (loading) {
                LoadingScreen(isBackground = false)
            } else {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(ImageBG)
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        .verticalScroll(verticalScrollState)
                ) {
                    ProfileTemplate(
                        profile = profile,
                        arkPassive = arkPassive,
                        onGetClick = { viewModel.saveCharDetailToLocal(profile, arkPassive) },
                        getButtonEnabled = !isSaved
                    )

                    arkPassive?.let {
                        ProfileDetails(
                            viewModel = viewModel,
                            profile = profile,
                            arkPassive = it
                        )
                    }
                }
            }
        }
    } ?: run {
        if (!isDelay) {
            NoCharacter(paddingValues = paddingValues)
        } else {
            LoadingScreen(isBackground = false)
        }
    }
}

@Composable
fun ProfileDetails(
    viewModel: ProfileVM,
    profile: SearchedCharacterDetail,
    arkPassive: ArkPassive
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Engraving(
            viewModel = viewModel,
            profile = profile,
            arkPassive = arkPassive
        )
        Equipments(
            viewModel = viewModel,
            arkPassive = arkPassive
        )
        Gem(viewModel = viewModel)
        Card(viewModel = viewModel)
        Skill(
            viewModel = viewModel,
            profile = profile
        )
        ArkPassiveNode(viewModel = viewModel)
    }
}

@Composable
fun NoCharacter(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(ImageBG),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = SEASON_3_NO_RECENT_CONNECT,
            style = titleTextStyle(fontSize = 15.sp)
        )
        Text(
            text = RECONNECT,
            style = titleTextStyle(fontSize = 15.sp)
        )
    }
}