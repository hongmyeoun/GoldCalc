package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.raid.Gold
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class KazerothRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    ECHIDNA(
        boss = Raid.Name.ECHIDNA,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.ECHIDNA,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.ECHIDNA
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.ECHIDNA,
            Raid.Difficulty.HARD to Gold.Clear.Hard.ECHIDNA
        )
    ),
    EGIR(
        boss = Raid.Name.EGIR,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.EGIR,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.EGIR
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.EGIR,
            Raid.Difficulty.HARD to Gold.Clear.Hard.EGIR
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

    val egir: Egir = if (character != null) {
        Egir(character)
    } else {
        Egir(null)
    }
}

class Echidna(character: Character?) {
    val name = KazerothRaid.ECHIDNA.boss
    private val seeMoreGold = KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.NORMAL).first + KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.NORMAL).second + KazerothRaid.ECHIDNA.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.kazeroth?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.kazeroth?.get(0)?.phases?.get(0)

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

    private val getTwoPhase = character?.checkList?.kazeroth?.get(0)?.phases?.get(1)

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

class Egir(character: Character?) {
    val name = KazerothRaid.EGIR.boss
    private val seeMoreGold = KazerothRaid.EGIR.getBossInfo(Raid.Difficulty.NORMAL).first + KazerothRaid.EGIR.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = KazerothRaid.EGIR.getBossInfo(Raid.Difficulty.NORMAL).second + KazerothRaid.EGIR.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.kazeroth?.get(1)?.isCheck?:false

    private val getOnePhase = character?.checkList?.kazeroth?.get(1)?.phases?.get(0)

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

    private val getTwoPhase = character?.checkList?.kazeroth?.get(1)?.phases?.get(1)

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




