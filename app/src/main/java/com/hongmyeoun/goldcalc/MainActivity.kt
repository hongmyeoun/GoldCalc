package com.hongmyeoun.goldcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.MokokoGreen
import com.hongmyeoun.goldcalc.view.goldCheck.Setting
import com.hongmyeoun.goldcalc.view.main.MainScreen
import com.hongmyeoun.goldcalc.view.main.characterCard.CharacterCard
import com.hongmyeoun.goldcalc.view.search.CharacterDetailScreen
import com.hongmyeoun.goldcalc.view.search.CharacterScreen
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoldCalcTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "Main") {
                    composable("Main") {
                        val characterListVM: CharacterListVM = hiltViewModel()

                        MainScreen(
                            characterListVM = characterListVM,
                            navController = navController
                        ) { modifier ->
                            val characterList by characterListVM.characters.collectAsState()
                            val isLoading by characterListVM.isLoading.collectAsState()

                            if (isLoading) {
                                LoadingScreen()
                            } else {
//                                Column(
//                                    modifier = modifier.verticalScroll(rememberScrollState()),
//                                ) {
//                                    characterList.forEach {
//                                        val characterName = it.name
//                                        val characterCardVM = remember { CharacterCardVM(characterRepository, characterName) }
//
//                                        val character by characterCardVM.character.collectAsState()
//
//                                        CharacterCard(
//                                            navController = navController,
//                                            viewModel = characterCardVM,
//                                            onDelete = {
//                                                characterListVM.delete(character)
//                                                isLoading = true
//                                            },
//                                            onClick = { characterCardVM.enableDelay() }
//                                        )
//                                        Divider(modifier = Modifier.height(8.dp))
//                                    }
//                                }

                                LazyColumn(modifier = modifier) {
                                    items(characterList, key = { item -> item.name }) {
                                        val characterName = it.name
                                        val characterCardVM = remember { CharacterCardVM(characterRepository, characterName) }

                                        val character by characterCardVM.character.collectAsState()

                                        CharacterCard(
                                            navController = navController,
                                            viewModel = characterCardVM,
                                            onDelete = {
                                                characterListVM.delete(character)
                                                characterListVM.onDeleteLoading()
                                            },
                                            onClick = { characterCardVM.enableDelay() }
                                        )
                                        Divider(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }

                        }
                    }
                    composable("Check/{charName}") {
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
                            Setting(navController, gSVM, cbVM, adVM, kzVM, epVM)
                        }

                        LaunchedEffect(Unit) {
                            delay(1000) // 예시로 1초의 로딩 시간을 줍니다. 실제 필요한 시간에 맞게 조정하세요.
                            isLoading = false // 데이터 로딩이 완료되면 로딩 상태를 false로 변경합니다.
                        }

                    }
                    composable("Search") {
                        CharacterScreen(navController)
                    }
                    composable("CharDetail/{charName}") {
                        val charName = it.arguments?.getString("charName") ?: "마일즈섬광"
                        CharacterDetailScreen(charName)
                    }
                }
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator() // 프로그레스 바를 사용하여 로딩 상태를 표시합니다.
    }
}


@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
//    val animatedProgress = animateFloatAsState(
//        targetValue = 0.3f,
//        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
//    ).value

    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(2f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(25.dp),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.emblem_bard_dark),
                    contentDescription = "직업군"
                )

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "character.serverName",
                        fontSize = 10.sp
                    )

                    Text(text = "character.name")

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "character.className",
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "character.itemLevel",
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(10.dp),
                        painter = painterResource(id = R.drawable.gold_coins), contentDescription = "")
                    Text(text = "진행률 : ", fontSize = 4.sp)
                    Text(text = "percentage", fontSize = 5.sp)
                }
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        progress = 0.3f,
                        strokeCap = StrokeCap.Round,
                        color = MokokoGreen,
                        trackColor = Color.LightGray
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "earnGold",
                            fontSize = 6.sp
                        )
                        Text(
                            text = "/totalGold",
                            fontSize = 6.sp
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider()

    }
}


//                            val pagerState = rememberPagerState(pageCount = { characterList.size })

//                                HorizontalPager(state = pagerState) { page ->
//                                    val characterName = characterList[page].name
//                                    val characterCardVM = remember { CharacterCardVM(characterRepository, characterName) }
//                                    Column(
//                                        modifier
//                                            .padding(16.dp)
//                                            .graphicsLayer {
//                                                val pageOffset = (
//                                                        (pagerState.currentPage - page) + pagerState
//                                                            .currentPageOffsetFraction
//                                                        ).absoluteValue
//                                                alpha = lerp(
//                                                    start = 0.5f,
//                                                    stop = 1f,
//                                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                                                )
//                                            }
//                                    ) {
//                                        val character by characterCardVM.character.collectAsState()
//
//                                        CharacterCard(
//                                            navController = navController,
//                                            viewModel = characterCardVM,
//                                            onDelete = {
//                                                characterListVM.delete(character)
//                                                isLoading = true
//                                            }
//                                        ) { characterCardVM.enableDelay() }
//
//                                    }
//                                }