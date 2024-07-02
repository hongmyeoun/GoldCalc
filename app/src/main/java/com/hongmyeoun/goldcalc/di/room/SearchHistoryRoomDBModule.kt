package com.hongmyeoun.goldcalc.di.room

import android.content.Context
import androidx.room.Room
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistoryDB
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistoryDao
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchHistoryRoomDBModule {
    @Provides
    @Singleton
    fun provideSearchHistoryDB(@ApplicationContext appContext: Context) : SearchHistoryDB {
        return Room.databaseBuilder(
            context = appContext,
            klass = SearchHistoryDB::class.java,
            name = "searchHistory.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDao(searchHistoryDB: SearchHistoryDB) : SearchHistoryDao {
        return searchHistoryDB.searchHistoryDao()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(searchHistoryDao: SearchHistoryDao) : SearchHistoryRepository {
        return SearchHistoryRepository(searchHistoryDao)
    }
}