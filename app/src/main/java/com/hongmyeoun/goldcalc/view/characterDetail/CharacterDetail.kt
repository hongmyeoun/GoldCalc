package com.hongmyeoun.goldcalc.view.characterDetail

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.gson.Gson
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.searchedInfo.HeadgearTooltipETSX
import com.hongmyeoun.goldcalc.model.searchedInfo.HeadgearTooltipEX
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailScreen(charName: String, viewModel: CharDetailVM = hiltViewModel()) {
    val context = LocalContext.current
    var characterDetail by remember { mutableStateOf<CharacterDetail?>(null) }
//    var characterEquipment by remember { mutableStateOf<List<Equipment>?>(null) }

    LaunchedEffect(Unit) {
        characterDetail = APIRemote.getCharDetail(context, charName)
//        characterEquipment = APIRemote.getCharDetails(context, charName)
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

        testJson()
//        if (characterEquipment != null) {
//            characterEquipment?.let { equipList ->
//                LazyColumn(modifier = Modifier.height(1000.dp)) {
//                    items(equipList, key = { item -> item.name!! }) {
//                        Column {
//                            Row {
//                                GlideImage(model = it.icon, contentDescription = "이미지")
//                                Text(text = it.name?:"없음")
//                            }
//                            Text(text = it.tooltip?.element001?.value?.leftStr2?:"")
//                        }
//                    }
//                }
//            }
//        }
    }

}

