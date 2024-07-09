package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.raid.Gold
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class EpicRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    BEHEMOTH(
        boss = Raid.Name.BEHEMOTH,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.BEHEMOTH,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.BEHEMOTH
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.BEHEMOTH,
            Raid.Difficulty.HARD to Gold.Clear.Hard.BEHEMOTH
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
    private val seeMoreGold = EpicRaid.BEHEMOTH.getBossInfo(Raid.Difficulty.NORMAL).first + EpicRaid.BEHEMOTH.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = EpicRaid.BEHEMOTH.getBossInfo(Raid.Difficulty.NORMAL).second + EpicRaid.BEHEMOTH.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.epic?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.epic?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?: Raid.Difficulty.KR_NORMAL
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

    private val getTwoPhase = character?.checkList?.epic?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?: Raid.Difficulty.KR_NORMAL
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