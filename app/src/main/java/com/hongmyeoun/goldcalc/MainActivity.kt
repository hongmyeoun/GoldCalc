package com.hongmyeoun.goldcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.Phase
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.view.goldCheck.Setting
import com.hongmyeoun.goldcalc.view.search.CharacterDetailScreen
import com.hongmyeoun.goldcalc.view.search.CharacterScreen
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import com.hongmyeoun.goldcalc.viewModel.main.CharacterCardVM
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
                    composable("Main"){
                        MainScreen(
                            navController = navController
                        ) {
                            CharacterContents(it, navController)
                        }
                    }
                    composable("Check/{charName}"){
                        val charName = it.arguments?.getString("charName")?: "ERROR"
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
                    composable("Search"){
                        CharacterScreen(navController)
                    }
                    composable("CharDetail/{charName}"){
                        val charName = it.arguments?.getString("charName") ?: "마일즈섬광"
                        CharacterDetailScreen(charName)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit
) {
    var progressPercentage by remember { mutableStateOf(0.0f) }

    Scaffold(
        topBar = {
            Column {
                Row {
                    Text(text = "로골기")
                    IconButton(onClick = { navController.navigate("Search") }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "검색/추가")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "새로고침")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "설정")
                    }
                }
                Column {
                    Row {
                        Text(text = "숙제 진행도")
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = "전체 진행사항 한눈에 보기")
                        }
                        Text(text = "퍼센트")
                    }
                    LinearProgressIndicator(
                        progress = progressPercentage,
                        color = Color.Green
                    )
                }
                Row {
                    Column {
                        Text(text = "주간 총 골드")
                        Row {
                            Image(painter = painterResource(id = R.drawable.gold_coins), contentDescription = "골드")
                            Text(text = "1,000")
                        }
                    }
                    Column {
                        Text(text = "얻은 골드")
                        Row {
                            Image(painter = painterResource(id = R.drawable.gold_coins), contentDescription = "골드")
                            Text(text = "1,000")
                        }
                    }
                    Column {
                        Text(text = "남은 골드")
                        Row {
                            Image(painter = painterResource(id = R.drawable.gold_coins), contentDescription = "골드")
                            Text(text = "1,000")
                        }
                    }
                }
            }
        }
    ) {
        content(
            Modifier
                .fillMaxSize()
                .padding(it))
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

@Composable
fun CharacterContents(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: CharacterCardVM = hiltViewModel()
) {
    val characterList by viewModel.characters.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(characterList, key = { item -> item.name }){
            CharacterCard(navController, it) {
                viewModel.delete(it)
            }
        }
    }
}

@Composable
fun CharacterCard(
    navController: NavHostController,
    character: com.hongmyeoun.goldcalc.model.roomDB.Character,
    onDelete: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    var progressPercentage by remember { mutableStateOf(0.0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.weight(0.5f),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = CharacterResourceMapper.getClassImage(isDark, character.className)),
                contentDescription = "직업군"
            )
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(text = character.serverName)
                Row {
                    Text(text = character.name)
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "골드체크",
                        modifier = Modifier
                                .clickable { onDelete() }
                    )
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "설정",
                        modifier = Modifier
                            .clickable { navController.navigate("Check/${character.name}") })
                }
                Row {
                    Text(text = character.itemLevel)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = character.className)
                }
            }
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column {
                        Row {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${character.weeklyGold}")
                        }
                        Text(text = "진행도")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${(progressPercentage * 100).toInt()}%")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = progressPercentage,
                    color = Color.Green
                )
            }
        }
        Column {
            GoldContentStateUI(
                phases = character.checkList.epic[0].phases,
                raidImg = R.drawable.epic_behemoth,
                raidName = "베히모스"
            )
            GoldContentStateUI(
                phases = character.checkList.kazeroth[0].phases,
                raidImg = R.drawable.kazeroth_echidna,
                raidName = "에키드나"
            )
            GoldContentStateUI(
                phases = character.checkList.command[5].phases,
                raidImg = R.drawable.command_kamen,
                raidName = "카멘"
            )
            GoldContentStateUI(
                phases = character.checkList.abyssDungeon[1].phases,
                raidImg = R.drawable.abyss_dungeon_ivory_tower,
                raidName = "혼돈의 상아탑"
            )
            GoldContentStateUI(
                phases = character.checkList.command[4].phases,
                raidImg = R.drawable.command_illiakan,
                raidName = "일리아칸"
            )
            GoldContentStateUI(
                phases = character.checkList.abyssDungeon[0].phases,
                raidImg = R.drawable.abyss_dungeon_kayangel,
                raidName = "카양겔"
            )
            GoldContentStateUI(
                phases = character.checkList.command[3].phases,
                raidImg = R.drawable.command_abrelshud,
                raidName = "아브렐슈드"
            )
            GoldContentStateUI(
                phases = character.checkList.command[2].phases,
                raidImg = R.drawable.command_kouku,
                raidName = "쿠크세이튼"
            )
            GoldContentStateUI(
                phases = character.checkList.command[1].phases,
                raidImg = R.drawable.command_biackiss,
                raidName = "비아키스"
            )
            GoldContentStateUI(
                phases = character.checkList.command[0].phases,
                raidImg = R.drawable.command_valtan,
                raidName = "발탄"
            )
        }
//        Row {
//            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
//                Text(text = "군단장 레이드")
//            }
//            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
//                Text(text = "어비스 던전")
//            }
//        }
//        Row {
//            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
//                Text(text = "어비스 레이드")
//            }
//            OutlinedButton(onClick = { progressPercentage = 0.0f }) {
//                Text(text = "0%")
//            }
//        }
    }
}

@Composable
fun GoldContentStateUI(
    phases: List<Phase>,
    raidImg: Int,
    raidName: String,
) {
    if (phases[0].isClear) {
        var phase = 1

        for (index in phases.indices) {
            if (phases[index].isClear) {
                phase = index + 1
            } else {
                break
            }
        }
        val textColor = if (raidName == "카양겔") Color.Black else Color.White

        var nowPhase by remember { mutableStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable(enabled = nowPhase <= phase - 1) { nowPhase += 1 }
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(21f / 9f)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = raidImg),
                contentDescription = "보스 이미지"
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = raidName,
                    color = textColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${nowPhase}/${phase} 관문",
                    color = textColor
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
//    Setting()
}