// 투구 엘릭서, 초월, 상급재련, 만랩X
//fun testJson() {
//    val jsonString = "{\r\n  \"Element_000\": {\r\n    \"type\": \"NameTagBox\",\r\n    \"value\": \"<P ALIGN='CENTER'><FONT COLOR='#E3C7A1'>+20 사로잡힌 광기의 갈망 머리장식</FONT></P>\"\r\n  },\r\n  \"Element_001\": {\r\n    \"type\": \"ItemTitle\",\r\n    \"value\": {\r\n      \"bEquip\": 0,\r\n      \"leftStr0\": \"<FONT SIZE='12'><FONT COLOR='#E3C7A1'>고대 머리 방어구</FONT></FONT>\",\r\n      \"leftStr1\": \"<FONT SIZE='14'>품질</FONT>\",\r\n      \"leftStr2\": \"<FONT SIZE='14'>아이템 레벨 1640 (티어 3)</FONT>\",\r\n      \"qualityValue\": 96,\r\n      \"rightStr0\": \"<FONT SIZE='12'><FONT COLOR='#FFD200'>장착중</FONT></FONT>\",\r\n      \"slotData\": {\r\n        \"advBookIcon\": 0,\r\n        \"battleItemTypeIcon\": 0,\r\n        \"cardIcon\": false,\r\n        \"friendship\": 0,\r\n        \"iconGrade\": 6,\r\n        \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/sm_item/sm_item_01_166.png\",\r\n        \"imagePath\": \"\",\r\n        \"islandIcon\": 0,\r\n        \"petBorder\": 0,\r\n        \"rtString\": \"\",\r\n        \"seal\": false,\r\n        \"temporary\": 0,\r\n        \"town\": 0,\r\n        \"trash\": 0\r\n      }\r\n    }\r\n  },\r\n  \"Element_002\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='12'>바드 전용</FONT>\"\r\n  },\r\n  \"Element_003\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='12'>캐릭터 귀속됨</FONT>\"\r\n  },\r\n  \"Element_004\": {\r\n    \"type\": \"MultiTextBox\",\r\n    \"value\": \"|<font color='#C24B46'>거래 불가</font>\"\r\n  },\r\n  \"Element_005\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='14'><FONT COLOR='#A8EA6C'>[상급 재련]</FONT> <FONT COLOR='#FFD200'>15</FONT>단계</FONT>\"\r\n  },\r\n  \"Element_006\": {\r\n    \"type\": \"ItemPartBox\",\r\n    \"value\": {\r\n      \"Element_000\": \"<FONT COLOR='#A9D0F5'>기본 효과</FONT>\",\r\n      \"Element_001\": \"물리 방어력 +5802<BR>마법 방어력 +6445<BR>지능 +40270<BR>체력 +5487\"\r\n    }\r\n  },\r\n  \"Element_007\": {\r\n    \"type\": \"ItemPartBox\",\r\n    \"value\": {\r\n      \"Element_000\": \"<FONT COLOR='#A9D0F5'>추가 효과</FONT>\",\r\n      \"Element_001\": \"생명 활성력 +1291\"\r\n    }\r\n  },\r\n  \"Element_008\": {\r\n    \"type\": \"Progress\",\r\n    \"value\": {\r\n      \"forceValue\": \" \",\r\n      \"maximum\": 90000,\r\n      \"minimum\": 0,\r\n      \"title\": \"<FONT SIZE='12'><FONT COLOR='#A9D0F5'>현재 단계 재련 경험치</FONT></FONT>\",\r\n      \"value\": 0,\r\n      \"valueType\": 1\r\n    }\r\n  },\r\n  \"Element_009\": {\r\n    \"type\": \"IndentStringGroup\",\r\n    \"value\": {\r\n      \"Element_000\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": false,\r\n            \"contentStr\": \"지능 +5880\"\r\n          },\r\n          \"Element_001\": {\r\n            \"bPoint\": false,\r\n            \"contentStr\": \"<FONT COLOR='#FFD200'>모든 장비에 적용된 총 <img src='emoticon_Transcendence_Grade' width='18' height='18' vspace ='-4'></img>102개</FONT>\"\r\n          },\r\n          \"Element_002\": {\r\n            \"bPoint\": false,\r\n            \"contentStr\": \"<img src='emoticon_Transcendence_Grade' width='18' height='18' vspace ='-4'></img><font color='#ffffff'>5</font> - <font color='#ffffff'>장착중인 모든 장비의 초월 등급 당 최대 생명력이 <FONT COLOR='#99ff99'>80</FONT>씩, 자신의 공격력에 기반한 파티 공격력 버프 효과가 <FONT COLOR='#99ff99'>0.01%</FONT>씩 증가합니다.</font>\"\r\n          },\r\n          \"Element_003\": {\r\n            \"bPoint\": false,\r\n            \"contentStr\": \"<img src='emoticon_Transcendence_Grade' width='18' height='18' vspace ='-4'></img><font color='#ffffff'>10</font> - <font color='#ffffff'>장착중인 모든 장비의 초월 등급 당 힘, 민첩, 지능이 <FONT COLOR='#99ff99'>55</FONT>씩, 자신의 공격력에 기반한 파티 공격력 버프 효과가 <FONT COLOR='#99ff99'>0.01%</FONT>씩 증가합니다.</font>\"\r\n          },\r\n          \"Element_004\": {\r\n            \"bPoint\": false,\r\n            \"contentStr\": \"<img src='emoticon_Transcendence_Grade' width='18' height='18' vspace ='-4'></img><font color='#ffffff'>15</font> - <font color='#ffffff'>장착중인 모든 장비의 초월 등급 당 무기 공격력이 <FONT COLOR='#99ff99'>14</FONT>씩, 자신의 공격력에 기반한 파티 공격력 버프 효과가 <FONT COLOR='#99ff99'>0.01%</FONT>씩 증가합니다.</font>\"\r\n          },\r\n          \"Element_005\": {\r\n            \"bPoint\": false,\r\n            \"contentStr\": \"<img src='emoticon_Transcendence_Grade' width='18' height='18' vspace ='-4'></img><font color='#ffffff'>20</font> - <font color='#ffffff'>장착중인 모든 장비의 초월 등급 당 공격력이 <FONT COLOR='#99ff99'>6</FONT>씩, 자신의 공격력에 기반한 파티 공격력 버프 효과가 <FONT COLOR='#99ff99'>0.01%</FONT>씩 증가합니다.</font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<FONT COLOR='#FF9632'>[초월]</FONT> <FONT COLOR='#FFD200'>7</FONT>단계 <img src='emoticon_Transcendence_Grade' width='18' height='18' vspace ='-4'></img>20\"\r\n      }\r\n    }\r\n  },\r\n  \"Element_010\": {\r\n    \"type\": \"IndentStringGroup\",\r\n    \"value\": {\r\n      \"Element_000\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<FONT color='#FFD200'>[투구]</FONT> 진군 (질서) <FONT color='#FFD200'>Lv.4</FONT><br>자신의 공격력에 기반한 파티 공격력 버프 효과 +3%<BR>최대 생명력 +3750<BR>'진군 (혼돈)' 효과와 함께 활성화 시, '진군' 연성 추가 효과 발동\"\r\n          },\r\n          \"Element_001\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<FONT color='#FFD200'>[공용]</FONT> 무기 공격력 <FONT color='#FFD200'>Lv.5</FONT><br>무기 공격력 +1480\"\r\n          }\r\n        },\r\n        \"topStr\": \"<FONT SIZE='12' COLOR='#A9D0F5'>엘릭서 효과</FONT><br><font color='#91fe02'><FONT size='12'>지혜의 엘릭서</FONT></font>\"\r\n      }\r\n    }\r\n  },\r\n  \"Element_011\": {\r\n    \"type\": \"IndentStringGroup\",\r\n    \"value\": {\r\n      \"Element_000\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font color='#ffffff'><font color='#FFD200'>1단계 : <FONT size='12'>지혜의 엘릭서</FONT> 레벨 합 35</font><br>아군에게 보호 효과 사용 시, 자신의 <FONT color='#FFFFAC'>8</FONT>m 이내에 파티 전체에게 이로운 효과를 부여하는 에테르를 생성한다.<BR>(발동 재사용 대기시간 <FONT color='#FFFFAC'>10</FONT>초)<BR>진군 에테르: <FONT color='#FFFFAC'>15</FONT>초 간 무기 공격력 <FONT color='#D4FF88'>2230</FONT> 증가</font>\"\r\n          },\r\n          \"Element_001\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font color='#ffffff'><font color='#FFD200'>2단계 : <FONT size='12'>지혜의 엘릭서</FONT> 레벨 합 40</font><br>'진군 에테르' 생성 시, <FONT color='#FFFFAC'>15</FONT>초간 자신의 이동속도가 <FONT color='#D4FF88'>8%</FONT> 증가하고, 자신의 공격력에 기반한 파티 공격력 버프 효과가 <FONT color='#D4FF88'>6%</FONT> 증가한다.</font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<FONT SIZE='12' COLOR='#A9D0F5'>연성 추가 효과</FONT><br><FONT SIZE='12' color='#91FE02'>진군 (2단계)</FONT>\"\r\n      }\r\n    }\r\n  },\r\n  \"Element_012\": {\r\n    \"type\": \"ItemPartBox\",\r\n    \"value\": {\r\n      \"Element_000\": \"<FONT COLOR='#A9D0F5'>세트 효과 레벨</FONT>\",\r\n      \"Element_001\": \"갈망 <FONT COLOR='#FFD200'>Lv.3</FONT>\"\r\n    }\r\n  },\r\n  \"Element_013\": {\r\n    \"type\": \"SetItemGroup\",\r\n    \"value\": {\r\n      \"firstMsg\": \"<font size='14'><font color='#91fe02'>갈망</font></font>\",\r\n      \"itemData\": {\r\n        \"Element_000\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_11.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_001\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_12.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_002\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_13.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_003\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_14.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_004\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_15.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_005\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_16.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        }\r\n      }\r\n    }\r\n  },\r\n  \"Element_014\": {\r\n    \"type\": \"IndentStringGroup\",\r\n    \"value\": {\r\n      \"Element_000\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font size='12'><font color='#ffffff'>공격 적중 시 '전투의 축복' 효과를 획득하여 <FONT COLOR='#99ff99'>16</FONT>초 동안 자신의 이동속도 및 공격 적중 시 획득하는 아이덴티티 획득량이 <FONT COLOR='#99ff99'>20%</FONT> 증가한다.  (재사용 대기시간 <FONT COLOR='#99ff99'>0.5</FONT>초)</font></font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<font size='14'><font color='#91fe02'>2 세트 효과<BR>[<font size='14'><font color='#aaaaaa'>Lv.1 / Lv.2 / <FONT COLOR='#FFD200'>Lv.3</FONT></font></font>]</font></font>\"\r\n      },\r\n      \"Element_001\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font size='12'><font color='#ffffff'>'전투의 축복' 효과가 지속되는 동안 자신의 <FONT COLOR='#99ff99'>8</FONT>m 이내의 다른 파티원에게 매 초 마다 <FONT COLOR='#99ff99'>16</FONT>초 동안 지속되는 '기민함' 효과를 부여한다.<BR>'기민함' : 공격 및 이동속도 <FONT COLOR='#99ff99'>12%</FONT> 증가</font></font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<font size='14'><font color='#91fe02'>4 세트 효과<BR>[<font size='14'><font color='#aaaaaa'>Lv.1 / Lv.2 / <FONT COLOR='#FFD200'>Lv.3</FONT></font></font>]</font></font>\"\r\n      },\r\n      \"Element_002\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font size='12'><font color='#ffffff'>'전투의 축복' 효과가 지속되는 동안 자신의 <FONT COLOR='#99ff99'>8</FONT>m 이내의 다른 파티원에게 매 초 마다 <FONT COLOR='#99ff99'>16</FONT>초 동안 지속되는 '민첩' 효과를 부여한다.<BR>'민첩' : 적에게 주는 추가 피해가 <FONT COLOR='#99ff99'>12%</FONT> 증가한다.</font></font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<font size='14'><font color='#91fe02'>6 세트 효과<BR>[<font size='14'><font color='#aaaaaa'>Lv.1 / Lv.2 / <FONT COLOR='#FFD200'>Lv.3</FONT></font></font>]</font></font>\"\r\n      }\r\n    }\r\n  },\r\n  \"Element_015\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='12'><FONT COLOR='#C24B46'>분해불가</FONT></FONT>\"\r\n  },\r\n  \"Element_016\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<Font color='#5FD3F1'>[세트 업그레이드] 대도시 - 세트 장비 관리</font>\"\r\n  },\r\n  \"Element_017\": {\r\n    \"type\": \"ShowMeTheMoney\",\r\n    \"value\": \"<FONT SIZE='12'><FONT COLOR='#FFFFFF'>내구도 <FONT COLOR='#FFFFFF'>64 / 66</FONT></FONT></FONT>|\"\r\n  }\r\n}"
//    val gson = Gson()
//
//    val result = gson.fromJson(jsonString, HeadgearTooltipETSX::class.java)
//
//    val level = result.element001?.value?.leftStr2?:"null"
//    val preLevel = level.substringAfter("레벨 ").substringBefore(" (티어 3)")
//
//    val quality = result.element001?.value?.qualityValue?:0
//    val iconGrade = result.element001?.value?.slotData?.iconGrade?:0
//    val icon = result.element001?.value?.slotData?.iconPath?:"null"
//
//    val higherLevel = result.element005?.value?:"null"
//    val preHigherLevel = higherLevel.substringAfterLast("<FONT COLOR='#FFD200'>").substringBefore("</FONT>")
//
//    val transcendence = result.element009?.value?.element000?.contentStr?.element001?.contentStr?:"null"
//    val preTr = transcendence.substringAfter("</img>").substringBefore("개</FONT>")
//
//    val trLevel = result.element009?.value?.element000?.topStr?:"null"
//    val preTrLv = trLevel.substringAfter("<FONT COLOR='#FFD200'>").substringBefore("</FONT>")
//
//    val elixirSet = result.element011?.value?.element000?.topStr?:"null"
//    val preElixirSet = elixirSet.substringAfter("<FONT SIZE='12' color='#91FE02'>").substringBefore("</FONT>")
//
//    val elixir1 = result.element010?.value?.element000?.contentStr?.element000?.contentStr?:"null"
//    val preElixir1 = elixir1.substringAfter("</FONT> ").substringBefore(" <FONT color='#FFD200'>") + " " + elixir1.substringAfter("<FONT color='#FFD200'>Lv.").substringBefore("</FONT>")
//
//    val elixir2 = result.element010?.value?.element000?.contentStr?.element001?.contentStr?:"null"
//    val preElixir2 = elixir2.substringAfter("</FONT> ").substringBefore(" <FONT color='#FFD200'>") + " " + elixir2.substringAfter("<FONT color='#FFD200'>Lv.").substringBefore("</FONT>")
//
//    val setOption = result.element012?.value?.element001?:"null"
//    val preSetOp = setOption.substringBefore(" <FONT COLOR='#FFD200'>") + " " + setOption.substringAfter("<FONT COLOR='#FFD200'>Lv.").substringBefore("</FONT>")
//
//    Log.d("템랩", preLevel)
//    Log.d("품질", "$quality")
//    Log.d("등급", "$iconGrade")
//    Log.d("아이콘", icon)
//    Log.d("상급재련", preHigherLevel)
//    Log.d("초월총렙", preTr)
//    Log.d("초월단계", preTrLv)
//    Log.d("엘릭서효과", preElixirSet)
//    Log.d("엘릭서1", preElixir1)
//    Log.d("엘릭서2", preElixir2)
//    Log.d("세트옵션", preSetOp)
//}

