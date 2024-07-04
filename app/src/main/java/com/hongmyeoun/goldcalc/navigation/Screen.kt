package com.hongmyeoun.goldcalc.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Main")

    object Homework: Screen("Homework/{charName}") {
        fun moveWithName(charName: String) = "Homework/$charName"
    }

    object Search : Screen("Search")

    object Profile : Screen("Profile/{charName}") {
        fun moveWithName(charName: String) = "Profile/$charName"
    }

    object Setting : Screen("Setting")
}