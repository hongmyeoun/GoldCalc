package com.hongmyeoun.goldcalc.model.goldCheck

import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class CommandBoss(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    VALTAN(
        boss = "발탄",
        seeMoreGold = mapOf(
            "normal" to listOf(300, 400),
            "hard" to listOf(450, 600)
        ),
        clearGold = mapOf(
            "normal" to listOf(500, 700),
            "hard" to listOf(700, 1100)
        )
    ),
    BIACKISS(
        boss = "비아키스",
        seeMoreGold = mapOf(
            "normal" to listOf(300, 450),
            "hard" to listOf(500, 650)
        ),
        clearGold = mapOf(
            "normal" to listOf(600, 1000),
            "hard" to listOf(900, 1500)
        )
    ),
    KOUKU_SATON(
        boss = "쿠크세이튼",
        seeMoreGold = mapOf(
            "normal" to listOf(300, 500, 600),
            "hard" to listOf(300, 500, 600)
        ),
        clearGold = mapOf(
            "normal" to listOf(600, 900, 1500),
            "hard" to listOf(600, 900, 1500)
        )
    ),
    ABRELSHUD(
        boss = "아브렐슈드",
        seeMoreGold = mapOf(
            "normal" to listOf(400, 600, 800, 1500),
            "hard" to listOf(700, 900, 1100, 1800)
        ),
        clearGold = mapOf(
            "normal" to listOf(1500, 1500, 1500, 2500),
            "hard" to listOf(2000, 2000, 2000, 3000)
        )
    ),
    ILLIAKAN(
        boss = "일리아칸",
        seeMoreGold = mapOf(
            "normal" to listOf(900, 1100, 1500),
            "hard" to listOf(1200, 1400, 1900)
        ),
        clearGold = mapOf(
            "normal" to listOf(1500, 2000, 4000),
            "hard" to listOf(1750, 2500, 5750)
        )
    ),
    KAMEN(
        boss = "카멘",
        seeMoreGold = mapOf(
            "normal" to listOf(1500, 1800, 2500, 0),
            "hard" to listOf(2000, 2400, 2800, 3600)
        ),
        clearGold = mapOf(
            "normal" to listOf(3500, 4000, 5500, 0),
            "hard" to listOf(5000, 6000, 9000, 21000)
        )
    );

    fun getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}

class CommandBossModel(character: Character?) {
    val valtan: Valtan = if (character != null) {
        Valtan(character)
    } else {
        Valtan(null)
    }

    val biackiss: Biackiss = if (character != null) {
        Biackiss(character)
    } else {
        Biackiss(null)
    }

    val koukuSaton: KoukuSaton = if (character != null) {
        KoukuSaton(character)
    } else {
        KoukuSaton(null)
    }

    val abrelshud: Abrelshud = if (character != null) {
        Abrelshud(character)
    } else {
        Abrelshud(null)
    }

    val illiakan: Illiakan = if (character != null) {
        Illiakan(character)
    } else {
        Illiakan(null)
    }

    val kamen: Kamen = if (character != null) {
        Kamen(character)
    } else {
        Kamen(null)
    }
}

class Valtan(character: Character?) {
    var name = CommandBoss.VALTAN.boss
    private val seeMoreGold = CommandBoss.VALTAN.getBossInfo("normal").first + CommandBoss.VALTAN.getBossInfo("hard").first
    private val clearGold =  CommandBoss.VALTAN.getBossInfo("normal").second + CommandBoss.VALTAN.getBossInfo("hard").second

    var isChecked = character?.checkList?.command?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
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

    private val getTwoPhase = character?.checkList?.command?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
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

class Biackiss(character: Character?) {
    var name = CommandBoss.BIACKISS.boss
    private val seeMoreGold = CommandBoss.BIACKISS.getBossInfo("normal").first + CommandBoss.BIACKISS.getBossInfo("hard").first
    private val clearGold = CommandBoss.BIACKISS.getBossInfo("normal").second + CommandBoss.BIACKISS.getBossInfo("hard").second

    var isChecked = character?.checkList?.command?.get(1)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(1)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
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

    private val getTwoPhase = character?.checkList?.command?.get(1)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
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

class KoukuSaton(character: Character?) {
    var name = CommandBoss.KOUKU_SATON.boss
    private val seeMoreGold = CommandBoss.KOUKU_SATON.getBossInfo("normal").first + CommandBoss.KOUKU_SATON.getBossInfo("hard").first
    private val clearGold = CommandBoss.KOUKU_SATON.getBossInfo("normal").second + CommandBoss.KOUKU_SATON.getBossInfo("hard").second

