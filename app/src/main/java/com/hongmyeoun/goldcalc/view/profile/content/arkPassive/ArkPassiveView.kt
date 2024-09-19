package com.hongmyeoun.goldcalc.view.profile.content.arkPassive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassiveNode
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEnlightenment
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEvolution
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveLeap
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.Title
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun ArkPassiveView(
    viewModel: ProfileVM
) {
    val arkPassive by viewModel.arkPassive.collectAsState()
    val arkPassiveNodes by viewModel.arkPassiveNode.collectAsState()

    arkPassive?.let {
        arkPassiveNodes?.let { arkPassiveNodes ->
            Column(
                modifier = Modifier
                    .background(LightGrayTransBG, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Title(title = "아크패시브")
                Spacer(modifier = Modifier.height(8.dp))
                if (it.isArkPassive) {
                    ArkPassiveNodeBox(it.points[0].name, arkPassiveNodes, it.points[0].value)
                    ArkPassiveNodeBox(it.points[1].name, arkPassiveNodes, it.points[1].value)
                    ArkPassiveNodeBox(it.points[2].name, arkPassiveNodes, it.points[2].value)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArkPassiveTest() {
    GoldCalcTheme {
        Column(modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .noRippleClickable { }
            .padding(8.dp)
        ) {
            ArkPassiveNodeBoxTest("진화")
            ArkPassiveNodeBoxTest("깨달음")
            ArkPassiveNodeBoxTest("도약")
        }
    }
}

@Composable
private fun ArkPassiveNodeBox(
    arkPassiveType: String,
    arkPassiveNodes: List<ArkPassiveNode>,
    arkPassivePoints: Int,
) {
    val arkPassiveColor =
        if (arkPassiveType == "진화") ArkPassiveEvolution else if (arkPassiveType == "깨달음") ArkPassiveEnlightenment else if (arkPassiveType == "도약") ArkPassiveLeap else Color.White

    Column {
        ArkPassiveNodeTable(arkPassiveType, arkPassivePoints, arkPassiveColor)
        Column(modifier = Modifier.padding(12.dp)) {
            arkPassiveNodes.forEach {
                if (it.type == arkPassiveType) {
                    ArkPassiveContents(arkPassiveColor, it)
                    if (it != arkPassiveNodes.last()) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassiveNodeTable(
    arkPassiveType: String,
    arkPassivePoints: Int,
    arkPassiveColor: Color
) {
    val arkPassiveIcon =
        if (arkPassiveType == "진화") R.drawable.ico_arkpassive_evolution else if (arkPassiveType == "깨달음") R.drawable.ico_arkpassive_enlightenment else if (arkPassiveType == "도약") R.drawable.ico_arkpassive_leap else R.drawable.ico_arkpassive_evolution

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 0.5.dp,
                    color = arkPassiveColor,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GlideImage(
                    model = arkPassiveIcon,
                    loading = placeholder(arkPassiveIcon),
                    contentDescription = ""
                )
                Text(
                    text = "$arkPassiveType ",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$arkPassivePoints",
                    color = arkPassiveColor,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Divider(
            modifier = Modifier.align(Alignment.CenterVertically),
            thickness = 0.5.dp,
            color = arkPassiveColor,
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassiveContents(
    textColor: Color,
    arkPassiveNode: ArkPassiveNode,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            model = arkPassiveNode.icon,
            contentDescription = "아크패시브 노드 아이콘"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = arkPassiveNode.tier,
            color = Color.White,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${arkPassiveNode.name} ${arkPassiveNode.level}",
            color = textColor,
            fontSize = 12.sp
        )
    }
}


@Composable
private fun ArkPassiveNodeBoxTest(
    type: String
) {
    val arkPassiveIcon =
        if (type == "진화") R.drawable.ico_arkpassive_evolution else if (type == "깨달음") R.drawable.ico_arkpassive_enlightenment else if (type == "도약") R.drawable.ico_arkpassive_leap else R.drawable.ico_arkpassive_evolution
    val arkPassiveColor =
        if (type == "진화") ArkPassiveEvolution else if (type == "깨달음") ArkPassiveEnlightenment else if (type == "도약") ArkPassiveLeap else Color.White

    Column {
        ArkPassiveNodeTableTest(arkPassiveColor, arkPassiveIcon)
        Box(modifier = Modifier.padding(12.dp)) {
            ArkPassiveContentsTest(arkPassiveColor)
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassiveNodeTableTest(arkPassiveColor: Color, arkPassiveIcon: Int) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 0.5.dp,
                    color = arkPassiveColor,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    model = arkPassiveIcon,
                    loading = placeholder(arkPassiveIcon),
                    contentDescription = ""
                )
                Text(text = "진화 ", color = Color.White)
                Text(text = "140", color = arkPassiveColor)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Divider(
            modifier = Modifier.align(Alignment.CenterVertically),
            thickness = 0.5.dp,
            color = arkPassiveColor,
        )
    }
}

//@Composable
//@OptIn(ExperimentalGlideComposeApi::class)
//private fun ArkPassiveContents(
//    arkPassiveNode: ArkPassiveNode
//) {
//    Row(
//        horizontalArrangement = Arrangement.Center
//    ) {
//        GlideImage(
//            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
//            model = R.drawable.ark_passive_evolution_1,
//            loading = placeholder(R.drawable.ark_passive_evolution_1),
//            contentDescription = "아크패시브"
//        )
//        GlideImage(
//            modifier = Modifier.clip(RoundedCornerShape(16.dp)), model = arkPassiveNode.icon, contentDescription = "아크패시브"
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//
//        Text(
//            text = "1티어", color = Color.White
//        )
//        Text(
//            text = arkPassiveNode.tier, color = Color.White
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//
//        Text(
//            text = "치명 Lv.10",
//            color = ArkPassiveEvolution,
//        )
//        Text(
//            text = "${arkPassiveNode.name} ${arkPassiveNode.level}",
//            color = ArkPassiveEvolution,
//        )
//    }
//}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassiveContentsTest(
    textColor: Color,
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        GlideImage(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            model = R.drawable.ark_passive_evolution_1,
            loading = placeholder(R.drawable.ark_passive_evolution_1),
            contentDescription = "아크패시브"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "1티어", color = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "치명 Lv.10",
            color = textColor,
        )
    }
}