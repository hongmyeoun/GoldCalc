package com.hongmyeoun.goldcalc.view.mainApp

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.navigation.NavControl
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoGolGi(characterRepository: CharacterRepository) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    GoldCalcTheme {
        Box(modifier = Modifier.safeDrawingPadding()) {
            NavControl(characterRepository = characterRepository)
        }
    }
}