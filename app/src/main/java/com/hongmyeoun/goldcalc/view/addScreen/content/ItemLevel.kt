package com.hongmyeoun.goldcalc.view.addScreen.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun ItemLevelField(
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
