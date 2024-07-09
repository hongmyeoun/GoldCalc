package com.hongmyeoun.goldcalc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongmyeoun.goldcalc.model.constants.viewConst.NavigationKey
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.view.home.HomeView
import com.hongmyeoun.goldcalc.view.homework.HomeworkView
import com.hongmyeoun.goldcalc.view.profile.ProfileView
import com.hongmyeoun.goldcalc.view.search.SearchView
import com.hongmyeoun.goldcalc.view.setting.SettingView

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
            val charName = it.arguments?.getString(NavigationKey.KEY) ?: NavigationKey.KEY_ERROR
            HomeworkView(
                navController = navController,
                characterRepository = characterRepository,
                charName = charName
            )
        }
        composable(Screen.Search.route) {
            SearchView(navController)
        }
        composable(Screen.Profile.route) {
            val charName = it.arguments?.getString(NavigationKey.KEY)!!

            ProfileView(charName, navController)
        }
        composable(Screen.Setting.route) {
            SettingView(navController)
        }
    }
}