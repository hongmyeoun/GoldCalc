package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_ABILLITY_STONE_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_BRACELET_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_CHEST_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_EARRINGS_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_GLOVES_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_HEAD_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_NECKLACE_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_PANTS_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_RINGS_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_SHOULDER_ICON
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.NO_WEAPON_ICON
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.ABILITY_STONE
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.BRACELET
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.CHEST
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.EARRINGS
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.GLOVES
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.HEAD
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.NECKLACE
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.PANTS
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.RING
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.SHOULDER
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.WEAPON
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.AncientColor
import com.hongmyeoun.goldcalc.ui.theme.AncientMiddle
import com.hongmyeoun.goldcalc.ui.theme.BlueQual
import com.hongmyeoun.goldcalc.ui.theme.EsterColor
import com.hongmyeoun.goldcalc.ui.theme.EstherBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
import com.hongmyeoun.goldcalc.ui.theme.LegendaryColor
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.ui.theme.PurpleQual
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import com.hongmyeoun.goldcalc.ui.theme.YellowQual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EquipmentVM(characterEquipment: List<CharacterItem>) : ViewModel() {
    private val _accessoryAvgQuality = MutableStateFlow(0.0)
    val accessoryAvgQuality: StateFlow<Double> = _accessoryAvgQuality

    private val _setOption = MutableStateFlow(EquipmentConsts.NO_SETTING)
    val setOption: StateFlow<String> = _setOption

    private val _totalCombatStat = MutableStateFlow(0)
    val totalCombatStat: StateFlow<Int> = _totalCombatStat

    private val _showAccDialog = MutableStateFlow(false)
    val showAccDialog: StateFlow<Boolean> = _showAccDialog
    fun onAccDismissRequest() {
        _showAccDialog.value = false
    }

    fun onAccClicked() {
        _showAccDialog.value = true
    }

    private val _showBraDialog = MutableStateFlow(false)
    val showBraDialog: StateFlow<Boolean> = _showBraDialog
    fun onBraDismissRequest() {
        _showBraDialog.value = false
    }

    fun onBraClicked() {
        _showBraDialog.value = true
    }

    init {
        _accessoryAvgQuality.value = getAccessoryAvgQuality(characterEquipment)
        _setOption.value = getSetoption(characterEquipment)
        _totalCombatStat.value = getSumCombatStat(characterEquipment)
    }

    private fun getAccessoryAvgQuality(equipment: List<CharacterItem>): Double {
        val accessoryTotalQuality = equipment.filterIsInstance<CharacterAccessory>().map { it.itemQuality }
        return if (accessoryTotalQuality.isNotEmpty()) accessoryTotalQuality.average() else 0.0
    }

    private fun getSetoption(equipment: List<CharacterItem>): String {
        val setOptions = equipment.filterIsInstance<CharacterEquipment>().map { it.setOption }
        val setOptionGroups = setOptions.map {
            val parts = it.split(" ")
            val option = parts.dropLast(1).joinToString(" ")
            val level = parts.last().toIntOrNull() ?: 0 // 숫자로 변환이 안되면 0으로 처리
            option to level
        }.groupBy({ it.first }, { it.second })

        val formattedSetOptions = setOptionGroups.map { (option, levels) ->
            val count = levels.size
            val averageLevel = levels.average()

            val formattedLevel = if (averageLevel % 1 == 0.0) averageLevel.toInt().toString() else "%.1f".format(averageLevel)

            "$count$option Lv.$formattedLevel"
        }

        return formattedSetOptions.joinToString(", ")
    }

    private fun getSumCombatStat(equipment: List<CharacterItem>): Int {
        var combatStatOne = 0
        var combatStatTwo = 0

        equipment.filterIsInstance<CharacterAccessory>().forEach { accessory ->
            accessory.combatStat1?.let {
                combatStatOne += it.split(" ")[1].removePrefix("+").toIntOrNull() ?: 0
            }
            accessory.combatStat2?.let {
                combatStatTwo += it.split(" ")[1].removePrefix("+").toIntOrNull() ?: 0
            }
        }

        return combatStatOne + combatStatTwo
    }


    fun setOptionName(equipment: CharacterEquipment): String {
        return equipment.setOption.split(" ").first()
    }

    fun sumElixirLevel(equipment: List<CharacterItem>): Int {
        val sumFirst = equipment.filterIsInstance<CharacterEquipment>()
            .fastSumBy {
                if (it.elixirFirstLevel.isNotEmpty()) {
                    it.elixirFirstLevel.toInt()
                } else {
                    0
                }
            }

        val sumSecond = equipment.filterIsInstance<CharacterEquipment>()
            .fastSumBy {
                if (it.elixirSecondLevel.isNotEmpty()) {
                    it.elixirSecondLevel.toInt()
                } else {
                    0
                }
            }
        return sumFirst + sumSecond
    }

    fun elixirSetOption(equipment: List<CharacterItem>): String {
        val setOption = equipment.filterIsInstance<CharacterEquipment>().filter { it.type == "투구" }.map { it.elixirSetOption }
        return if (setOption.isEmpty()) EquipmentConsts.NO_SETTING else setOption[0]
    }

    fun sumTransLevel(equipment: List<CharacterItem>): Int {
        return equipment.filterIsInstance<CharacterEquipment>().fastSumBy { if (it.transcendenceTotal.isNotEmpty()) it.transcendenceTotal.toInt() else 0 }
    }

    fun avgTransLevel(equipment: List<CharacterItem>): String {
        val sumLevel = equipment.filterIsInstance<CharacterEquipment>().fastSumBy { if (it.transcendenceLevel.isNotEmpty()) it.transcendenceLevel.toInt() else 0 }
        val avg = sumLevel.toDouble() / 6.0
        return "%.1f".format(avg)
    }

    fun getItemBG(grade: String): Brush {
        val itemBG = when (grade) {
            EquipmentConsts.GRADE_ESTHER -> EstherBG
            EquipmentConsts.GRADE_ANCIENT -> AncientBG
            EquipmentConsts.GRADE_RELIC -> RelicBG
            else -> LegendaryBG
        }
        return itemBG
    }

    fun getGradeBG(grade: String, brightAncient: Boolean = true): Color {
        val itemBG = when (grade) {
            EquipmentConsts.GRADE_ESTHER -> EsterColor
            EquipmentConsts.GRADE_ANCIENT -> if (brightAncient) AncientColor else AncientMiddle
            EquipmentConsts.GRADE_RELIC -> RelicColor
            else -> LegendaryColor
        }
        return itemBG
    }

    fun getQualityColor(quality: String): Color {
        return if (quality.toInt() >= 100) {
            OrangeQual
        } else if (quality.toInt() in 90 until 100) {
            PurpleQual
        } else if (quality.toInt() in 70 until 90) {
            BlueQual
        } else if (quality.toInt() in 30 until 70) {
            GreenQual
        } else if (quality.toInt() in 10 until 30) {
            YellowQual
        } else {
            RedQual
        }
    }

    fun getElixirColor(level: String): Color {
        return when (level.toInt()) {
            5 -> RelicColor
            4 -> OrangeQual
            3 -> PurpleQual
            2 -> BlueQual
            else -> GreenQual
        }
    }

    fun getStoneColor(level: String): Color {
        return if (level.toInt() >= 9) {
            OrangeQual
        } else if (level.toInt() in 7 until 9) {
            PurpleQual
        } else if (level.toInt() in 5 until 7) {
            BlueQual
        } else {
            RedQual
        }
    }

    fun simplyEngravingName(engraving: String): String {
        val setOption = onlySetOption(engraving)
        val level = engraving.split(" ").last()
        return "$setOption $level"
    }

    fun onlySetOption(engraving: String): String {
        val regex = Regex("""^[^\d]+""")
        val match = regex.find(engraving)?.value?.trim() ?: ""
        return getSimpleEngraving(match)
    }

    fun grindEffectSize(input: String?): String {
        val inputSize = input?.split("\n")?.size
        return if (inputSize != null) "연마 +$inputSize" else "연마 없음"
    }

    fun arkPassiveEngLv(engLv: Int?): String? {
        return when (engLv) {
            6 -> { "Lv.1" }
            in 7..8 -> { "Lv.2" }
            9 -> { "Lv.3" }
            10 -> { "Lv.4" }
            else -> null
        }
    }

    fun arkPassiveEngPenLv(engLv: Int?): String? {
        return when (engLv) {
            in 5..6 -> { "Lv.1" }
            in 7..9 -> { "Lv.2" }
            10 -> { "Lv.3" }
            else -> null
        }
    }

    fun arkPoint(input: String): Int {
        val regex = "\\d+".toRegex()
        val match = regex.find(input)
        return match?.value?.toInt() ?: 0
    }

    fun nullEquipmentIcon(type: String): String {
        return when(type) {
            HEAD ->  NO_HEAD_ICON
            SHOULDER -> NO_SHOULDER_ICON
            CHEST -> NO_CHEST_ICON
            PANTS -> NO_PANTS_ICON
            GLOVES -> NO_GLOVES_ICON
            WEAPON -> NO_WEAPON_ICON
            NECKLACE -> NO_NECKLACE_ICON
            EARRINGS -> NO_EARRINGS_ICON
            RING -> NO_RINGS_ICON
            ABILITY_STONE -> NO_ABILLITY_STONE_ICON
            BRACELET -> NO_BRACELET_ICON
            else -> NO_HEAD_ICON
        }
    }

    fun getSimpleEngraving(engraving: String): String {
        val simpleName = mapOf(
            "결투의 대가" to "결대",
            "급소 타격" to "급타",
            "기습의 대가" to "기습",
            "돌격대장" to "돌대",
            "속전속결" to "속속",
            "슈퍼 차지" to "슈차",
            "아드레날린" to "아드",
            "에테르 포식자" to "에포",
            "예리한 둔기" to "예둔",
            "저주받은 인형" to "저받",
            "정기 흡수" to "정흡",
            "중갑 착용" to "중갑",
            "정밀 단도" to "정단",
            "질량 증가" to "질증",
            "최대 마나 증가" to "최마증",
            "타격의 대가" to "타대",
            "폭발물 전문가" to "폭전",
            "분노의 망치" to "분망",
            "중력 수련" to "중수",
            "광전사의 비기" to "비기",
            "고독한 기사" to "고기",
            "전투 태세" to "전태",
            "축복의 오라" to "축오",
            "세맥타통" to "세맥",
            "역천지체" to "역천",
            "수라의 길" to "수라",
            "권왕파천무" to "권왕",
            "일격필살" to "일격",
            "극의: 체술" to "체술",
            "충격 단련" to "충단",
            "사냥의 시간" to "사시",
            "피스메이커" to "피메",
            "강화 무기" to "강무",
            "포격 강화" to "포강",
            "화력 강화" to "화강",
            "진화의 유산" to "유산",
            "아르데타인의 기술" to "기술",
            "두 번째 동료" to "두동",
            "죽음의 습격" to "죽습",
            "절실한 구원" to "절구",
            "넘치는 교감" to "교감",
            "상급 소환사" to "상소",
            "황제의 칙령" to "황제",
            "황후의 은총" to "황후",
            "멈출 수 없는 충동" to "충동",
            "완벽한 억제" to "억제",
            "달의 소리" to "달소",
            "잔재된 기운" to "잔재",
            "그믐의 경계" to "그믐",
            "만월의 집행자" to "만월",
            "질풍노도" to "질풍",
            "공격력 감소" to "공감",
            "공격속도 감소" to "공속감",
            "이동속도 감소" to "이속감",
            "방어력 감소" to "방감",
        )
        return simpleName[engraving] ?: engraving
    }
}