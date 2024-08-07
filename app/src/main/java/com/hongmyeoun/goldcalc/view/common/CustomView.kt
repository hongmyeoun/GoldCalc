package com.hongmyeoun.goldcalc.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongmyeoun.goldcalc.ui.theme.RelicBG

// 커스텀 TextChip
@Composable
fun TextChip(
    text: String,
    textColor: Color = Color.White,
    customTextSize: TextUnit = 10.sp,
    borderless: Boolean = false,
    customPadding: Modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp),
    customBGColor: Color = Color.Black,
    brush: Boolean = false,
    customBGBrush: Brush = RelicBG,
    fixedWidth: Boolean = false,
    customWidthSize: Dp = 40.dp,
    customRoundedCornerSize: Dp = 4.dp,
    image: Boolean = false,
    yourImage: @Composable (() -> Unit)? = null
) {
    val modifier = if (!borderless) {
        Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    } else {
        Modifier
    }

    val customWidth = if (!fixedWidth) {
        Modifier
    } else {
        Modifier.width(customWidthSize)
    }

    val customBG = if (!brush) {
        Modifier
            .background(
                color = customBGColor,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    } else {
        Modifier
            .background(
                brush = customBGBrush,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .then(customWidth)
            .then(customBG)
            .then(customPadding)
    ) {
        if (!image) {
            Text(
                text = text,
                fontSize = customTextSize,
                color = textColor
            )
        } else {
            if (yourImage != null) {
                yourImage()
            }
            Text(
                text = text,
                fontSize = customTextSize,
                color = textColor
            )
        }
    }
}

@Composable
fun TextChip(
    text: AnnotatedString,
    textColor: Color = Color.White,
    customTextSize: TextUnit = 10.sp,
    borderless: Boolean = false,
    customPadding: Modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp),
    customBGColor: Color = Color.Black,
    brush: Boolean = false,
    customBGBrush: Brush = RelicBG,
    fixedWidth: Boolean = false,
    customWidthSize: Dp = 40.dp,
    customRoundedCornerSize: Dp = 4.dp,
    image: Boolean = false,
    yourImage: @Composable (() -> Unit)? = null
) {
    val modifier = if (!borderless) {
        Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    } else {
        Modifier
    }

    val customWidth = if (!fixedWidth) {
        Modifier
    } else {
        Modifier.width(customWidthSize)
    }

    val customBG = if (!brush) {
        Modifier
            .background(
                color = customBGColor,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    } else {
        Modifier
            .background(
                brush = customBGBrush,
                shape = RoundedCornerShape(customRoundedCornerSize)
            )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .then(customWidth)
            .then(customBG)
            .then(customPadding)
    ) {
        if (!image) {
            Text(
                text = text,
                fontSize = customTextSize,
                color = textColor
            )
        } else {
            if (yourImage != null) {
                yourImage()
            }
            Text(
                text = text,
                fontSize = customTextSize,
                color = textColor
            )
        }
    }
}