// 투구 엘릭서만
fun testJson() {
    val jsonString = "{\r\n  \"Element_000\": {\r\n    \"type\": \"NameTagBox\",\r\n    \"value\": \"<P ALIGN='CENTER'><FONT COLOR='#E3C7A1'>+17 가라앉은 욕망의 배신 머리장식</FONT></P>\"\r\n  },\r\n  \"Element_001\": {\r\n    \"type\": \"ItemTitle\",\r\n    \"value\": {\r\n      \"bEquip\": 0,\r\n      \"leftStr0\": \"<FONT SIZE='12'><FONT COLOR='#E3C7A1'>고대 머리 방어구</FONT></FONT>\",\r\n      \"leftStr1\": \"<FONT SIZE='14'>품질</FONT>\",\r\n      \"leftStr2\": \"<FONT SIZE='14'>아이템 레벨 1610 (티어 3)</FONT>\",\r\n      \"qualityValue\": 92,\r\n      \"rightStr0\": \"<FONT SIZE='12'><FONT COLOR='#FFD200'>장착중</FONT></FONT>\",\r\n      \"slotData\": {\r\n        \"advBookIcon\": 0,\r\n        \"battleItemTypeIcon\": 0,\r\n        \"cardIcon\": false,\r\n        \"friendship\": 0,\r\n        \"iconGrade\": 6,\r\n        \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/ifm_item/ifm_item_59.png\",\r\n        \"imagePath\": \"\",\r\n        \"islandIcon\": 0,\r\n        \"petBorder\": 0,\r\n        \"rtString\": \"\",\r\n        \"seal\": false,\r\n        \"temporary\": 0,\r\n        \"town\": 0,\r\n        \"trash\": 0\r\n      }\r\n    }\r\n  },\r\n  \"Element_002\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='12'>브레이커 전용</FONT>\"\r\n  },\r\n  \"Element_003\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='12'>캐릭터 귀속됨</FONT>\"\r\n  },\r\n  \"Element_004\": {\r\n    \"type\": \"MultiTextBox\",\r\n    \"value\": \"|<font color='#C24B46'>거래 불가</font>\"\r\n  },\r\n  \"Element_005\": {\r\n    \"type\": \"ItemPartBox\",\r\n    \"value\": {\r\n      \"Element_000\": \"<FONT COLOR='#A9D0F5'>기본 효과</FONT>\",\r\n      \"Element_001\": \"물리 방어력 +5259<BR>마법 방어력 +5843<BR>힘 +33131<BR>체력 +4885\"\r\n    }\r\n  },\r\n  \"Element_006\": {\r\n    \"type\": \"ItemPartBox\",\r\n    \"value\": {\r\n      \"Element_000\": \"<FONT COLOR='#A9D0F5'>추가 효과</FONT>\",\r\n      \"Element_001\": \"생명 활성력 +1185\"\r\n    }\r\n  },\r\n  \"Element_007\": {\r\n    \"type\": \"Progress\",\r\n    \"value\": {\r\n      \"forceValue\": \" \",\r\n      \"maximum\": 60000,\r\n      \"minimum\": 0,\r\n      \"title\": \"<FONT SIZE='12'><FONT COLOR='#A9D0F5'>현재 단계 재련 경험치</FONT></FONT>\",\r\n      \"value\": 0,\r\n      \"valueType\": 1\r\n    }\r\n  },\r\n  \"Element_008\": {\r\n    \"type\": \"IndentStringGroup\",\r\n    \"value\": {\r\n      \"Element_000\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<FONT color='#FFD200'>[공용]</FONT> 힘 <FONT color='#FFD200'>Lv.4</FONT><br>힘 +4050\"\r\n          }\r\n        },\r\n        \"topStr\": \"<FONT SIZE='12' COLOR='#A9D0F5'>엘릭서 효과</FONT><br><font color='#91fe02'><FONT size='12'>지혜의 엘릭서</FONT></font>\"\r\n      }\r\n    }\r\n  },\r\n  \"Element_009\": {\r\n    \"type\": \"ItemPartBox\",\r\n    \"value\": {\r\n      \"Element_000\": \"<FONT COLOR='#A9D0F5'>세트 효과 레벨</FONT>\",\r\n      \"Element_001\": \"배신 <FONT COLOR='#FFD200'>Lv.3</FONT>\"\r\n    }\r\n  },\r\n  \"Element_010\": {\r\n    \"type\": \"SetItemGroup\",\r\n    \"value\": {\r\n      \"firstMsg\": \"<font size='14'><font color='#91fe02'>배신</font></font>\",\r\n      \"itemData\": {\r\n        \"Element_000\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_11.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_001\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_12.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_002\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_13.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_003\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_14.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_004\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_15.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        },\r\n        \"Element_005\": {\r\n          \"label\": \" <FONT COLOR='#FFD200'>Lv.3</FONT>\",\r\n          \"slotData\": {\r\n            \"iconGrade\": 10,\r\n            \"iconPath\": \"https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_16.png\",\r\n            \"imagePath\": \"\"\r\n          }\r\n        }\r\n      }\r\n    }\r\n  },\r\n  \"Element_011\": {\r\n    \"type\": \"IndentStringGroup\",\r\n    \"value\": {\r\n      \"Element_000\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font size='12'><font color='#ffffff'>공격 적중 시 자신만 획득 가능한 강력한 에테르 중 하나를 <FONT COLOR='#99ff99'>3</FONT>m 이내에 생성한다.(재사용 대기시간 <FONT COLOR='#99ff99'>1</FONT>분)<BR>에테르 생성 재사용 대기시간이 <FONT COLOR='#99ff99'>5</FONT>초 감소한다.</font></font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<font size='14'><font color='#91fe02'>2 세트 효과<BR>[<font size='14'><font color='#aaaaaa'>Lv.1 / Lv.2 / <FONT COLOR='#FFD200'>Lv.3</FONT></font></font>]</font></font>\"\r\n      },\r\n      \"Element_001\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font size='12'><font color='#ffffff'>강력한 에테르를 하나 더 생성한다. 동일한 에테르 획득 시 몬스터에게 주는 피해가 획득한 개수 만큼 증가한다. 에테르 생성 재사용 대기시간이 <FONT COLOR='#99ff99'>6</FONT>초 감소한다.</font></font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<font size='14'><font color='#91fe02'>4 세트 효과<BR>[<font size='14'><font color='#aaaaaa'>Lv.1 / Lv.2 / <FONT COLOR='#FFD200'>Lv.3</FONT></font></font>]</font></font>\"\r\n      },\r\n      \"Element_002\": {\r\n        \"contentStr\": {\r\n          \"Element_000\": {\r\n            \"bPoint\": true,\r\n            \"contentStr\": \"<font size='12'><font color='#ffffff'>최대 에테르를 <FONT COLOR='#99ff99'>3</FONT>개 생성하며 서로 다른 <FONT COLOR='#99ff99'>3</FONT>개의 에테르 효과가 활성될 때 '초월' 효과가 발동된다.<BR>에테르 생성 재사용 대기시간이 <FONT COLOR='#99ff99'>7</FONT>초 감소한다.<BR>'초월' : 공격속도 <FONT COLOR='#99ff99'>40%</FONT> 증가하고 이동기 및 각성기를 제외한 스킬 재사용 대기시간 <FONT COLOR='#99ff99'>50%</FONT> 감소된다.<BR>일정 시간마다 모든 디버프 제거, 마나 및 아이덴티티 게이지를 <FONT COLOR='#99ff99'>100%</FONT> 회복한다.</font></font>\"\r\n          }\r\n        },\r\n        \"topStr\": \"<font size='14'><font color='#91fe02'>6 세트 효과<BR>[<font size='14'><font color='#aaaaaa'>Lv.1 / Lv.2 / <FONT COLOR='#FFD200'>Lv.3</FONT></font></font>]</font></font>\"\r\n      }\r\n    }\r\n  },\r\n  \"Element_012\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<FONT SIZE='12'><FONT COLOR='#C24B46'>분해불가</FONT></FONT>\"\r\n  },\r\n  \"Element_013\": {\r\n    \"type\": \"SingleTextBox\",\r\n    \"value\": \"<Font color='#5FD3F1'>[세트 업그레이드] 대도시 - 세트 장비 관리</font>\"\r\n  },\r\n  \"Element_014\": {\r\n    \"type\": \"ShowMeTheMoney\",\r\n    \"value\": \"<FONT SIZE='12'><FONT COLOR='#FFFFFF'>내구도 <FONT COLOR='#FFFFFF'>61 / 66</FONT></FONT></FONT>|\"\r\n  }\r\n}"
    val gson = Gson()

    val result = gson.fromJson(jsonString, HeadgearTooltipEX::class.java)

    val level = result.element001?.value?.leftStr2?:"null"
    val preLevel = level.substringAfter("레벨 ").substringBefore(" (티어 3)")

    val quality = result.element001?.value?.qualityValue?:0
    val iconGrade = result.element001?.value?.slotData?.iconGrade?:0
    val icon = result.element001?.value?.slotData?.iconPath?:"null"

    val elixirSet = result.element009?.value?.element000?.topStr?:"null"
    val preElixirSet = elixirSet.substringAfter("<FONT SIZE='12' color='#91FE02'>").substringBefore("</FONT>")

    val elixir1 = result.element008?.value?.element000?.contentStr?.element000?.contentStr?:"null"
    val preElixir1 = elixir1.substringAfter("</FONT> ").substringBefore(" <FONT color='#FFD200'>") + " " + elixir1.substringAfter("<FONT color='#FFD200'>Lv.").substringBefore("</FONT>")

    val elixir2 = result.element008?.value?.element000?.contentStr?.element001?.contentStr?:"null"
    val preElixir2 = elixir2.substringAfter("</FONT> ").substringBefore(" <FONT color='#FFD200'>") + " " + elixir2.substringAfter("<FONT color='#FFD200'>Lv.").substringBefore("</FONT>")

    val setOption = result.element010?.value?.element001?:"null"
    val preSetOp = setOption.substringBefore(" <FONT COLOR='#FFD200'>") + " " + setOption.substringAfter("<FONT COLOR='#FFD200'>Lv.").substringBefore("</FONT>")

    Log.d("템랩", preLevel)
    Log.d("품질", "$quality")
    Log.d("등급", "$iconGrade")
    Log.d("아이콘", icon)
    Log.d("엘릭서효과", preElixirSet)
    Log.d("엘릭서1", preElixir1)
    Log.d("엘릭서2", preElixir2)
    Log.d("세트옵션", preSetOp)
}
