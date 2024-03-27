package com.hongmyeoun.goldcalc.model

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
    )
}

class AbyssDungeonModel {
    val ka = AbyssDungeon.KAYANGEL.boss
    val kaSMG = AbyssDungeon.KAYANGEL.getBossInfo("normal").first + AbyssDungeon.KAYANGEL.getBossInfo("hard").first
    val kaCG = AbyssDungeon.KAYANGEL.getBossInfo("normal").second + AbyssDungeon.KAYANGEL.getBossInfo("hard").second

    val ivT = AbyssDungeon.IVORY_TOWER.boss
    val ivTSMG = AbyssDungeon.IVORY_TOWER.getBossInfo("normal").first + AbyssDungeon.IVORY_TOWER.getBossInfo("hard").first
    val ivTCG = AbyssDungeon.IVORY_TOWER.getBossInfo("normal").second + AbyssDungeon.IVORY_TOWER.getBossInfo("hard").second

    private fun AbyssDungeon.getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}