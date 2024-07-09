package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class KazerothRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    ECHIDNA(
        boss = Raid.Name.ECHIDNA,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(2200, 3400),
            Raid.Difficulty.HARD to listOf(2800, 4100)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(5000, 9500),
            Raid.Difficulty.HARD to listOf(6000, 12500)
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
    private val seeMoreGold = KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.NORMAL).first + KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.NORMAL).second + KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.kazeroth?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.kazeroth?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[2],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2]
    )

    private val getTwoPhase = character?.checkList?.kazeroth?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold
    }

    fun onShowChecked() {
        onePhase.onShowChecked()
        twoPhase.onShowChecked()
    }

}



