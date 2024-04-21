package com.hongmyeoun.goldcalc.di.room

import android.content.Context
import androidx.room.Room
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterDB
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterDao
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterRoomDBModule {

    @Provides
    @Singleton // synchronized와 유사효과
    fun provideCharacterDB(@ApplicationContext appContext: Context) : CharacterDB {
        return Room.databaseBuilder(
            context = appContext,
            klass = CharacterDB::class.java,
            name = "character.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(characterDB: CharacterDB) : CharacterDao {
        return characterDB.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(characterDao: CharacterDao) : CharacterRepository {
        return CharacterRepository(characterDao)
    }

}