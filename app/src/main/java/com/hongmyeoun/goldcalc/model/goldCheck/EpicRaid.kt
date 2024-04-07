package com.hongmyeoun.goldcalc.model.goldCheck

import com.hongmyeoun.goldcalc.model.roomDB.Character

enum class EpicRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    BEHEMOTH(
        boss = "베히모스",
        seeMoreGold = mapOf(
            "normal" to listOf(3100, 4900),
            "hard" to listOf(3100, 4900)
        ),
        clearGold = mapOf(
            "normal" to listOf(7000, 14500),
            "hard" to listOf(7000, 14500)
        )
    );

    fun getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}

class EpicRaidModel(character: Character?) {
    val behemoth: Behemoth = if (character != null) {
        Behemoth(character)
    } else {
        Behemoth(null)
    }
}

class Behemoth(character: Character?) {
    val name = EpicRaid.BEHEMOTH.boss
    private val seeMoreGold = EpicRaid.BEHEMOTH.getBossInfo("normal").first + EpicRaid.BEHEMOTH.getBossInfo("hard").first
    private val clearGold = EpicRaid.BEHEMOTH.getBossInfo("normal").second + EpicRaid.BEHEMOTH.getBossInfo("hard").second

    var isChecked = character?.checkList?.epic?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.epic?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[2],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2]
    )

    private val getTwoPhase = character?.checkList?.epic?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold
    }

}