package com.hongmyeoun.goldcalc.model.common

import com.hongmyeoun.goldcalc.model.constants.raid.Raid.Difficulty.KR_SOLO
import com.hongmyeoun.goldcalc.model.constants.raid.Raid.Name.BOUND_ABYSS_DUNGEON_LIST
import com.hongmyeoun.goldcalc.model.constants.raid.Raid.Name.BOUND_COMMAND_RAID_LIST
import com.hongmyeoun.goldcalc.model.constants.raid.Raid.Name.BOUND_KAZEROTH_RAID_LIST

object BoundGoldReturn {
    fun isBoundGold(raidName: String, difficulty: String) : Boolean {
        return when (raidName) {
            in BOUND_COMMAND_RAID_LIST -> true
            in BOUND_ABYSS_DUNGEON_LIST -> true
            in BOUND_KAZEROTH_RAID_LIST -> checkDifficulty(difficulty)
            else -> false
        }
    }

    private fun checkDifficulty(difficulty: String) : Boolean {
        return difficulty == KR_SOLO
    }
}