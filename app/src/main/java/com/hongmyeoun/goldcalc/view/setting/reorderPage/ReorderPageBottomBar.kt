package com.hongmyeoun.goldcalc.view.setting.reorderPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.constants.ButtonText
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.viewModel.setting.SettingVM

@Composable
fun ReorderBottomBar(
    viewModel: SettingVM,
    snackbarHostState: SnackbarHostState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBG)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = { viewModel.closeReorderPage() },
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
                viewModel.onSave(snackbarHostState)
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenQual,
                contentColor = Color.White
            )
        ) {
            Text(text = ButtonText.FIX)
        }
    }
}