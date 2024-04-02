package com.hongmyeoun.goldcalc.model.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Character::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharacterDB : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        private var INSTANCE: CharacterDB? = null

        fun getDB(context: Context): CharacterDB {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context = context,
                    klass = CharacterDB::class.java,
                    name = "character.db"
                ).build()
                db
            }
        }
    }
}