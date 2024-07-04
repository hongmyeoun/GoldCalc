package com.hongmyeoun.goldcalc.view.profile.content.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.profile.titleBoldWhite12
import com.hongmyeoun.goldcalc.viewModel.charDetail.CardDetailVM

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardImage(
    grade: String,
    icon: String,
    awakeCount: Int,
    viewModel: CardDetailVM,
    name: String = "",
    detail: Boolean = false
) {
    val cardBorder = viewModel.cardBorderGrade(grade)
    val awake = viewModel.awakePoint(awakeCount)
    val (cardSize, awakeUnfilledSize, awakeFillSize) = viewModel.imageSizes(detail, awakeCount)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            GlideImage(
                modifier = Modifier
                    .then(cardSize)
                    .padding(start = 1.dp, end = 1.dp),
                model = icon,
                contentDescription = "카드 이미지"
            )
            GlideImage(
                modifier = cardSize,
                model = cardBorder,
                contentDescription = "카드 테두리"
            )
            Box(
                modifier = cardSize,
                contentAlignment = Alignment.BottomCenter
            ) {
                // 활성화 안된거
                Box {
                    GlideImage(
                        modifier = awakeUnfilledSize,
                        model = R.drawable.img_profile_awake_unfilled,
                        contentDescription = "빈슬롯"
                    )
                    // 활성화 된거
                    GlideImage(
                        modifier = awakeFillSize,
                        model = awake,
                        contentDescription = "활성화"
                    )
                }
            }
        }
        if (detail) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                style = titleBoldWhite12()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
