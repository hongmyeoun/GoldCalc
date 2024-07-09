package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.raid.Gold
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class AbyssDungeon(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    KAYANGEL(
        boss = Raid.Name.KAYANGEL,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.KAYANGEL,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.KAYANGEL,
            Raid.Difficulty.SOLO to Gold.SeeMore.Solo.KAYANGEL
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.KAYANGEL,
            Raid.Difficulty.HARD to Gold.Clear.Hard.KAYANGEL,
            Raid.Difficulty.SOLO to Gold.Clear.Solo.KAYANGEL
        )
    ),
    IVORY_TOWER(
        boss = Raid.Name.IVORY_TOWER,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.IVORY_TOWER,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.IVORY_TOWER,
            Raid.Difficulty.SOLO to Gold.SeeMore.Solo.IVORY_TOWER
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.IVORY_TOWER,
            Raid.Difficulty.HARD to Gold.Clear.Hard.IVORY_TOWER,
            Raid.Difficulty.SOLO to Gold.Clear.Solo.IVORY_TOWER
        )
    );

    fun getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}

class AbyssDungeonModel(character: Character?) {
    val kayangel: Kayangel = if (character != null) {
        Kayangel(character)
    } else {
        Kayangel(null)
    }

    val ivoryTower: IvoryTower = if (character != null) {
        IvoryTower(character)
    } else {
        IvoryTower(null)
    }
}

class Kayangel(character: Character?) {
    val name = AbyssDungeon.KAYANGEL.boss
    private val seeMoreGold = AbyssDungeon.KAYANGEL.getBossInfo(Raid.Difficulty.NORMAL).first + AbyssDungeon.KAYANGEL.getBossInfo(Raid.Difficulty.HARD).first + AbyssDungeon.KAYANGEL.getBossInfo(
        Raid.Difficulty.SOLO).first
    private val clearGold = AbyssDungeon.KAYANGEL.getBossInfo(Raid.Difficulty.NORMAL).second + AbyssDungeon.KAYANGEL.getBossInfo(Raid.Difficulty.HARD).second + AbyssDungeon.KAYANGEL.getBossInfo(
        Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.abyssDungeon?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.abyssDungeon?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        seeMoreGoldS = seeMoreGold[6],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3],
        clearGoldS = clearGold[6]
    )

    private val getTwoPhase = character?.checkList?.abyssDungeon?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        seeMoreGoldS = seeMoreGold[7],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4],
        clearGoldS = clearGold[7]
    )

    private val getThreePhase = character?.checkList?.abyssDungeon?.get(0)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?: Raid.Difficulty.KR_NORMAL
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[5],
        seeMoreGoldS = seeMoreGold[8],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[5],
        clearGoldS = clearGold[8]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold
    }

    fun onShowChecked() {
        onePhase.onShowChecked()
        twoPhase.onShowChecked()
        threePhase.onShowChecked()
    }
}

class IvoryTower(character: Character?) {
    val name = AbyssDungeon.IVORY_TOWER.boss
    private val seeMoreGold = AbyssDungeon.IVORY_TOWER.getBossInfo(Raid.Difficulty.NORMAL).first + AbyssDungeon.IVORY_TOWER.getBossInfo(Raid.Difficulty.HARD).first + AbyssDungeon.IVORY_TOWER.getBossInfo(
        Raid.Difficulty.SOLO).first
    private val clearGold = AbyssDungeon.IVORY_TOWER.getBossInfo(Raid.Difficulty.NORMAL).second + AbyssDungeon.IVORY_TOWER.getBossInfo(Raid.Difficulty.HARD).second + AbyssDungeon.IVORY_TOWER.getBossInfo(
        Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.abyssDungeon?.get(1)?.isCheck?:false

    private val getOnePhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        seeMoreGoldS = seeMoreGold[6],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3],
        clearGoldS = clearGold[6]
    )

    private val getTwoPhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        seeMoreGoldS = seeMoreGold[7],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4],
        clearGoldS = clearGold[7]
    )

    private val getThreePhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?: Raid.Difficulty.KR_NORMAL
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[5],
        seeMoreGoldS = seeMoreGold[8],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[5],
        clearGoldS = clearGold[8]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold
    }

    fun onShowChecked() {
        onePhase.onShowChecked()
        twoPhase.onShowChecked()
        threePhase.onShowChecked()
    }
}