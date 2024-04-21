package com.hongmyeoun.goldcalc.model.roomDB.character

import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val characterDao: CharacterDao) {
    fun getAll(): Flow<List<Character>> {
        return characterDao.getAll()
    }

    fun getNames(): List<String> {
        return characterDao.getNames()
    }

    fun getCharacterByName(name: String): Flow<Character> {
        return characterDao.getCharacterByName(name)
    }

    fun insertAll(character: Character) {
        characterDao.insertAll(character)
    }

    fun delete(character: Character) {
        characterDao.delete(character)
    }

    fun update(character: Character) {
        characterDao.update(character)
    }
}