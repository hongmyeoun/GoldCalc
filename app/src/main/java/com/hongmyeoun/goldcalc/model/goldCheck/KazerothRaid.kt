package com.hongmyeoun.goldcalc.model.goldCheck

import com.hongmyeoun.goldcalc.model.roomDB.Character

enum class KazerothRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    ECHIDNA(
        boss = "에키드나",
        seeMoreGold = mapOf(
            "normal" to listOf(2200, 3400),
            "hard" to listOf(2800, 4100)
        ),
        clearGold = mapOf(
            "normal" to listOf(5000, 9500),
            "hard" to listOf(6000, 12500)
        )
    );

    fun getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}

class KazerothRaidModel(character: Character?) {
    val echidna: Echidna = if (character != null) {
        Echidna(character)
    } else {
        Echidna(null)
    }
}

class Echidna(character: Character?) {
    val name = KazerothRaid.ECHIDNA.boss
    private val seeMoreGold = KazerothRaid.ECHIDNA.getBossInfo("normal").first + KazerothRaid.ECHIDNA.getBossInfo("hard").first
    private val clearGold = KazerothRaid.ECHIDNA.getBossInfo("normal").second + KazerothRaid.ECHIDNA.getBossInfo("hard").second

    private val getOnePhase = character?.checkList?.kazeroth?.get(0)?.phases?.get(0)

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

    private val getTwoPhase = character?.checkList?.kazeroth?.get(0)?.phases?.get(1)

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

}



