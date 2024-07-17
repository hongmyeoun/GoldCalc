package com.hongmyeoun.goldcalc.view.profile

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.common.LoadingScreen
import com.hongmyeoun.goldcalc.view.profile.content.ProfileContent
import com.hongmyeoun.goldcalc.view.profile.topbar.ProfileTopBar
import kotlinx.coroutines.delay

@Composable
fun ProfileView(
    charName: String,
    navController: NavHostController
) {
    var isLoading by remember { mutableStateOf(true) }

    if (isLoading) {
        LoadingScreen()
    }

    LaunchedEffect(Unit) {
        delay(250L)
        isLoading = false
    }

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