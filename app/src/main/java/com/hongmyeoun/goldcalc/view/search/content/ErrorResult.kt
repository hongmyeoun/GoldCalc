package com.hongmyeoun.goldcalc.view.search.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.ErrorMessage
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig

@Composable
fun Error(errorMessage: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage == NetworkConfig.NETWORK_ERROR) {
            Image(
                painter = painterResource(id = R.drawable.baseline_wifi_off_24),
                contentDescription = "Disconnect"
            )
            Text(
                text = errorMessage,
                color = Color(0xFFCFCFCF)
            )
        } else if (errorMessage == ErrorMessage.ERROR_503) {
            Text(
                text = ErrorMessage.ERROR_503_MESSAGE,
                color = Color(0xFFCFCFCF)
            )
        } else if (errorMessage != null && errorMessage.contains(ErrorMessage.NO)) {
            Text(
                text = errorMessage,
                color = Color(0xFFCFCFCF)
            )
        }
    }
}