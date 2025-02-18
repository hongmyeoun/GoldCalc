package com.hongmyeoun.goldcalc.view.common.profileTemplate

import SearchProfile
import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.model.lostArkApi.SearchedCharacterDetail
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

@Composable
fun ProfileTemplate(
    profile: SearchedCharacterDetail? = null,
    arkPassive: ArkPassive? = null,
    character: Character? = null,
    onReloadClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onGetClick: () -> Unit = {},
    getButtonEnabled: Boolean = false,
    imgUrl: String? = null
) {
    character?.let {
        HomeworkProfile(it, onAvatarClick, onReloadClick, imgUrl)
    }
    profile?.let { profiles ->
        arkPassive?.let { arkPassives ->
            SearchProfile(profiles, arkPassives, onGetClick, getButtonEnabled, imgUrl)
        }
    }
}

