package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.raid.Gold
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

enum class EventRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    EVENT(
        boss = Raid.Name.EVENT_RAID,
        seeMoreGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.SeeMore.Normal.EVENT_RAID,
            Raid.Difficulty.HARD to Gold.SeeMore.Hard.EVENT_RAID,
        ),
        clearGold = mapOf(
            Raid.Difficulty.NORMAL to Gold.Clear.Normal.EVENT_RAID,
            Raid.Difficulty.HARD to Gold.Clear.Hard.EVENT_RAID,
        )
    );

    fun getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}

class EventRaidModel(character: Character?) {
    val event: Event = if (character != null) {
        Event(character)
    } else {
        Event(null)
    }
}

class Event(character: Character?) {
    var name = EventRaid.EVENT.boss
    private val seeMoreGold = EventRaid.EVENT.getBossInfo(Raid.Difficulty.NORMAL).first + EventRaid.EVENT.getBossInfo(Raid.Difficulty.HARD).first
    private val clearGold = EventRaid.EVENT.getBossInfo(Raid.Difficulty.NORMAL).second + EventRaid.EVENT.getBossInfo(Raid.Difficulty.HARD).second

    var isChecked = character?.checkList?.event?.get(0)?.isCheck ?: false

    private val getOnePhase = character?.checkList?.event?.get(0)?.phases?.get(0)

    private val onePhaseDifficulty = getOnePhase?.difficulty ?: Raid.Difficulty.KR_NORMAL
    private val onePhaseIsClear = getOnePhase?.isClear ?: false
    private val onePhaseMCheck = getOnePhase?.mCheck ?: false

    val onePhase = PhaseInfo(
        difficulty = onePhaseDifficulty,
        isClearCheck = onePhaseIsClear,
        moreCheck = onePhaseMCheck,
        isChecked = isChecked,
        seeMoreGoldN = seeMoreGold[0],
        seeMoreGoldH = seeMoreGold[1],
        clearGoldN = clearGold[0],
        clearGoldH = clearGold[1]
    )

    var totalGold = onePhase.totalGold

    fun totalGold() {
        totalGold = onePhase.totalGold
    }

    fun onShowChecked() {
        onePhase.onShowChecked()
    }
}