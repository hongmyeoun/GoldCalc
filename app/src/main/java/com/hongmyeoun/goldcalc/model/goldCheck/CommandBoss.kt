package com.hongmyeoun.goldcalc.model.goldCheck

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
    )
}

class CommandBossModel {
    var valtan = CommandBoss.VALTAN.boss
    var valtanSMG = CommandBoss.VALTAN.getBossInfo("normal").first + CommandBoss.VALTAN.getBossInfo("hard").first
    var valtanCG =  CommandBoss.VALTAN.getBossInfo("normal").second + CommandBoss.VALTAN.getBossInfo("hard").second

    var bi = CommandBoss.BIACKISS.boss
    var biSMG = CommandBoss.BIACKISS.getBossInfo("normal").first + CommandBoss.BIACKISS.getBossInfo("hard").first
    var biCG = CommandBoss.BIACKISS.getBossInfo("normal").second + CommandBoss.BIACKISS.getBossInfo("hard").second

    var kuku = CommandBoss.KOUKU_SATON.boss
    var kukuSMG = CommandBoss.KOUKU_SATON.getBossInfo("normal").first + CommandBoss.KOUKU_SATON.getBossInfo("hard").first
    var kukuCG = CommandBoss.KOUKU_SATON.getBossInfo("normal").second + CommandBoss.KOUKU_SATON.getBossInfo("hard").second

    var abrel = CommandBoss.ABRELSHUD.boss
    var abrelSMG = CommandBoss.ABRELSHUD.getBossInfo("normal").first + CommandBoss.ABRELSHUD.getBossInfo("hard").first
    var abrelCG = CommandBoss.ABRELSHUD.getBossInfo("normal").second + CommandBoss.ABRELSHUD.getBossInfo("hard").second

    var illi = CommandBoss.ILLIAKAN.boss
    var illiSMG = CommandBoss.ILLIAKAN.getBossInfo("normal").first + CommandBoss.ILLIAKAN.getBossInfo("hard").first
    var illiCG = CommandBoss.ILLIAKAN.getBossInfo("normal").second + CommandBoss.ILLIAKAN.getBossInfo("hard").second

    var kamen = CommandBoss.KAMEN.boss
    var kamenSMG = CommandBoss.KAMEN.getBossInfo("normal").first + CommandBoss.KAMEN.getBossInfo("hard").first
    var kamenCG = CommandBoss.KAMEN.getBossInfo("normal").second + CommandBoss.KAMEN.getBossInfo("hard").second

    private fun CommandBoss.getBossInfo(mode: String): Pair<List<Int>, List<Int>> {
        val seeMoreGold = seeMoreGold[mode] ?: emptyList()
        val clearGold = clearGold[mode] ?: emptyList()
        return Pair(seeMoreGold, clearGold)
    }
}