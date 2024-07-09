package com.hongmyeoun.goldcalc.view.search.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.model.constants.viewConst.Search
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.search.SearchVM

@Composable
fun WarningDialog(
    viewModel: SearchVM,
    title: String,
) {
    Dialog(onDismissRequest = { viewModel.onDismissRequest() }) {

        Column(
            modifier = Modifier.background(LightGrayBG, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = title,
                style = titleTextStyle()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = Search.DELETE_WARN,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 0.5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.LightGray
                    )
                ) {
                    Text(text = ButtonText.CANCEL)
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                )

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.deleteHistory()
                    },
                ) {
                    Text(
                        text = ButtonText.DELETE,
                        color = Color.Red
                    )
                }
            }
        }
    }
}