package com.hongmyeoun.goldcalc.view.addScreen.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun AddScreenBottomBar(
    navController: NavHostController,
    viewModel: AddScreenVM
) {
    val isDuplicate by viewModel.isDuplicate.collectAsState()
    val isDuplicateCheck by viewModel.isDuplicateCheck.collectAsState()
    val nowNickname by viewModel.nickname.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG)
            .imePadding()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.AddCharacter.route) {
                        inclusive = true
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Text(text = ButtonText.CANCEL)
        }

        Spacer(modifier = Modifier.width(24.dp))

        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                viewModel.confirm()
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.AddCharacter.route) {
                        inclusive = true
                    }
                }
            },
            enabled = viewModel.confirmEnable(isDuplicate, isDuplicateCheck, nowNickname),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenQual,
                contentColor = Color.White,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.LightGray
            )
        ) {
            Text(text = ButtonText.CONFIRM)
        }
    }
}