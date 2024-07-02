package com.hongmyeoun.goldcalc.model.roomDB.searchHistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey(autoGenerate = false) val charName: String = "",
    @ColumnInfo("TimeStamp") val timeStamp: String = ""
)