    var isChecked = character?.checkList?.command?.get(2)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(2)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3]
    )

    private val getTwoPhase = character?.checkList?.command?.get(2)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4]
    )

    private val getThreePhase = character?.checkList?.command?.get(2)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?:"노말"
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[5]
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

class Abrelshud(character: Character?) {
    var name = CommandBoss.ABRELSHUD.boss
    private val seeMoreGold = CommandBoss.ABRELSHUD.getBossInfo("normal").first + CommandBoss.ABRELSHUD.getBossInfo("hard").first
    private val clearGold = CommandBoss.ABRELSHUD.getBossInfo("normal").second + CommandBoss.ABRELSHUD.getBossInfo("hard").second

    var isChecked = character?.checkList?.command?.get(3)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(3)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[4]
    )

    private val getTwoPhase = character?.checkList?.command?.get(3)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[5]
    )

    private val getThreePhase = character?.checkList?.command?.get(3)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?:"노말"
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[6],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[6]
    )

    private val getFourPhase = character?.checkList?.command?.get(3)?.phases?.get(3)
    private val fourPhaseDifficulty = getFourPhase?.difficulty?:"노말"
    private val fourPhaseIsClear = getFourPhase?.isClear?:false
    private val fourPhaseMCheck = getFourPhase?.mCheck?:false

    val fourPhase = PhaseInfo(
        difficulty = fourPhaseDifficulty,
        isClearCheck = fourPhaseIsClear,
        moreCheck = fourPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[3],
        seeMoreGoldH = seeMoreGold[7],
        clearGoldN = clearGold[3],
        clearGoldH = clearGold[7]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold + fourPhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold + fourPhase.totalGold
    }

    fun onShowChecked() {
        onePhase.onShowChecked()
        twoPhase.onShowChecked()
        threePhase.onShowChecked()
        fourPhase.onShowChecked()
    }
}

class Illiakan(character: Character?) {
    var name = CommandBoss.ILLIAKAN.boss
    private val seeMoreGold = CommandBoss.ILLIAKAN.getBossInfo("normal").first + CommandBoss.ILLIAKAN.getBossInfo("hard").first
    private val clearGold = CommandBoss.ILLIAKAN.getBossInfo("normal").second + CommandBoss.ILLIAKAN.getBossInfo("hard").second

    var isChecked = character?.checkList?.command?.get(4)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(4)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3]
    )

    private val getTwoPhase = character?.checkList?.command?.get(4)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4]
    )

    private val getThreePhase = character?.checkList?.command?.get(4)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?:"노말"
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[5]
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

class Kamen(character: Character?) {
    var name = CommandBoss.KAMEN.boss
    private val seeMoreGold = CommandBoss.KAMEN.getBossInfo("normal").first + CommandBoss.KAMEN.getBossInfo("hard").first
    private val clearGold = CommandBoss.KAMEN.getBossInfo("normal").second + CommandBoss.KAMEN.getBossInfo("hard").second

    var isChecked = character?.checkList?.command?.get(5)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(5)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:"노말"
    private val onePhaseIsClear = getOnePhase?.isClear?:false
    private val onePhaseMCheck = getOnePhase?.mCheck?:false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[4]
    )

    private val getTwoPhase = character?.checkList?.command?.get(5)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:"노말"
    private val twoPhaseIsClear = getTwoPhase?.isClear?:false
    private val twoPhaseMCheck = getTwoPhase?.mCheck?:false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[5]
    )

    private val getThreePhase = character?.checkList?.command?.get(5)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty?:"노말"
    private val threePhaseIsClear = getThreePhase?.isClear?:false
    private val threePhaseMCheck = getThreePhase?.mCheck?:false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[6],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[6]
    )

    private val getFourPhase = character?.checkList?.command?.get(5)?.phases?.get(3)
    private val fourPhaseDifficulty = getFourPhase?.difficulty?:"노말"
    private val fourPhaseIsClear = getFourPhase?.isClear?:false
    private val fourPhaseMCheck = getFourPhase?.mCheck?:false

    val fourPhase = PhaseInfo(
        difficulty = fourPhaseDifficulty,
        isClearCheck = fourPhaseIsClear,
        moreCheck = fourPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[3],
        seeMoreGoldH = seeMoreGold[7],
        clearGoldN = clearGold[3],
        clearGoldH = clearGold[7]
    )

    var totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold + fourPhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold + twoPhase.totalGold + threePhase.totalGold + fourPhase.totalGold
    }

    fun onShowChecked() {
        onePhase.onShowChecked()
        twoPhase.onShowChecked()
        threePhase.onShowChecked()
        fourPhase.onShowChecked()
    }

}