package com.hongmyeoun.goldcalc.view.addScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.navigation.Screen
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun AddScreenView(
    navController: NavHostController,
    viewModel: AddScreenVM = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AddScreenTopBar(navController)
        },
        bottomBar = {
            AddScreenBottomBar(navController, viewModel)
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(ImageBG)
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 닉네임
            NicknameField(viewModel)

            // 아이템 레벨
            ItemLevelField(viewModel)

            // 서버
            ServerSelect(viewModel)

            // 직업
            ClassSelect(viewModel)
        }

    }

}

@Composable
private fun ItemLevelField(
    viewModel: AddScreenVM
) {
    val itemLevel by viewModel.itemLevel.collectAsState()

    Column {
        Text(
            text = "아이템 레벨",
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = itemLevel,
            onValueChange = { viewModel.itemLevelValueChange(it) },
            placeholder = {
                Text(
                    text = "0~1714.17",
                    style = normalTextStyle(color = Color.DarkGray, fontSize = 12.sp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified
            ),
            shape = RoundedCornerShape(16.dp)
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ClassSelect(
    viewModel: AddScreenVM
) {
    Column {
        Text(
            text = "직업",
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            val classDropdownExpanded by viewModel.classDropdownExpanded.collectAsState()
            val classSelect by viewModel.classSelect.collectAsState()

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = { viewModel.onClassDropdownClicked() }
            ) {
                Text(text = classSelect)
            }

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 220.dp),
                expanded = classDropdownExpanded,
                onDismissRequest = { viewModel.onClassDropdownDismiss() },
                properties = PopupProperties(focusable = true)
            ) {
                viewModel.className.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = { viewModel.onClassSelected(item) }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ServerSelect(
    viewModel: AddScreenVM
) {
    Column {
        Text(
            text = "서버",
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            val serverDropdownExpanded by viewModel.serverDropdownExpanded.collectAsState()
            val serverSelect by viewModel.serverSelect.collectAsState()

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = { viewModel.onServerDropdownClicked() }
            ) {
                Text(text = serverSelect)
            }

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp),
                expanded = serverDropdownExpanded,
                onDismissRequest = { viewModel.onServerDropdownDismiss() },
                properties = PopupProperties(focusable = true)
            ) {
                viewModel.serverName.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = { viewModel.onServerSelected(item) }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun NicknameField(viewModel: AddScreenVM) {
    val isDuplicate by viewModel.isDuplicate.collectAsState()
    val isDuplicateCheck by viewModel.isDuplicateCheck.collectAsState()

    val nickname by viewModel.nickname.collectAsState()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "닉네임",
                style = titleTextStyle()
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.size(width = 100.dp, height = 30.dp),
                onClick = { viewModel.checkDuplication(nickname) },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenQual,
                    contentColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.LightGray
                ),
                enabled = nickname.isNotEmpty()
            ) {
                Text(
                    text = "중복 체크",
                    style = normalTextStyle(fontSize = 12.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = nickname,
            onValueChange = { viewModel.nicknameValueChange(it) },
            placeholder = {
                Text(
                    text = "1~12글자의 닉네임을 입력해 주세요.",
                    style = normalTextStyle(color = Color.DarkGray, fontSize = 12.sp)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = null),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified
            ),
            shape = RoundedCornerShape(16.dp)
        )
        if (isDuplicateCheck) {
            Spacer(modifier = Modifier.height(4.dp))
            if (isDuplicate) {
                Text(
                    text = "이미 사용중인 캐릭터 입니다.",
                    style = normalTextStyle(color = RedQual, fontSize = 12.sp)
                )
            } else {
                Text(
                    text = "사용가능한 캐릭터 입니다.",
                    style = normalTextStyle(color = GreenQual, fontSize = 12.sp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun AddScreenBottomBar(
    navController: NavHostController,
    viewModel: AddScreenVM
) {
    val isDuplicate by viewModel.isDuplicate.collectAsState()

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
            enabled = viewModel.confirmEnable(isDuplicate, nowNickname),
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

@Composable
private fun AddScreenTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.weight(0.5f),
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.AddCharacter.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "뒤로",
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            text = "캐릭터 추가",
            style = titleTextStyle(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .width(32.dp)
                .weight(0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPreview() {
    GoldCalcTheme {
        var nickname by remember { mutableStateOf("") }
        var itemLevel by remember { mutableStateOf("") }

        var serverDropdownExpanded by remember { mutableStateOf(false) }
        val serverName = listOf("루페온", "아브렐슈드", "카마인")
        var serverSelect by remember { mutableStateOf(serverName[0]) }

        var classDropdownExpanded by remember { mutableStateOf(false) }
        val className = listOf("브레이커", "블레이드", "소서리스")
        var classSelect by remember { mutableStateOf(className[0]) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 닉네임
            Row {
                TextField(value = nickname, onValueChange = { nickname = it })
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "중복 체크")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 서버
                Box {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        onClick = { serverDropdownExpanded = true }
                    ) {
                        Text(text = serverSelect)
                    }

                    DropdownMenu(
                        expanded = serverDropdownExpanded,
                        onDismissRequest = { serverDropdownExpanded = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        serverName.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    serverSelect = item
                                    serverDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // 직업
                Box {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        onClick = { classDropdownExpanded = true }
                    ) {
                        Text(text = classSelect)
                    }

                    DropdownMenu(
                        expanded = classDropdownExpanded,
                        onDismissRequest = { classDropdownExpanded = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        className.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    classSelect = item
                                    classDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 아이템 레벨
            TextField(
                value = itemLevel,
                onValueChange = { itemLevel = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}