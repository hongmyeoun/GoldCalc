package com.hongmyeoun.goldcalc.model.roomDB.searchHistory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM searchhistory ORDER BY TimeStamp DESC")
    fun getAll(): Flow<List<SearchHistory>>

    @Insert
    fun insertAll(vararg list: SearchHistory)

    @Delete
    fun delete(searchHistory: SearchHistory)

    @Update
    fun update(searchHistory: SearchHistory)
}