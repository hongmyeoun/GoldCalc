package com.hongmyeoun.goldcalc.model.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getAll(): Flow<List<Character>>

    @Insert
    fun insertAll(vararg lists: Character)

    @Delete
    fun delete(character: Character)

    @Update
    fun update(character: Character)
}