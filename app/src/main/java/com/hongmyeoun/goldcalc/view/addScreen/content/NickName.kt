package com.hongmyeoun.goldcalc.view.addScreen.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongmyeoun.goldcalc.model.constants.viewConst.AddCharacter
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun NicknameField(viewModel: AddScreenVM) {
    val isDuplicate by viewModel.isDuplicate.collectAsState()
    val isDuplicateCheck by viewModel.isDuplicateCheck.collectAsState()

    val nickname by viewModel.nickname.collectAsState()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AddCharacter.NICKNAME,
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
                    text = AddCharacter.CHECK_DUPLICATE,
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
                    text = AddCharacter.NICKNAME_PLACEHOLDER,
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
                    text = AddCharacter.USED,
                    style = normalTextStyle(color = RedQual, fontSize = 12.sp)
                )
            } else {
                Text(
                    text = AddCharacter.NOT_USED,
                    style = normalTextStyle(color = GreenQual, fontSize = 12.sp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}
