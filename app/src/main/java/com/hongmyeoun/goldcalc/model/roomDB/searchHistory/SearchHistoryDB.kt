package com.hongmyeoun.goldcalc.model.roomDB.searchHistory

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchHistory::class], version = 1)
abstract class SearchHistoryDB : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}