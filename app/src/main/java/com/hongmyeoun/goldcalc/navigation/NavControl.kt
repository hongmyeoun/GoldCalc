package com.hongmyeoun.goldcalc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.view.characterDetail.CharacterDetailUI
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.view.goldCheck.setting.GoldSetting
import com.hongmyeoun.goldcalc.view.main.MainScreen
import com.hongmyeoun.goldcalc.view.main.characterCard.CharacterCard
import com.hongmyeoun.goldcalc.view.search.SearchUI
import com.hongmyeoun.goldcalc.view.setting.SettingUI
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import com.hongmyeoun.goldcalc.viewModel.main.CharacterCardVM
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavControl(characterRepository: CharacterRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
    ) {
        composable(Screen.Main.route) {
            val characterListVM: CharacterListVM = hiltViewModel()

            MainScreen(
                characterListVM = characterListVM,
                navController = navController
            ) { modifier ->
                val characterList by characterListVM.characters.collectAsState()
                val isLoading by characterListVM.isLoading.collectAsState()

                if (isLoading) {
                    LoadingScreen()
                }

                LazyColumn(modifier = modifier) {
                    items(characterList, key = { item -> item.name }) {
                        val characterName = it.name
                        val characterCardVM = remember { CharacterCardVM(characterRepository, characterName) }

                        CharacterCard(
                            navController = navController,
                            viewModel = characterCardVM,
                        )
                    }
                }

            }
        }
        composable(Screen.Homework.route) {
            val charName = it.arguments?.getString("charName") ?: "ERROR"
            val gSVM = remember { GoldSettingVM(characterRepository, charName) }
            val character by gSVM.character.collectAsState()

            var isLoading by remember { mutableStateOf(true) }

            if (isLoading) {
                LoadingScreen()
            } else {
                val cbVM = remember { CommandBossVM(character) }
                val adVM = remember { AbyssDungeonVM(character) }
                val kzVM = remember { KazerothRaidVM(character) }
                val epVM = remember { EpicRaidVM(character) }
                GoldSetting(navController, gSVM, cbVM, adVM, kzVM, epVM)
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