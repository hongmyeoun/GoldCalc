package com.hongmyeoun.goldcalc.view.profile.content.engraving

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.lostArkApi.SearchedCharacterDetail
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun Engraving(
    viewModel: ProfileVM,
    profile: SearchedCharacterDetail,
    arkPassive: ArkPassive
) {
    // 각인
    val engravings by viewModel.engravings.collectAsState()
    val isArkPassive = arkPassive.isArkPassive

    Row {
        val modifier = Modifier.weight(1f)

        Stats(
            modifier = modifier,
            profile = profile
        )
        Spacer(modifier = Modifier.width(8.dp))

        engravings?.let { skillEngravings ->
            EngravingView(
                modifier = modifier,
                skillEngravings = skillEngravings,
                isArkPassive = isArkPassive
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}