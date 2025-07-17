package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.raid.Gold
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class CommandBoss(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    VALTAN(
        boss = Raid.Name.VALTAN,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.VALTAN,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.VALTAN,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.VALTAN,
            Raid.Difficulty.HARD to Gold.Clear.Hard.VALTAN,
        )
    ),
    BIACKISS(
        boss = Raid.Name.BIACKISS,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.BIACKISS,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.BIACKISS,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.BIACKISS,
            Raid.Difficulty.HARD to Gold.Clear.Hard.BIACKISS,
        )
    ),
    KOUKU_SATON(
        boss = Raid.Name.KOUKU_SATON,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.KOUKU_SATON,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.KOUKU_SATON,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.KOUKU_SATON,
            Raid.Difficulty.HARD to Gold.Clear.Hard.KOUKU_SATON,
        )
    ),
    ABRELSHUD(
        boss = Raid.Name.ABRELSHUD,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.ABRELSHUD,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.ABRELSHUD,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.ABRELSHUD,
            Raid.Difficulty.HARD to Gold.Clear.Hard.ABRELSHUD,
        )
    ),
    ILLIAKAN(
        boss = Raid.Name.ILLIAKAN,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.ILLIAKAN,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.ILLIAKAN,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.ILLIAKAN,
            Raid.Difficulty.HARD to Gold.Clear.Hard.ILLIAKAN,
        )
    ),
    KAMEN(
        boss = Raid.Name.KAMEN,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.KAMEN,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.KAMEN,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.KAMEN,
            Raid.Difficulty.HARD to Gold.Clear.Hard.KAMEN,
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
    private val seeMoreGold = CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.VALTAN.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.command?.get(0)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.command?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[2],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
    )

    private val getTwoPhase = character?.checkList?.command?.get(0)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear ?: false
    private val twoPhaseMCheck = getTwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3],
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
    private val seeMoreGold = CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.BIACKISS.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.command?.get(1)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.command?.get(1)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[2],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[2],
    )

    private val getTwoPhase = character?.checkList?.command?.get(1)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear ?: false
    private val twoPhaseMCheck = getTwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[3],
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
    private val seeMoreGold = CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.NORMAL).first
    private val clearGold = CommandBoss.KOUKU_SATON.getBossInfo(Raid.Difficulty.NORMAL).second

    var isChecked = character?.checkList?.command?.get(2)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.command?.get(2)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[0],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[0],
    )

    private val getTwoPhase = character?.checkList?.command?.get(2)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear ?: false
    private val twoPhaseMCheck = getTwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[1],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[1],
    )

    private val getThreePhase = character?.checkList?.command?.get(2)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val threePhaseIsClear = getThreePhase?.isClear ?: false
    private val threePhaseMCheck = getThreePhase?.mCheck ?: false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[2],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[2],
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
    private val seeMoreGold = CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.ABRELSHUD.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.command?.get(3)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.command?.get(3)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[4],
    )

    private val getTwoPhase = character?.checkList?.command?.get(3)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear ?: false
    private val twoPhaseMCheck = getTwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[5],
    )

    private val getThreePhase = character?.checkList?.command?.get(3)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val threePhaseIsClear = getThreePhase?.isClear ?: false
    private val threePhaseMCheck = getThreePhase?.mCheck ?: false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[6],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[6],
    )

    private val getFourPhase = character?.checkList?.command?.get(3)?.phases?.get(3)
    private val fourPhaseDifficulty = getFourPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val fourPhaseIsClear = getFourPhase?.isClear ?: false
    private val fourPhaseMCheck = getFourPhase?.mCheck ?: false

    val fourPhase = PhaseInfo(
        difficulty = fourPhaseDifficulty,
        isClearCheck = fourPhaseIsClear,
        moreCheck = fourPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[3],
        seeMoreGoldH = seeMoreGold[7],
        clearGoldN = clearGold[3],
        clearGoldH = clearGold[7],
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
    private val seeMoreGold = CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.NORMAL).first + CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.NORMAL).second + CommandBoss.ILLIAKAN.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.command?.get(4)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.command?.get(4)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[3],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[3],
    )

    private val getTwoPhase = character?.checkList?.command?.get(4)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear ?: false
    private val twoPhaseMCheck = getTwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[4],
    )

    private val getThreePhase = character?.checkList?.command?.get(4)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val threePhaseIsClear = getThreePhase?.isClear ?: false
    private val threePhaseMCheck = getThreePhase?.mCheck ?: false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[5],
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

    var isChecked = character?.checkList?.command?.get(5)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.command?.get(5)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[4],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[4],
    )

    private val getTwoPhase = character?.checkList?.command?.get(5)?.phases?.get(1)

    private val twoPhaseDifficulty = getTwoPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val twoPhaseIsClear = getTwoPhase?.isClear ?: false
    private val twoPhaseMCheck = getTwoPhase?.mCheck ?: false

    val twoPhase = PhaseInfo(
        difficulty = twoPhaseDifficulty,
        isClearCheck = twoPhaseIsClear,
        moreCheck = twoPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[1],
        seeMoreGoldH = seeMoreGold[5],
        clearGoldN = clearGold[1],
        clearGoldH = clearGold[5],
    )

    private val getThreePhase = character?.checkList?.command?.get(5)?.phases?.get(2)
    private val threePhaseDifficulty = getThreePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val threePhaseIsClear = getThreePhase?.isClear ?: false
    private val threePhaseMCheck = getThreePhase?.mCheck ?: false

    val threePhase = PhaseInfo(
        difficulty = threePhaseDifficulty,
        isClearCheck = threePhaseIsClear,
        moreCheck = threePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[2],
        seeMoreGoldH = seeMoreGold[6],
        clearGoldN = clearGold[2],
        clearGoldH = clearGold[6],
    )

    private val getFourPhase = character?.checkList?.command?.get(5)?.phases?.get(3)
    private val fourPhaseDifficulty = getFourPhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val fourPhaseIsClear = getFourPhase?.isClear ?: false
    private val fourPhaseMCheck = getFourPhase?.mCheck ?: false

    val fourPhase = PhaseInfo(
        difficulty = fourPhaseDifficulty,
        isClearCheck = fourPhaseIsClear,
        moreCheck = fourPhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[3],
        seeMoreGoldH = seeMoreGold[7],
        clearGoldN = clearGold[3],
        clearGoldH = clearGold[7],
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