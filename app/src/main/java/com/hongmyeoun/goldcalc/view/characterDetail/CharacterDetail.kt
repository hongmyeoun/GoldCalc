package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.HigherUpgradeColor
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM
import kotlin.math.absoluteValue

@OptIn(ExperimentalLayoutApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailScreen(charName: String, viewModel: CharDetailVM = hiltViewModel()) {
    val context = LocalContext.current
    var characterDetail by remember { mutableStateOf<CharacterDetail?>(null) }
    var characterEquipment by remember { mutableStateOf<List<CharacterItem>?>(null) }
    var characterGem by remember { mutableStateOf<List<Gem>?>(null) }
    var characterCards by remember { mutableStateOf<List<Cards>?>(null) }
    var characterCardsEffects by remember { mutableStateOf<List<CardEffects>?>(null) }
    var characterSkills by remember { mutableStateOf<List<Skills>?>(null) }

    LaunchedEffect(Unit) {
        characterDetail = APIRemote.getCharDetail(context, charName)
        characterEquipment = APIRemote.getCharEquipment(context, charName)
        characterGem = APIRemote.getCharGem(context, charName)
        APIRemote.getCharCard(context, charName)?.let { (cards, effects) ->
            characterCards = cards
            characterCardsEffects = effects
        }
        characterSkills = APIRemote.getCharSkill(context, charName)

        viewModel.isSavedName(charName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = characterDetail?.characterImage,
            contentDescription = null,
        )
        Text(text = "${characterDetail?.characterClassName ?: "ERROR"} ${characterDetail?.characterName ?: "ERROR"} : Lv. ${characterDetail?.itemMaxLevel ?: 0}")
        Button(
            onClick = {
                val avatarImage = !characterDetail?.characterImage.isNullOrEmpty()

                val character = Character(
                    name = characterDetail!!.characterName,
                    itemLevel = characterDetail!!.itemMaxLevel,
                    serverName = characterDetail!!.serverName,
                    className = characterDetail!!.characterClassName,

                    guildName = characterDetail!!.guildName,
                    title = characterDetail!!.title,
                    characterLevel = characterDetail!!.characterLevel,
                    expeditionLevel = characterDetail!!.expeditionLevel,
                    pvpGradeName = characterDetail!!.pvpGradeName,
                    townLevel = characterDetail!!.townLevel,
                    townName = characterDetail!!.townName,
                    characterImage = characterDetail?.characterImage,
                    avatarImage = avatarImage
                )
                viewModel.saveCharacter(character)
            },
            enabled = !viewModel.isSaved
        ) {
            Text(text = "가져오기")
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "장비", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                CustomSuggestionChip("배신 333333")
                Spacer(modifier = Modifier.width(8.dp))
                CustomSuggestionChip("악세 품질 68")
                Spacer(modifier = Modifier.width(8.dp))
                CustomSuggestionChip("특성합 1875")
            }

            Row {
                Column(modifier = Modifier.weight(0.7f)) {
                    characterEquipment?.let { characterEquipmentList ->
                        characterEquipmentList.forEach {
                            when (it) {
                                is CharacterEquipment -> {
                                    EquipmentDetails(
                                        icon = it.itemIcon,
                                        grade = it.grade,
                                        name = it.type,
                                        upgrade = it.upgradeLevel,
                                        itemLevel = it.itemLevel,
                                        quality = "${it.itemQuality}",
                                        elixir1Lv = it.elixirFirstLevel,
                                        elixir1Op = it.elixirFirstOption,
                                        elixir2Lv = it.elixirSecondLevel,
                                        elixir2Op = it.elixirSecondOption,
                                        transcendenceLevel = it.transcendenceLevel,
                                        transcendenceMultiple = it.transcendenceTotal,
                                        higherUpgrade = it.highUpgradeLevel
                                    )
                                }
                            }
                        }
                    }
                }
                Column(modifier = Modifier.weight(0.4f)) {
                    characterEquipment?.let { characterEquipmentList ->
                        characterEquipmentList.forEach {
                            when (it) {
                                is CharacterAccessory -> {
                                    AccessoryDetails(
                                        icon = it.itemIcon,
                                        name = it.type,
                                        grade = it.grade,
                                        quality = "${it.itemQuality}"
                                    )
                                }

                                is AbilityStone -> {
                                    AccessoryDetails(
                                        icon = it.itemIcon,
                                        name = "스톤",
                                        grade = it.grade,
                                        quality = "68"
                                    )

                                }
                            }
                        }
                    }

                }
            }

            characterGem?.let { gemList ->
                val annihilation = gemList.count { it.type == "멸화" }
                val crimsonFlame = gemList.size - annihilation

                var isDetail by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDetail = !isDetail }
                ) {
                    Text(text = "보석")
                    TextChip(text = "멸화 x$annihilation")
                    TextChip(text = "홍염 x$crimsonFlame")
                }
                if (isDetail) {
                    Row {
                        GemDetail(
                            modifier = Modifier.weight(1f),
                            effectType = "피해",
                            gemList = gemList
                        )
                        GemDetail(
                            modifier = Modifier.weight(1f),
                            effectType = "재사용 대기시간",
                            gemList = gemList
                        )
                    }
                } else {
                    Row {
                        val (annMaxItemCount, criMaxItemCount) = calcMaxItemsInEachRow(annihilation, crimsonFlame)
                        Column {
                            FlowRow(
                                maxItemsInEachRow = annMaxItemCount
                            ) {
                                gemList.filter { it.type == "멸화" }.forEach {
                                    GemSimple(it)
                                }
                            }
                        }
                        Column {
                            FlowRow(
                                maxItemsInEachRow = criMaxItemCount
                            ) {
                                gemList.filter { it.type == "홍염" }.forEach {
                                    GemSimple(it)
                                }
                            }
                        }

                    }

                }
            }

            characterCards?.let { cardList ->
                var isDetail by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDetail = !isDetail }
                ) {
                    Text(text = "카드")
                    characterCardsEffects?.let { cardEffects ->
                        cardEffects.forEach { effect ->
                            val cardInfo = effect.items.last().name
                            val regex = Regex("""(.*)\s(\d+세트)\s\((\d+)각성합계\)""")
                            regex.find(cardInfo)?.let {
                                val (setOption, setLevel, setAwakeCount) = it.destructured
                                TextChip(text = "$setOption $setAwakeCount (${setLevel})")
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    cardList.forEach { card ->
                        CardImage(
                            grade = card.grade,
                            icon = card.icon,
                            awakeCount = card.awakeCount
                        )
                    }
                }
            }

            characterSkills?.let { skills ->
                Column(
                    modifier = Modifier.background(Color.Black)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .background(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "뭐 넣어야 될까",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    skills.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box {
                                Column {
                                    Row {
                                        GlideImage(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .border(
                                                    width = 1.dp,
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(10.dp)
                                                )
                                                .clip(RoundedCornerShape(10.dp)),
                                            model = it.icon,
                                            contentScale = ContentScale.Crop,
                                            contentDescription = ""
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                }
                                Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                                    TextChip(text = "${it.level}")
                                }
                            }
                            Column(horizontalAlignment = Alignment.Start) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = it.name,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    it.tripods.let { tripods ->
                                        tripods.forEach { tripod ->
                                            if (tripod.isSelected) {
                                                TextChip(text = "${tripod.slot}")
                                            }
                                        }
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    it.tripods.let { tripods ->
                                        tripods.forEach { tripod ->
                                            if (tripod.isSelected) {
                                                TextChip(text = "${tripod.level}")
                                                Text(
                                                    text = tripod.name,
                                                    color = Color.White,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            if (it.rune != null || it.gem != null) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    it.rune?.let { rune ->
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            TextChip(text = rune.name)
                                            GlideImage(
                                                modifier = Modifier
                                                    .background(
                                                        brush = LegendaryBG,
                                                        shape = RoundedCornerShape(30.dp)
                                                    ),
                                                model = rune.icon,
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                    it.gem?.let { gems ->
                                        gems.forEach { gem ->
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                TextChip(text = gem["name"]!!)
                                                GlideImage(
                                                    modifier = Modifier
                                                        .background(
                                                            brush = LegendaryBG,
                                                            shape = RoundedCornerShape(30.dp)
                                                        ),
                                                    model = gem["icon"],
                                                    contentDescription = ""
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

fun calcMaxItemsInEachRow(ann: Int, cri: Int): Pair<Int, Int> {
    val difference = (ann - cri).absoluteValue
    return when {
        difference < 7 -> if (ann > cri) Pair(4, 3) else Pair(3, 4)
        difference < 9 -> if (ann > cri) Pair(5, 2) else Pair(2, 5)
        difference < 11 -> if (ann > cri) Pair(6, 1) else Pair(1, 6)
        else -> if (ann > cri) Pair(7, 0) else Pair(0, 7)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GemSimple(it: Gem) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(4.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(44.dp)
                .background(
                    brush = RelicBG,
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
                .padding(4.dp),
            model = it.gemIcon,
            contentDescription = "",
        )
        Text(text = "${it.level}", fontSize = 10.sp, color = Color.White)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardImage(grade: String, icon: String, awakeCount: Int) {
    val horizontalBias = when (grade) {
        "일반" -> {
            -1f
        }

        "고급" -> {
            -0.6f
        }

        "희귀" -> {
            -0.2f
        }

        "영웅" -> {
            0.2f
        }

        "전설" -> {
            0.6f
        }

        else -> {
            1f
        }
    }

    val xOffset = ((5 - awakeCount) * (-8.8)).dp
    Box(
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Box(
            modifier = Modifier.size(width = 47.dp, height = 69.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier.padding(start = 1.dp, end = 1.7.dp, top = 1.dp),
                    model = icon,
                    contentDescription = "카드 이미지"
                )
            }
            GlideImage(
                alignment = BiasAlignment(horizontalBias, 0f),
                contentScale = ContentScale.FillHeight,
                model = "https://cdn-lostark.iloa.gg/2018/obt/assets/images/pc/profile/img_card_grade.png",
                contentDescription = "카드 테두리"
            )
        }
        Box(
            modifier = Modifier
                .height(80.6.dp)
                .padding(start = 3.dp, bottom = 3.dp, end = 3.dp)
                .offset(x = (-1).dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Box {
                // 활성화 안된거
                Box(
                    modifier = Modifier.clipToBounds()
                ) {
                    GlideImage(
                        modifier = Modifier.offset(y = 12.6.dp),
                        model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/profile/img_profile_awake.png",
                        contentDescription = "빈슬롯(위)"
                    )
                }

                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    // 활성화 된거
                    Box(
                        modifier = Modifier.clipToBounds()
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .offset(x = xOffset, y = (-12.6).dp),
                            alignment = BiasAlignment(-1f, 0f),
                            model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/profile/img_profile_awake.png",
                            contentDescription = "활성화"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.background(Color.Black)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "뭐 넣어야 될까",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Box {
                        Column {
                            Row {
                                GlideImage(
                                    loading = placeholder(painterResource(id = R.drawable.bd_skill_01_26)),
                                    modifier = Modifier
                                        .size(44.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clip(RoundedCornerShape(10.dp)),
                                    model = "",
                                    contentScale = ContentScale.Crop,
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                            TextChip(text = "${10}")
                        }
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "소나티네",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TextChip(text = "${1}")
                            TextChip(text = "${3}")
                            TextChip(text = "${2}")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextChip(text = "${5}")
                            Text(
                                text = "빠른 준비",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            TextChip(text = "${1}")
                            Text(
                                text = "음표 낙인",
                                color = Color.White,
                                fontSize = 10.sp
                            )

                            Spacer(modifier = Modifier.width(4.dp))
                            TextChip(text = "${5}")
                            Text(
                                text = "선율 증가",
                                color = Color.White,
                                fontSize = 10.sp
                            )

                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextChip(text = "집중")
                            GlideImage(
                                modifier = Modifier
                                    .background(
                                        brush = LegendaryBG,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                ,
                                loading = placeholder(painterResource(id = R.drawable.use_7_200)),
                                model = "",
                                contentDescription = ""
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            TextChip(text = "7홍")
                            GlideImage(
                                modifier = Modifier
                                    .background(
                                        brush = LegendaryBG,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                ,
                                loading = placeholder(painterResource(id = R.drawable.use_7_200)),
                                model = "",
                                contentDescription = ""
                            )
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GemDetail(
    modifier: Modifier,
    effectType: String,
    gemList: List<Gem>
) {
    Column(
        modifier = modifier.background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = effectType,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        val type = if (effectType == "피해") "멸화" else "홍염"

        gemList.filter { it.type == type }.forEach { gem ->
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                Box {
                    Column {
                        Row {
                            GlideImage(
                                modifier = Modifier
                                    .size(44.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clip(RoundedCornerShape(10.dp)),
                                model = gem.skillIcon,
                                contentScale = ContentScale.Crop,
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                        TextChip(text = "${gem.level}")
                    }
                }
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = gem.skill,
                        color = RelicColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = gem.effect.substringAfter("$effectType "),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun AccessoryDetails(
    icon: String,
    name: String,
    grade: String,
    quality: String
) {
    val itemBG = if (grade == "고대") AncientBG else RelicBG

    Row {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = itemBG,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            GlideImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                model = icon,
                contentDescription = ""
            )
            TextChip(text = name)
        }
        Spacer(modifier = Modifier.width(4.dp))
        UpgradeQualityRow(quality)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun EquipmentDetails(
    icon: String,
    grade: String,
    name: String,
    upgrade: String,
    itemLevel: String,
    quality: String,
    elixir1Lv: String,
    elixir1Op: String,
    elixir2Lv: String,
    elixir2Op: String,
    transcendenceLevel: String,
    transcendenceMultiple: String,
    higherUpgrade: String,
) {
    val itemBG = if (grade == "고대") AncientBG else RelicBG

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = itemBG,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            GlideImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                model = icon,
                contentDescription = "",
            )
            TextChip(text = name)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            UpgradeQualityRow(quality, upgrade, itemLevel)
            if (transcendenceLevel.isNotEmpty() || higherUpgrade.isNotEmpty()) {
                TranscendenceLevelRow(transcendenceLevel, transcendenceMultiple, higherUpgrade)
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        if (elixir1Lv.isNotEmpty()) {
            Column {
                ElixirLevelOptionRow(elixir1Lv, elixir1Op)
                ElixirLevelOptionRow(elixir2Lv, elixir2Op)
            }
        }
    }

}

@Composable
private fun ElixirLevelOptionRow(level: String, option: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElixirTextChip(level)
        Text(text = option, fontSize = 10.sp)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun TranscendenceLevelRow(level: String, multiple: String, upgrade: String) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (level.isNotEmpty()) {
            GlideImage(
                model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png",
                contentDescription = ""
            )
            Text(text = "Lv.$level", fontSize = 10.sp)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "x$multiple", fontSize = 10.sp)
            Spacer(modifier = Modifier.width(2.dp))
        }
        if (upgrade.isNotEmpty()) {
            Text(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = HigherUpgradeColor,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                text = "+$upgrade",
                fontSize = 10.sp,
            )
        }
    }
}

@Composable
private fun UpgradeQualityRow(quality: String, upgrade: String = "", itemLevel: String = "") {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (upgrade.isNotEmpty()) {
            Text(
                text = upgrade,
                fontSize = 10.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        if (itemLevel.isNotEmpty()) {
            TextChip(itemLevel)
        }
        QualityTextChip(quality)
    }
}

@Composable
fun CustomSuggestionChip(labelText: String) {
    SuggestionChip(
        shape = MaterialTheme.shapes.small,
        enabled = false,
        onClick = { /*TODO*/ },
        label = { Text(text = labelText) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            disabledContainerColor = Color.Transparent,
            disabledLabelColor = Color.Black
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            disabledBorderColor = Color.Black
        )
    )
}

@Composable
fun TextChip(
    text: String,
) {
    val textSize = if (text.length == 3) 8.sp else 10.sp

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = textSize,
            color = Color.White
        )
    }
}

@Composable
fun QualityTextChip(
    text: String,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.White
        )
    }
}


@Composable
fun ElixirTextChip(
    text: String,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.White
        )
    }
}