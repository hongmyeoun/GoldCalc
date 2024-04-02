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
        RaidList("Valtan"),
        RaidList("Biackiss"),
        RaidList("KoukuSaton"),
        RaidList("Abrelshud"),
        RaidList("Illiakan"),
        RaidList("Kamen"),
    ),
    @ColumnInfo("AbyssDungeon") val abyssDungeon: List<RaidList> = listOf(
        RaidList("Kayangel"),
        RaidList("IvoryTower"),
    ),
    @ColumnInfo("Kazeroth") val kazeroth: List<RaidList> = listOf(
        RaidList("Echidna"),
    ),
    @ColumnInfo("Epic") val epic: List<RaidList> = listOf(
        RaidList("Behemoth"),
    )
)

data class RaidList(
    val name: String,
    @ColumnInfo("difficulty") val difficulty: String = "normal",
    @ColumnInfo("isClear") val isClear: Boolean = false,
    @ColumnInfo("mCheck") val mCheck: Boolean = false,
)