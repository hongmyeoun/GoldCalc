package com.hongmyeoun.goldcalc.model.roomDB

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
    @ColumnInfo("goldCheck") val goldCheck: Boolean = false,
    @ColumnInfo("checkList") val checkList: CheckList = CheckList()
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