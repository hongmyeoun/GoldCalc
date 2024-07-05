package com.hongmyeoun.goldcalc.view.profile.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ProfileTemplate
import com.hongmyeoun.goldcalc.view.profile.content.card.Card
import com.hongmyeoun.goldcalc.view.profile.content.engraving.Engraving
import com.hongmyeoun.goldcalc.view.profile.content.equipments.Equipments
import com.hongmyeoun.goldcalc.view.profile.content.gem.Gem
import com.hongmyeoun.goldcalc.view.profile.content.skill.Skill
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun ProfileContent(
    charName: String,
    paddingValues: PaddingValues,
    viewModel: ProfileVM = hiltViewModel()
) {
    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getCharDetails(context, charName)
        viewModel.isSavedName(charName)
    }

    // 캐릭터 정보
    val charProfile by viewModel.charProfile.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    charProfile?.let { profile ->
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
                onGetClick = { viewModel.saveCharDetailToLocal(profile) },
                getButtonEnabled = !isSaved
            )

            ProfileDetails(
                viewModel = viewModel,
                profile = profile
            )
        }
    }
}

@Composable
fun ProfileDetails(
    viewModel: ProfileVM,
    profile: CharacterDetail
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Engraving(
            viewModel = viewModel,
            profile = profile
        )
        Equipments(viewModel = viewModel)
        Gem(viewModel = viewModel)
        Card(viewModel = viewModel)
        Skill(
            viewModel = viewModel,
            profile = profile
        )
    }
}