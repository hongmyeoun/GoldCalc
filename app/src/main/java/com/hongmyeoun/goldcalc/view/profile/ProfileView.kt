package com.hongmyeoun.goldcalc.view.profile

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.profile.content.ProfileContent
import com.hongmyeoun.goldcalc.view.profile.topbar.ProfileTopBar

@Composable
fun ProfileView(
    charName: String,
    navController: NavHostController
) {


    Scaffold(
        topBar = { ProfileTopBar(navController) },
        containerColor = ImageBG,
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        ProfileContent(
            charName = charName,
            paddingValues = paddingValues
        )
    }
}