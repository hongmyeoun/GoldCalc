package com.hongmyeoun.goldcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongmyeoun.goldcalc.retrofitest.CharacterDetailScreen
import com.hongmyeoun.goldcalc.retrofitest.CharacterScreen
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.view.setting.Setting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoldCalcTheme {
//                MainScreen()
//                Setting()
//                CharacterScreen()
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "Main") {
                    composable("Main"){
                        MainScreen(navController)
                    }
                    composable("Check"){
                        Setting()
                    }
                    composable("Search"){
                        CharacterScreen(navController)
                    }
                    composable("CharDetail/{charName}"){
                        val charName = it.arguments?.getString("charName") ?: "마일즈섬광"
                        CharacterDetailScreen(charName)
                    }
                }
//                CharacterDetailScreen()
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { Character(navController) }
    }
}

@Composable
fun Character(navController: NavHostController) {
    var progressPercentage by remember { mutableStateOf(0.0f) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(0.5f),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "직업"
            )
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(text = "서버")
                Row {
                    Text(text = "닉네임")
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "골드체크")
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "설정", modifier = Modifier.clickable { navController.navigate("Check") })
                }
                Row {
                    Text(text = "레벨")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "직업")
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "진행도")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "${(progressPercentage * 100).toInt()}%")
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = progressPercentage,
                    color = Color.Green
                )
            }
        }
        Row {
            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
                Text(text = "군단장 레이드")
            }
            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
                Text(text = "어비스 던전")
            }
        }
        Row {
            OutlinedButton(onClick = { progressPercentage += 0.1f }, enabled = progressPercentage < 1f) {
                Text(text = "어비스 레이드")
            }
            OutlinedButton(onClick = { progressPercentage = 0.0f }) {
                Text(text = "0%")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
//    Setting()
}