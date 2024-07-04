package com.hongmyeoun.goldcalc.view.home.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM

@Composable
fun TitleAndMenu(
    navController: NavHostController,
    viewModel: CharacterListVM,
    snackbarHostState: SnackbarHostState
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Title(modifier = Modifier.align(Alignment.CenterStart))
        Menus(modifier = Modifier.align(Alignment.CenterEnd), navController, viewModel, snackbarHostState)
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Title(modifier: Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontSize = 30.sp
                )
            ) {
                append("로")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
            ) {
                append("아")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontSize = 30.sp
                )
            ) {
                append("골")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
            ) {
                append("드 계산")
            }

            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontSize = 30.sp
                )
            ) {
                append("기")
            }

        },
        modifier = modifier
    )
}

@Composable
private fun Menus(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: CharacterListVM,
    snackbarHostState: SnackbarHostState
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(35.dp),
            onClick = { navController.navigate(Screen.Search.route) }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.White,
                contentDescription = "검색/추가"
            )
        }

        IconButton(
            modifier = Modifier.size(35.dp),
            onClick = { viewModel.onReset(snackbarHostState) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_settings_backup_restore_24),
                tint = Color.White,
                contentDescription = "새로고침"
            )
        }

        IconButton(
            modifier = Modifier.size(35.dp),
            onClick = { navController.navigate(Screen.Setting.route) }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                tint = Color.White,
                contentDescription = "설정"
            )
        }
    }
}
