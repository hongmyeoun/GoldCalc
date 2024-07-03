package com.hongmyeoun.goldcalc

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.MokokoGreen
import com.hongmyeoun.goldcalc.view.characterDetail.CharacterDetailUI
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var characterRepository: CharacterRepository

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

            GoldCalcTheme {

                val navController = rememberNavController()

                Box(modifier = Modifier.safeDrawingPadding()) {
                    NavHost(
                        navController = navController,
                        startDestination = "Main",
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
                            }

                            val cbVM = remember { CommandBossVM(character) }
                            val adVM = remember { AbyssDungeonVM(character) }
                            val kzVM = remember { KazerothRaidVM(character) }
                            val epVM = remember { EpicRaidVM(character) }
                            GoldSetting(navController, gSVM, cbVM, adVM, kzVM, epVM)

                            LaunchedEffect(Unit) {
                                delay(1000)
                                isLoading = false
                            }

                        }
                        composable(Screen.Search.route) {
                            SearchUI(navController)
                        }
                        composable("CharDetail/{charName}") {
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
                        composable("Setting") {
                            SettingUI(navController)
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadingScreen() {
    val transition = rememberInfiniteTransition(label = "색 변경")
    val tintColor by transition.animateColor(
        initialValue = Color.White,
        targetValue = MokokoGreen,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "색이 바뀌는 애니메이션"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ImageBG.copy(alpha = 0.5f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = R.drawable.mokoko_white,
            colorFilter = ColorFilter.tint(tintColor),
            contentDescription = "로딩 이미지"
        )
    }

}

// 화면 세로 고정
@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.requestedOrientation = orientation
}

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .border(0.3f.dp, Color.Gray, RoundedCornerShape(8.dp)),
        progress = 1f,
        color = MokokoGreen,
        trackColor = Color.Transparent,
        strokeCap = StrokeCap.Round
    )
}
