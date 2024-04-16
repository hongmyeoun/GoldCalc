package com.hongmyeoun.goldcalc.model.roomDB.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = false) val name: String = "",
    @ColumnInfo("itemLevel") val itemLevel: String = "",
    @ColumnInfo("className") val className: String = "",
    @ColumnInfo("serverName") val serverName: String = "",
    @ColumnInfo("weeklyGold") val weeklyGold: Int = 0,
    @ColumnInfo("plusGold") val plusGold: String = "0",
    @ColumnInfo("minusGold") val minusGold: String = "0",
    @ColumnInfo("goldCheck") val goldCheck: Boolean = false,
    @ColumnInfo("checkList") val checkList: CheckList = CheckList(),
    @ColumnInfo("earnGold") val earnGold: Int = 0,
    @ColumnInfo("raidPhaseInfo") val raidPhaseInfo: RaidPhaseInfo = RaidPhaseInfo(),
)

data class RaidPhaseInfo(
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
)


data class CheckList(
    @ColumnInfo("Command") val command: List<RaidList> = listOf(
        RaidList(
            name = "발탄",
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = "비아키스",
            phases = listOf(Phase(), Phase())
        ),
        RaidList(
            name = "쿠크세이튼",
            phases = listOf(Phase(), Phase(), Phase())
        ),
        RaidList(
            name = "아브렐슈드",
            phases = listOf(Phase(), Phase(), Phase(), Phase())
        ),
        RaidList(
            name = "일리아칸",
            phases = listOf(Phase(), Phase(), Phase())
        ),
        RaidList(
            name = "카멘",
            phases = listOf(Phase(), Phase(), Phase(), Phase())
        ),
    ),
    @ColumnInfo("AbyssDungeon") val abyssDungeon: List<RaidList> = listOf(
        RaidList(
            name = "카양겔",
            phases = listOf(Phase(), Phase(), Phase())
        ),
        RaidList(
            name = "상아탑",
            phases = listOf(Phase(), Phase(), Phase(), Phase())
        ),
    ),
    @ColumnInfo("Kazeroth") val kazeroth: List<RaidList> = listOf(
        RaidList(
            name = "에키드나",
            phases = listOf(Phase(), Phase())
        )
    ),
    @ColumnInfo("Epic") val epic: List<RaidList> = listOf(
        RaidList(
            name = "베히모스",
            phases = listOf(Phase(), Phase())
        )
    )
)

data class RaidList(
    val name: String,
    val phases: List<Phase> = listOf(
        Phase(difficulty = "노말", isClear = false, mCheck = false)
    ),
    @ColumnInfo("isCheck") val isCheck: Boolean = false,

)

data class Phase(
    @ColumnInfo("difficulty") val difficulty: String = "노말",
    @ColumnInfo("isClear") val isClear: Boolean = false,
    @ColumnInfo("mCheck") val mCheck: Boolean = false,
)