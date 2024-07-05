package com.hongmyeoun.goldcalc.view.common.profileTemplate

import SearchProfile
import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

@Composable
fun ProfileTemplate(
    profile: CharacterDetail? = null,
    character: Character? = null,
    onReloadClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onGetClick: () -> Unit = {},
    getButtonEnabled: Boolean = false
) {
    character?.let {
        HomeworkProfile(it, onAvatarClick, onReloadClick)
    }
    profile?.let {
        SearchProfile(it, onGetClick, getButtonEnabled)
    }
}

