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
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun Engraving(
    viewModel: ProfileVM,
    profile: CharacterDetail
) {
    // 각인
    val engravings by viewModel.engravings.collectAsState()

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
                skillEngravings = skillEngravings
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}