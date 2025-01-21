package com.hongmyeoun.goldcalc.model.roomDB.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hongmyeoun.goldcalc.model.constants.raid.Raid

@Entity
data class Character(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("name") val name: String = "",
    @ColumnInfo("itemLevel") val itemLevel: String = "",
    @ColumnInfo("className") val className: String = "",
    @ColumnInfo("serverName") val serverName: String = "",
    @ColumnInfo("weeklyGold") val weeklyGold: Int = 0,
    @ColumnInfo("plusGold") val plusGold: String = "0",
    @ColumnInfo("minusGold") val minusGold: String = "0",
    @ColumnInfo("goldCheck") val goldCheck: Boolean = false,
    @ColumnInfo("earnGold") val earnGold: Int = 0,

    @ColumnInfo("guildName") val guildName: String? = null,
    @ColumnInfo("title") val title: String? = null,
    @ColumnInfo("characterLevel") val characterLevel: Int = 0,
    @ColumnInfo("expeditionLevel") val expeditionLevel: Int = 0,
    @ColumnInfo("pvpGradeName") val pvpGradeName: String = "",
    @ColumnInfo("townLevel") val townLevel: Int? = null,
    @ColumnInfo("townName") val townName: String = "",
    @ColumnInfo("characterImage") val characterImage: String? = null,

    @ColumnInfo("avatarImage") var avatarImage: Boolean = true,

    @ColumnInfo("arkPassiveEvolution") val evolutionLevel: Int = 0,
    @ColumnInfo("arkPassiveEnlightenment") val enlightenmentLevel: Int = 0,
    @ColumnInfo("arkPassiveLeap") val leapLevel: Int = 0,

    @ColumnInfo("checkList") val checkList: CheckList = CheckList(),
    @ColumnInfo("raidPhaseInfo") val raidPhaseInfo: RaidPhaseInfo = RaidPhaseInfo(),
)

data class RaidPhaseInfo(
    @ColumnInfo("egirPhase") val egirPhase: Int = 0,
    @ColumnInfo("egirTotalGold") val egirTotalGold: Int = 0,

    @ColumnInfo("behemothPhase") val behemothPhase: Int = 0,
    @ColumnInfo("behemothTotalGold") val behemothTotalGold: Int = 0,

    @ColumnInfo("echidnaPhase") val echidnaPhase: Int = 0,
    @ColumnInfo("echidnaTotalGold") val echidnaTotalGold: Int = 0,

    @ColumnInfo("kamenPhase") val kamenPhase: Int = 0,
    @ColumnInfo("kamenTotalGold") val kamenTotalGold: Int = 0,

    @ColumnInfo("ivoryPhase") val ivoryPhase: Int = 0,
    @ColumnInfo("ivoryTotalGold") val ivoryTotalGold: Int = 0,

    @ColumnInfo("illiakanPhase") val illiakanPhase: Int = 0,
    @ColumnInfo("illiakanTotalGold") val illiakanTotalGold: Int = 0,

    @ColumnInfo("kayangelPhase") val kayangelPhase: Int = 0,
    @ColumnInfo("kayangelTotalGold") val kayangelTotalGold: Int = 0,

    @ColumnInfo("abrelPhase") val abrelPhase: Int = 0,
    @ColumnInfo("abrelTotalGold") val abrelTotalGold: Int = 0,

    @ColumnInfo("koukuPhase") val koukuPhase: Int = 0,
    @ColumnInfo("koukuTotalGold") val koukuTotalGold: Int = 0,

    @ColumnInfo("biackissPhase") val biackissPhase: Int = 0,
    @ColumnInfo("biackissTotalGold") val biackissTotalGold: Int = 0,

    @ColumnInfo("valtanPhase") val valtanPhase: Int = 0,
    @ColumnInfo("valtanTotalGold") val valtanTotalGold: Int = 0,

    @ColumnInfo("abrel2Phase") val abrel2Phase: Int = 0,
    @ColumnInfo("abrel2TotalGold") val abrel2TotalGold: Int = 0,

    @ColumnInfo("mordumPhase") val mordumPhase: Int = 0,
    @ColumnInfo("mordumTotalGold") val mordumTotalGold: Int = 0
)


data class CheckList(
    @ColumnInfo("Command") val command: List<RaidList> = listOf(
        RaidList(
            name = Raid.Name.VALTAN,
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.BIACKISS,
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.KOUKU_SATON,
            phases = listOf(Phase(), Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.ABRELSHUD,
            phases = listOf(Phase(), Phase(), Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.ILLIAKAN,
            phases = listOf(Phase(), Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.KAMEN,
            phases = listOf(Phase(), Phase(), Phase(), Phase(difficulty = Raid.Difficulty.KR_HARD))
        ),
    ),
    @ColumnInfo("AbyssDungeon") val abyssDungeon: List<RaidList> = listOf(
        RaidList(
            name = Raid.Name.KAYANGEL,
            phases = listOf(Phase(), Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.IVORY_TOWER,
            phases = listOf(Phase(), Phase(), Phase())
        ),
    ),
    @ColumnInfo("Kazeroth") val kazeroth: List<RaidList> = listOf(
        RaidList(
            name = Raid.Name.ECHIDNA,
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.EGIR,
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.ABRELSHUD_2,
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = Raid.Name.MORDUM,
            phases = listOf(Phase(), Phase(), Phase())
        )
    ),
    @ColumnInfo("Epic") val epic: List<RaidList> = listOf(
        RaidList(
            name = Raid.Name.BEHEMOTH,
            phases = listOf(Phase(), Phase())
        )
    )
)

data class RaidList(
    val name: String,
    val phases: List<Phase> = listOf(
        Phase(difficulty = Raid.Difficulty.KR_NORMAL, isClear = false, mCheck = false)
    ),
    @ColumnInfo("isCheck") val isCheck: Boolean = true,
)

data class Phase(
    @ColumnInfo("difficulty") val difficulty: String = Raid.Difficulty.KR_NORMAL,
    @ColumnInfo("isClear") val isClear: Boolean = false,
    @ColumnInfo("mCheck") val mCheck: Boolean = false,
)
