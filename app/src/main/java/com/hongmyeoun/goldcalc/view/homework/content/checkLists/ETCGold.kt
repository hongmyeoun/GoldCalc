package com.hongmyeoun.goldcalc.view.homework.content.checkLists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM

@Composable
fun ETCGold(
    viewModel: HomeworkVM,
    onDone: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.plusGold,
            onValueChange = { viewModel.plusGoldValue(it) },
            label = { Text(text = "추가 골드") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    keyboardController?.hide()
                    focusState.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,

                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,

                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.minusGold,
            onValueChange = { viewModel.minusGoldValue(it) },
            label = { Text(text = "사용 골드") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    keyboardController?.hide()
                    focusState.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,

                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,

                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White
            )
        )
    }
}
