package com.hongmyeoun.goldcalc.model.roomDB.searchHistory

import kotlinx.coroutines.flow.Flow

class SearchHistoryRepository(private val searchHistoryDao: SearchHistoryDao) {
    fun getAll(): Flow<List<SearchHistory>> {
        return searchHistoryDao.getAll()
    }

    fun insertAll(searchHistory: SearchHistory) {
        searchHistoryDao.insertAll(searchHistory)
    }

    fun delete(searchHistory: SearchHistory) {
        searchHistoryDao.delete(searchHistory)
    }

    fun update(searchHistory: SearchHistory) {
        searchHistoryDao.update(searchHistory)
    }
}