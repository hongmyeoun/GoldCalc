package com.hongmyeoun.goldcalc.view.goldCheck.setting

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.LoadingScreen
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.DarkModeGray
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.goldCheck.RaidCard
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.AbyssDungeon
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.CommandRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.EpicRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.KazerothRaid
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GoldSettingContent(
    paddingValues: PaddingValues,
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM
) {
    val character by viewModel.character.collectAsState()
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        LoadingScreen()
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            item {
                GoldSettingCharacterDetails(
                    character = character,
                    onReloadClick = { viewModel.onReloadClick(context, character?.name) },
                    onAvatarClick = { viewModel.onAvatarClick(character) }
                )
            }
            stickyHeader { RaidHeader(viewModel) }
            item { GoldSetting(viewModel, cbVM, adVM, kzVM, epVM) }
        }
    }
}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GoldSettingCharacterDetails(character: Character?, onReloadClick: () -> Unit, onAvatarClick: () -> Unit) {
    var isBlinking by remember { mutableStateOf(false) }
    val characterImgText = if (character?.avatarImage == true) "아바타" else "기본"
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .background(ImageBG)
            .fillMaxWidth()
    ) {
        character?.let {
            if (it.avatarImage) {
                GlideImage(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .height(320.dp),
                    model = it.characterImage,
                    contentDescription = "캐릭터 이미지"
                )
            } else {
                GlideImage(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .height(320.dp),
                    contentScale = ContentScale.FillHeight,
                    model = CharacterResourceMapper.getClassDefaultImg(character.className),
                    contentDescription = "캐릭터 이미지"
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                DetailInfomation(detailMenu = "닉네임", detail = it.name)
                DetailInfomation(detailMenu = "서    버", detail = it.serverName)
                DetailInfomation(detailMenu = "클래스", detail = it.className)
                DetailInfomation(detailMenu = "템레벨", detail = it.itemLevel)
                DetailInfomation(detailMenu = "원정대", detail = it.expeditionLevel.toString())
                DetailInfomation(detailMenu = "칭    호", detail = it.title)
                DetailInfomation(detailMenu = "전투렙", detail = it.characterLevel.toString())
                DetailInfomation(detailMenu = "길    드", detail = it.guildName ?: "-")
                DetailInfomation(detailMenu = "P  V  P", detail = it.pvpGradeName)
                DetailInfomation(detailMenu = "영    지", detail = "Lv.${it.townLevel} ${it.townName}")
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp, bottom = 16.dp)
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        onAvatarClick()
                        isBlinking = true
                        coroutineScope.launch {
                            delay(2250)
                            isBlinking = false
                        }
                    },
                    containerColor = DarkModeGray
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "아바타 이미지",
                        tint = Color.White
                    )
                }
                SmallFloatingActionButton(
                    onClick = onReloadClick,
                    containerColor = DarkModeGray
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "새로고침",
                        tint = Color.White
                    )
                }
            }
            BlinkingText(isBlinking, characterImgText, Modifier.align(Alignment.TopEnd))
        }
    }
}

@Composable
fun BlinkingText(isBlinking: Boolean, text: String, modifier: Modifier) {
    Column(
        modifier = modifier.padding(top = 24.dp, end = 16.dp)
    ) {
        if (isBlinking) {
            val infiniteTransition = rememberInfiniteTransition()

            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 750 // 1초 동안 깜빡이도록 수정
                    },
                    repeatMode = RepeatMode.Reverse
                )
            )
            Text(
                text = text,
                color = Color.White.copy(alpha = alpha)
            )
        }
    }
}
@Composable
private fun DetailInfomation(detailMenu: String, detail: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .background(DarkModeGray, RoundedCornerShape(16.dp))
                .width(80.dp),
            text = "  $detailMenu  ",
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = detail,
            color = Color.White
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}


@Composable
private fun RaidHeader(viewModel: GoldSettingVM, isDark: Boolean = isSystemInDarkTheme()) {
    val bgColor = if (isDark) ImageBG else Color.White
    Column {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .height(50.dp)
        ) {
            TopBarBox(
                title = "군단장",
                modifier = Modifier.weight(1f),
                onClick = { viewModel.moveCommandRaid() }
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            TopBarBox(
                title = "어비스 던전",
                modifier = Modifier.weight(1f),
                onClick = { viewModel.moveAbyssDungeon() }
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            TopBarBox(
                title = "카제로스",
                modifier = Modifier.weight(1f),
                onClick = { viewModel.moveKazeRaid() }
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            TopBarBox(
                title = "에픽",
                modifier = Modifier.weight(1f),
                onClick = { viewModel.moveEpicRaid() }
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            TopBarBox(
                title = "기타",
                modifier = Modifier.weight(1f),
                onClick = { viewModel.moveETC() }
            )
        }
    }
    Divider()
}

@Composable
private fun TopBarBox(
    title: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (title == "어비스 던전") {
            Text(
                text = title,
                fontSize = 12.sp
            )
        } else {
            Text(text = title)
        }
    }
}


@Composable
private fun GoldSetting(
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (viewModel.selectedTab) {
            "군단장" -> {
                RaidCard(
                    raidImg = R.drawable.command_icon,
                    totalGold = cbVM.totalGold
                ) {
                    CommandRaid(viewModel = cbVM)
                }
            }

            "어비스 던전" -> {
                RaidCard(
                    raidImg = R.drawable.abyss_dungeon_icon,
                    totalGold = adVM.totalGold,
                ) {
                    AbyssDungeon(viewModel = adVM)
                }
            }

            "카제로스" -> {
                RaidCard(
                    raidImg = R.drawable.kazeroth_icon,
                    totalGold = kzVM.totalGold
                ) {
                    KazerothRaid(viewModel = kzVM)
                }
            }

            "에픽" -> {
                RaidCard(
                    raidImg = R.drawable.epic_icon,
                    totalGold = epVM.totalGold
                ) {
                    EpicRaid(viewModel = epVM)
                }
            }

            "기타" -> {
                ETCGold(
                    viewModel = viewModel,
                    onDone = { viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold) },
                )
            }
        }
    }
}

@Composable
fun ETCGold(
    viewModel: GoldSettingVM,
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
            )
        )
    }

}