package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.raid.Gold
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class ShadowRaid (
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    SERCA(
        boss = Raid.Name.SERCA,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.SERCA,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.SERCA,
            Raid.Difficulty.NIGHTMARE to Gold.SeeMore.Nightmare.SERCA,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.SERCA,
            Raid.Difficulty.HARD to Gold.Clear.Hard.SERCA,
            Raid.Difficulty.NIGHTMARE to Gold.Clear.Nightmare.SERCA,
        )
    );
    fun getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}

class ShadowRaidModel(character: Character?) {
    val serca: Serca = if (character != null) {
        Serca(character)
    } else {
        Serca(null)
    }
}

class Serca(character: Character?) {
    val name = ShadowRaid.SERCA.boss
    private val seeMoreGold = ShadowRaid.SERCA.getBossInfo(Raid.Difficulty.NORMAL).first + ShadowRaid.SERCA.getBossInfo(Raid.Difficulty.HARD).first + ShadowRaid.SERCA.getBossInfo(Raid.Difficulty.NIGHTMARE).first
    private val clearGold = ShadowRaid.SERCA.getBossInfo(Raid.Difficulty.NORMAL).second + ShadowRaid.SERCA.getBossInfo(Raid.Difficulty.HARD).second + ShadowRaid.SERCA.getBossInfo(Raid.Difficulty.NIGHTMARE).second

    val isChecked = character?.checkList?.shadow?.get(0)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.shadow?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        isShadowRaid = true,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[2],
        seeMoreGoldNM = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
        clearGoldNM = clearGold[4]
    )

    private val gettwoPhase = character?.checkList?.shadow?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = gettwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = gettwoPhase?.isClear ?: false
    private val twoPhaseMCheck = gettwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        isShadowRaid = true,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[3],
        seeMoreGoldNM = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3],
        clearGoldNM = clearGold[5]
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