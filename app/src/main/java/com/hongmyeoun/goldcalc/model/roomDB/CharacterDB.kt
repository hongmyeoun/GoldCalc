package com.hongmyeoun.goldcalc.model.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Character::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharacterDB : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
