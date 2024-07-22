package com.hongmyeoun.goldcalc.view.addScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.window.PopupProperties
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.model.constants.viewConst.Setting
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle

@Composable
fun AddScreenView(

) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGrayBG),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.weight(0.5f),
                    onClick = {
//                        navController.navigate(Screen.Home.route) {
//                            popUpTo(Screen.Setting.route) {
//                                inclusive = true
//                            }
//                        }
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
                    text = Setting.SETTING,
                    style = titleTextStyle(),
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier
                        .width(32.dp)
                        .weight(0.5f)
                )
            }

        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGrayBG)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { },
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
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenQual,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = ButtonText.FIX)
                }
            }
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
            Row {
                var nickname by remember { mutableStateOf("") }

                val maxLength = 12

                TextField(
                    value = nickname,
                    onValueChange = {
                        if (it.length <= maxLength) {
                            nickname = it
                        }
                    },
                    placeholder = { Text(text = "1~12글자의 닉네임을 입력해 주세요.") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Unspecified,
                        unfocusedIndicatorColor = Color.Unspecified
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "중복 체크")
                }
            }

            // 서버
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                var serverDropdownExpanded by remember { mutableStateOf(false) }
                val serverName = listOf("루페온", "아브렐슈드", "카마인")
                var serverSelect by remember { mutableStateOf(serverName[0]) }

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
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                var classDropdownExpanded by remember { mutableStateOf(false) }
                val className = listOf("브레이커", "블레이드", "소서리스")
                var classSelect by remember { mutableStateOf(className[0]) }
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

            // 아이템 레벨
            var itemLevel by remember { mutableStateOf("") }
            val maxLength = 7
            val maxLevel = 1714.17
            TextField(
                value = itemLevel,
                onValueChange = {
                    if (it.length <= maxLength && it.toFloatOrNull() != null && it.toFloat() <= maxLevel + 0.01f) {
                        itemLevel = it
                    } else if (it.toFloatOrNull() == null) {
                        itemLevel = ""
                    }
                },
                placeholder = { Text(text = "0~1714.17") },
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