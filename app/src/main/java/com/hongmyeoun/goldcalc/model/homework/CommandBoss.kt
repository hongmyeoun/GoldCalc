package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class CommandBoss(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    VALTAN(
        boss = Raid.Name.VALTAN,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(300, 400),
            Raid.Difficulty.HARD to listOf(450, 600),
            Raid.Difficulty.SOLO to listOf(0, 0)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(500, 700),
            Raid.Difficulty.HARD to listOf(700, 1100),
            Raid.Difficulty.SOLO to listOf(100, 150)
        )
    ),
    BIACKISS(
        boss = Raid.Name.BIACKISS,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(300, 450),
            Raid.Difficulty.HARD to listOf(500, 650),
            Raid.Difficulty.SOLO to listOf(0, 0)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(600, 1000),
            Raid.Difficulty.HARD to listOf(900, 1500),
            Raid.Difficulty.SOLO to listOf(150, 275)
        )
    ),
    KOUKU_SATON(
        boss = Raid.Name.KOUKU_SATON,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(300, 500, 600),
            Raid.Difficulty.HARD to listOf(300, 500, 600),
            Raid.Difficulty.SOLO to listOf(0, 0, 0)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(600, 900, 1500),
            Raid.Difficulty.HARD to listOf(600, 900, 1500),
            Raid.Difficulty.SOLO to listOf(150, 200, 450)
        )
    ),
    ABRELSHUD(
        boss = Raid.Name.ABRELSHUD,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(250, 300, 400, 600),
            Raid.Difficulty.HARD to listOf(400, 400, 500, 800),
            Raid.Difficulty.SOLO to listOf(0, 0, 0, 0)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(1000, 1000, 1000, 1600),
            Raid.Difficulty.HARD to listOf(1200, 1200, 1200, 2000),
            Raid.Difficulty.SOLO to listOf(375, 350, 300, 500)
        )
    ),
    ILLIAKAN(
        boss = Raid.Name.ILLIAKAN,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(450, 550, 750),
            Raid.Difficulty.HARD to listOf(600, 700, 950),
            Raid.Difficulty.SOLO to listOf(0, 0, 0)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(1000, 1800, 2600),
            Raid.Difficulty.HARD to listOf(1500, 2500, 3500),
            Raid.Difficulty.SOLO to listOf(275, 575, 975)
        )
    ),
    KAMEN(
        boss = Raid.Name.KAMEN,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(1500, 1800, 2500, 0),
            Raid.Difficulty.HARD to listOf(2000, 2400, 2800, 3600)
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to listOf(3500, 4000, 5500, 0),
            Raid.Difficulty.HARD to listOf(5000, 6000, 9000, 21000)
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
    private val seeMoreGold = CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.HARD).first + CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.SOLO).first
    private val clearGold =  CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.HARD).second + CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.command?.get(0)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(0)?.phases?.get(0)

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
        seeMoreGoldS = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
        clearGoldS = clearGold[4]
    )

    private val getTwoPhase = character?.checkList?.command?.get(0)?.phases?.get(1)

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
    private val seeMoreGold = CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.HARD).first + CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.SOLO).first
    private val clearGold = CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.HARD).second + CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.command?.get(1)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(1)?.phases?.get(0)

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
        seeMoreGoldS = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
        clearGoldS = clearGold[4]
    )

    private val getTwoPhase = character?.checkList?.command?.get(1)?.phases?.get(1)

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
    private val seeMoreGold = CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.HARD).first + CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.SOLO).first
    private val clearGold = CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.HARD).second + CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.command?.get(2)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(2)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val threePhaseDifficulty = getThreePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val seeMoreGold = CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.HARD).first + CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.SOLO).first
    private val clearGold = CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.HARD).second + CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.command?.get(3)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(3)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val threePhaseDifficulty = getThreePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val fourPhaseDifficulty = getFourPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val seeMoreGold = CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.HARD).first + CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.SOLO).first
    private val clearGold = CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.HARD).second + CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.SOLO).second

    var isChecked = character?.checkList?.command?.get(4)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(4)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val threePhaseDifficulty = getThreePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val seeMoreGold = CommandBoss.KAMEN.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.KAMEN.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = CommandBoss.KAMEN.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.KAMEN.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.command?.get(5)?.isCheck?:false

    private val getOnePhase = character?.checkList?.command?.get(5)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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

    private val twoPhaseDifficulty = getTwoPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val threePhaseDifficulty = getThreePhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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
    private val fourPhaseDifficulty = getFourPhase?.difficulty?:Raid.Difficulty.KR_NORMAL
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