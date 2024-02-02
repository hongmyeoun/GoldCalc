package com.example.goldcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goldcalc.ui.theme.GoldCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoldCalcTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main"){
                    composable("main"){
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row {
                                CharacterCard("캐릭터1")
                                CharacterCard("캐릭터2")
                            }
                            Row {
                                CharacterCard("캐릭터3")
                                CharacterCard("캐릭터4")
                            }
                            Row {
                                CharacterCard("캐릭터5")
                                CharacterCard("캐릭터6")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: String){
    Card {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = character)
        }
    }
}