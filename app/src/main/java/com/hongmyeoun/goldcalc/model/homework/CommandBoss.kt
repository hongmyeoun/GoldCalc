package com.hongmyeoun.goldcalc.model.homework

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
            "hard" to listOf(450, 600),
            "solo" to listOf(0, 0)
        ),
        clearGold = mapOf(
            "normal" to listOf(500, 700),
            "hard" to listOf(700, 1100),
            "solo" to listOf(100, 150)
        )
    ),
    BIACKISS(
        boss = "비아키스",
        seeMoreGold = mapOf(
            "normal" to listOf(300, 450),
            "hard" to listOf(500, 650),
            "solo" to listOf(0, 0)
        ),
        clearGold = mapOf(
            "normal" to listOf(600, 1000),
            "hard" to listOf(900, 1500),
            "solo" to listOf(150, 275)
        )
    ),
    KOUKU_SATON(
        boss = "쿠크세이튼",
        seeMoreGold = mapOf(
            "normal" to listOf(300, 500, 600),
            "hard" to listOf(300, 500, 600),
            "solo" to listOf(0, 0, 0)
        ),
        clearGold = mapOf(
            "normal" to listOf(600, 900, 1500),
            "hard" to listOf(600, 900, 1500),
            "solo" to listOf(150, 200, 450)
        )
    ),
    ABRELSHUD(
        boss = "아브렐슈드",
        seeMoreGold = mapOf(
            "normal" to listOf(250, 300, 400, 600),
            "hard" to listOf(400, 400, 500, 800),
            "solo" to listOf(0, 0, 0, 0)
        ),
        clearGold = mapOf(
            "normal" to listOf(1000, 1000, 1000, 1600),
            "hard" to listOf(1200, 1200, 1200, 2000),
            "solo" to listOf(375, 350, 300, 500)
        )
    ),
    ILLIAKAN(
        boss = "일리아칸",
        seeMoreGold = mapOf(
            "normal" to listOf(450, 550, 750),
            "hard" to listOf(600, 700, 950),
            "solo" to listOf(0, 0, 0)
        ),
        clearGold = mapOf(
            "normal" to listOf(1000, 1800, 2600),
            "hard" to listOf(1500, 2500, 3500),
            "solo" to listOf(275, 575, 975)
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
    private val seeMoreGold = CommandBoss.VALTAN.getBossInfo("normal").first + CommandBoss.VALTAN.getBossInfo("hard").first + CommandBoss.VALTAN.getBossInfo("solo").first
    private val clearGold =  CommandBoss.VALTAN.getBossInfo("normal").second + CommandBoss.VALTAN.getBossInfo("hard").second + CommandBoss.VALTAN.getBossInfo("solo").second

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
        seeMoreGoldS = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
        clearGoldS = clearGold[4]
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
        seeMoreGoldS = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3],
        clearGoldS = seeMoreGold[5]
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
    private val seeMoreGold = CommandBoss.BIACKISS.getBossInfo("normal").first + CommandBoss.BIACKISS.getBossInfo("hard").first + CommandBoss.BIACKISS.getBossInfo("solo").first
    private val clearGold = CommandBoss.BIACKISS.getBossInfo("normal").second + CommandBoss.BIACKISS.getBossInfo("hard").second + CommandBoss.BIACKISS.getBossInfo("solo").second

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
        seeMoreGoldS = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
        clearGoldS = clearGold[4]
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
        seeMoreGoldS = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3],
        clearGoldS = seeMoreGold[5]
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
    private val seeMoreGold = CommandBoss.KOUKU_SATON.getBossInfo("normal").first + CommandBoss.KOUKU_SATON.getBossInfo("hard").first + CommandBoss.KOUKU_SATON.getBossInfo("solo").first
    private val clearGold = CommandBoss.KOUKU_SATON.getBossInfo("normal").second + CommandBoss.KOUKU_SATON.getBossInfo("hard").second + CommandBoss.KOUKU_SATON.getBossInfo("solo").second

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
        noHardWithSolo = true,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        seeMoreGoldS = seeMoreGold[6],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3],
        clearGoldS = clearGold[6]
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
        noHardWithSolo = true,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        seeMoreGoldS = seeMoreGold[7],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4],
        clearGoldS = clearGold[7]
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
        noHardWithSolo = true,
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

class Abrelshud(character: Character?) {
    var name = CommandBoss.ABRELSHUD.boss
    private val seeMoreGold = CommandBoss.ABRELSHUD.getBossInfo("normal").first + CommandBoss.ABRELSHUD.getBossInfo("hard").first + CommandBoss.ABRELSHUD.getBossInfo("solo").first
    private val clearGold = CommandBoss.ABRELSHUD.getBossInfo("normal").second + CommandBoss.ABRELSHUD.getBossInfo("hard").second + CommandBoss.ABRELSHUD.getBossInfo("solo").second

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
        seeMoreGoldS = seeMoreGold[8],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[4],
        clearGoldS = clearGold[8]
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
        seeMoreGoldS = seeMoreGold[9],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[5],
        clearGoldS = clearGold[9]
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
        seeMoreGoldS = seeMoreGold[10],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[6],
        clearGoldS = clearGold[10]
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
        seeMoreGoldS = seeMoreGold[11],
        clearGoldN = clearGold[3],
        clearGoldH = clearGold[7],
        clearGoldS = clearGold[11]
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
    private val seeMoreGold = CommandBoss.ILLIAKAN.getBossInfo("normal").first + CommandBoss.ILLIAKAN.getBossInfo("hard").first + CommandBoss.ILLIAKAN.getBossInfo("solo").first
    private val clearGold = CommandBoss.ILLIAKAN.getBossInfo("normal").second + CommandBoss.ILLIAKAN.getBossInfo("hard").second + CommandBoss.ILLIAKAN.getBossInfo("solo").second

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
        seeMoreGoldS = seeMoreGold[6],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3],
        clearGoldS = clearGold[6]
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
        seeMoreGoldS = seeMoreGold[7],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4],
        clearGoldS = clearGold[7]
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