package com.hongmyeoun.goldcalc.model.goldCheck

import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class AbyssDungeon(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    KAYANGEL(
        boss = "카양겔",
        seeMoreGold = mapOf(
            "normal" to listOf(600, 800, 1000),
            "hard" to listOf(700, 900, 1100)
        ),
        clearGold = mapOf(
            "normal" to listOf(1000, 1500, 2000),
            "hard" to listOf(1500, 2000, 3000)
        )
    ),
    IVORY_TOWER(
        boss = "상아탑",
        seeMoreGold = mapOf(
            "normal" to listOf(700, 800, 900, 1100),
            "hard" to listOf(1000, 1000, 1500, 2000)
        ),
        clearGold = mapOf(
            "normal" to listOf(1500, 1750, 2500, 3250),
            "hard" to listOf(2000, 2500, 4000, 6000)
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
    private val seeMoreGold = AbyssDungeon.KAYANGEL.getBossInfo("normal").first + AbyssDungeon.KAYANGEL.getBossInfo("hard").first
    private val clearGold = AbyssDungeon.KAYANGEL.getBossInfo("normal").second + AbyssDungeon.KAYANGEL.getBossInfo("hard").second

    var isChecked = character?.checkList?.abyssDungeon?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.abyssDungeon?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3]
    )

    private val getTwoPhase = character?.checkList?.abyssDungeon?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4]
    )

    private val getThreePhase = character?.checkList?.abyssDungeon?.get(0)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?:"노말"
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[5]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold
    }

}

class IvoryTower(character: Character?) {
    val name = AbyssDungeon.IVORY_TOWER.boss
    private val seeMoreGold = AbyssDungeon.IVORY_TOWER.getBossInfo("normal").first + AbyssDungeon.IVORY_TOWER.getBossInfo("hard").first
    private val clearGold = AbyssDungeon.IVORY_TOWER.getBossInfo("normal").second + AbyssDungeon.IVORY_TOWER.getBossInfo("hard").second

    var isChecked = character?.checkList?.abyssDungeon?.get(1)?.isCheck?:false

    private val getOnePhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[4]
    )

    private val getTwoPhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[5]
    )

    private val getThreePhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?:"노말"
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[6],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[6]
    )

    private val getFourPhase = character?.checkList?.abyssDungeon?.get(1)?.phases?.get(3)
    private val fourPhaseDifficulty = getFourPhase?.difficulty?:"노말"
    private val fourPhaseIsClear = getFourPhase?.isClear?:false
    private val fourPhaseMCheck = getFourPhase?.mCheck?:false

    val fourPhase = PhaseInfo(
        difficulty = fourPhaseDifficulty,
        isClearCheck = fourPhaseIsClear,
        moreCheck = fourPhaseMCheck,
        seeMoreGoldN = seeMoreGold[3],
        seeMoreGoldH = seeMoreGold[7],
        clearGoldN = clearGold[3],
        clearGoldH = clearGold[7]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold + fourPhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold + fourPhase.totalGold
    }

}