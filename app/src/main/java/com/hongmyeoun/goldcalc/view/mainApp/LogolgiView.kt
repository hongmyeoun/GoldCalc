package com.hongmyeoun.goldcalc.view.mainApp

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.navigation.NavControl
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoGolGi(characterRepository: CharacterRepository) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    GoldCalcTheme {
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(1500L)
            isLoading = false
        }

        Box(modifier = Modifier.safeDrawingPadding()) {
            Crossfade(
                targetState = isLoading,
                label = "시작 로딩"
            ) { loading ->
                if (loading) {
                    LoadingScreen()
                } else {
                    NavControl(characterRepository = characterRepository)
                }
            }
        }
    }
}
