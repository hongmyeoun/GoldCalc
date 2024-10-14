package com.hongmyeoun.goldcalc

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.MobileAds
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.view.mainApp.LoGolGi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var characterRepository: CharacterRepository

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)

        enableEdgeToEdge()

        setContent {
            LoGolGi(characterRepository)
        }
    }
}
