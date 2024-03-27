package com.hongmyeoun.goldcalc.model

enum class EpicRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    BEHEMOTH(
        boss = "베히모스",
        seeMoreGold = mapOf(
            "normal" to listOf(3100, 4900),
            "hard" to listOf(3100, 4900)
        ),
        clearGold = mapOf(
            "normal" to listOf(7000, 14500),
            "hard" to listOf(7000, 14500)
        )
    ),
}

class EpicRaidModel {
    val behe = EpicRaid.BEHEMOTH.boss
    val beheSMG = EpicRaid.BEHEMOTH.getBossInfo("normal").first + EpicRaid.BEHEMOTH.getBossInfo("hard").first
    val beheCG =  EpicRaid.BEHEMOTH.getBossInfo("normal").second + EpicRaid.BEHEMOTH.getBossInfo("hard").second

    private fun EpicRaid.getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}