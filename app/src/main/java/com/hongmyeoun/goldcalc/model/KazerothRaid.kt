package com.hongmyeoun.goldcalc.model

enum class KazerothRaid(
    val boss: String,
    val seeMoreGold: Map<String, List<Int>>,
    val clearGold: Map<String, List<Int>>
) {
    ECHIDNA(
        boss = "에키드나",
        seeMoreGold = mapOf(
            "normal" to listOf(2000, 3400),
            "hard" to listOf(2800, 4100)
        ),
        clearGold = mapOf(
            "normal" to listOf(5000, 9500),
            "hard" to listOf(6000, 12500)
        )
    ),
}

class KazerothRaidModel {
    val echi = KazerothRaid.ECHIDNA.boss
    var echiSMG = KazerothRaid.ECHIDNA.getBossInfo("normal").first + KazerothRaid.ECHIDNA.getBossInfo("hard").first
    var echiCG =  KazerothRaid.ECHIDNA.getBossInfo("normal").second + KazerothRaid.ECHIDNA.getBossInfo("hard").second

    private fun KazerothRaid.getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}