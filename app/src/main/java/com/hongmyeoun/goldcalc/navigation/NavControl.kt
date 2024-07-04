package com.hongmyeoun.goldcalc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.view.characterDetail.CharacterDetailUI
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.view.goldCheck.setting.GoldSetting
import com.hongmyeoun.goldcalc.view.home.HomeView
import com.hongmyeoun.goldcalc.view.search.SearchUI
import com.hongmyeoun.goldcalc.view.setting.SettingUI
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavControl(characterRepository: CharacterRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeView(
                navController = navController,
                characterRepository = characterRepository
            )
        }
        composable(Screen.Homework.route) {
            val charName = it.arguments?.getString("charName") ?: "ERROR"
            val gSVM = remember { GoldSettingVM(characterRepository, charName) }
            val character by gSVM.character.collectAsState()

            var isLoading by remember { mutableStateOf(true) }

            Crossfade(
                targetState = isLoading,
                label = "로딩 crossfade"
            ) { loading ->
                if (loading) {
                    LoadingScreen()
                } else {
                    val cbVM = remember { CommandBossVM(character) }
                    val adVM = remember { AbyssDungeonVM(character) }
                    val kzVM = remember { KazerothRaidVM(character) }
                    val epVM = remember { EpicRaidVM(character) }
                    GoldSetting(navController, gSVM, cbVM, adVM, kzVM, epVM)
                }
            }

            LaunchedEffect(Unit) {
                delay(1000)
                isLoading = false
            }
        }
        composable(Screen.Search.route) {
            SearchUI(navController)
        }
        composable(Screen.Profile.route) {
            val charName = it.arguments?.getString("charName")

            var isLoading by remember { mutableStateOf(true) }


            if (isLoading) {
                LoadingScreen()
            }

            charName?.let {
                CharacterDetailUI(charName, navController)
            }

            LaunchedEffect(Unit) {
                delay(1000)
                isLoading = false
            }
        }
        composable(Screen.Setting.route) {
            SettingUI(navController)
        }
    }

}