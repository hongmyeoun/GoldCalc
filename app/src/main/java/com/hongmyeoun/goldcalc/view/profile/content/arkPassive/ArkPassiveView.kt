package com.hongmyeoun.goldcalc.view.profile.content.arkPassive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.hongmyeoun.goldcalc.model.common.htmlStyledText
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassiveNode
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.Title
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.ArkPassiveVM
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM

@Composable
fun ArkPassiveNode(
    viewModel: ProfileVM
) {
    val arkPassive by viewModel.arkPassive.collectAsState()
    val arkPassiveNodes by viewModel.arkPassiveNode.collectAsState()

    arkPassive?.let {
        arkPassiveNodes?.let { arkPassiveNodes ->
            ArkPassiveView(arkPassive = it, arkPassiveNodes = arkPassiveNodes)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArkPassiveView(
    arkPassive: ArkPassive,
    arkPassiveNodes: List<ArkPassiveNode>,
    viewModel: ArkPassiveVM = viewModel()
) {
    val showDialog by viewModel.showDialog.collectAsState()
    val isDetail by viewModel.isDetail.collectAsState()

    if (showDialog) {
        Description(
            viewModel = viewModel,
            arkPassiveNodes = arkPassiveNodes
        )
    }

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .noRippleClickable { viewModel.onDetailClicked() }
    ) {
        Title(
            title = "아크패시브",
            onClick = { viewModel.onDetailClicked() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (isDetail) {
            if (arkPassive.isArkPassive) {
                ArkPassiveNodeBox(
                    arkPassiveType = arkPassive.points[0].name,
                    arkPassiveNodes = arkPassiveNodes,
                    arkPassivePoints = arkPassive.points[0].value,
                    viewModel = viewModel
                )
                ArkPassiveNodeBox(
                    arkPassiveType = arkPassive.points[1].name,
                    arkPassiveNodes = arkPassiveNodes,
                    arkPassivePoints = arkPassive.points[1].value,
                    viewModel = viewModel
                )
                ArkPassiveNodeBox(
                    arkPassiveType = arkPassive.points[2].name,
                    arkPassiveNodes = arkPassiveNodes,
                    arkPassivePoints = arkPassive.points[2].value,
                    viewModel = viewModel
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Simple(
                    arkPassiveType = arkPassive.points[0].name,
                    arkPassivePoints = arkPassive.points[0].value,
                    arkPassiveRank = arkPassive.points[0].description,
                    viewModel = viewModel
                )
                Simple(
                    arkPassiveType = arkPassive.points[1].name,
                    arkPassivePoints = arkPassive.points[1].value,
                    arkPassiveRank = arkPassive.points[1].description,
                    viewModel = viewModel
                )
                Simple(
                    arkPassiveType = arkPassive.points[2].name,
                    arkPassivePoints = arkPassive.points[2].value,
                    arkPassiveRank = arkPassive.points[2].description,
                    viewModel = viewModel
                )
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Simple(
    arkPassiveType: String,
    arkPassivePoints: Int,
    arkPassiveRank: String,
    viewModel: ArkPassiveVM
) {
    val arkPassiveColor = viewModel.textColor(arkPassiveType)
    val arkPassiveIcon = viewModel.arkPassiveIcon(arkPassiveType)

    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = arkPassiveColor,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "$arkPassivePoints",
                    color = arkPassiveColor,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = arkPassiveRank,
                color = arkPassiveColor,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun ArkPassiveNodeBox(
    arkPassiveType: String,
    arkPassiveNodes: List<ArkPassiveNode>,
    arkPassivePoints: Int,
    viewModel: ArkPassiveVM
) {
    Column {
        ArkPassiveNodeTable(arkPassiveType, arkPassivePoints, viewModel)
        Column(modifier = Modifier.padding(12.dp)) {
            arkPassiveNodes.forEach {
                if (it.type == arkPassiveType) {
                    ArkPassiveContents(
                        arkPassiveNode = it,
                        viewModel = viewModel
                    )
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
    viewModel: ArkPassiveVM
) {
    val arkPassiveColor = viewModel.textColor(arkPassiveType)
    val arkPassiveIcon = viewModel.arkPassiveIcon(arkPassiveType)

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
    arkPassiveNode: ArkPassiveNode,
    viewModel: ArkPassiveVM
) {
    val arkPassiveColor = viewModel.textColor(arkPassiveNode.type)

    Row(
        modifier = Modifier.noRippleClickable { viewModel.onClicked(arkPassiveNode.name) },
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
            color = arkPassiveColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun Description(
    viewModel: ArkPassiveVM,
    arkPassiveNodes: List<ArkPassiveNode>,
) {
    val arkPassiveNode = viewModel.getArkPassive(arkPassiveNodes)

    arkPassiveNode?.let {
        Dialog(
            onDismissRequest = { viewModel.onDismissRequest() }
        ) {
            Column(
                modifier = Modifier
                    .background(ImageBG, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "아크패시브 노드",
                    style = titleTextStyle()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                ArkPassiveDetail(arkPassiveNode = arkPassiveNode, viewModel = viewModel)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassiveDetail(
    arkPassiveNode: ArkPassiveNode,
    viewModel: ArkPassiveVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = arkPassiveNode.icon,
            contentDescription = "각인 아이콘"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = arkPassiveNode.name,
            style = titleTextStyle(
                fontSize = 15.sp,
                color = viewModel.textColor(arkPassiveNode.type)
            )
        )

        Spacer(modifier = Modifier.width(4.dp))
        TextChip(
            text = arkPassiveNode.tier,
            borderless = true,
            customBGColor = LightGrayBG
        )

        Spacer(modifier = Modifier.width(4.dp))
        TextChip(
            text = arkPassiveNode.type,
            borderless = true,
            textColor = viewModel.textColor(arkPassiveNode.type)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .background(LightGrayBG, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = htmlStyledText(arkPassiveNode.script),
            style = normalTextStyle(fontSize = 12.sp),
            lineHeight = 16.sp,
            softWrap = true
        )
    }
}