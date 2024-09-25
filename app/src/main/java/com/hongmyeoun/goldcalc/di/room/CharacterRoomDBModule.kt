package com.hongmyeoun.goldcalc.di.room

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // 새로운 Character_new 테이블 생성
            db.execSQL(
                """
            CREATE TABLE Character_new (
                name TEXT PRIMARY KEY NOT NULL,
                itemLevel TEXT NOT NULL,
                className TEXT NOT NULL,
                serverName TEXT NOT NULL,
                weeklyGold INTEGER NOT NULL,
                plusGold TEXT NOT NULL DEFAULT '0',
                minusGold TEXT NOT NULL DEFAULT '0',
                goldCheck INTEGER NOT NULL DEFAULT 0,
                earnGold INTEGER NOT NULL DEFAULT 0,
                guildName TEXT,
                title TEXT,
                characterLevel INTEGER NOT NULL DEFAULT 0,
                expeditionLevel INTEGER NOT NULL DEFAULT 0,
                pvpGradeName TEXT NOT NULL,
                townLevel INTEGER,
                townName TEXT NOT NULL,
                characterImage TEXT,
                avatarImage INTEGER NOT NULL DEFAULT 1,
                arkPassiveEvolution INTEGER NOT NULL DEFAULT 0,
                arkPassiveEnlightenment INTEGER NOT NULL DEFAULT 0,
                arkPassiveLeap INTEGER NOT NULL DEFAULT 0,
                checkList TEXT NOT NULL,
                raidPhaseInfo TEXT NOT NULL
            )
        """.trimIndent()
            )

            // 기존 Character 테이블에서 데이터 복사
            db.execSQL(
                """
            INSERT INTO Character_new
            SELECT * FROM character
        """
            )

            // 기존 Character 테이블 삭제
            db.execSQL("DROP TABLE character")

            // 새로 만든 테이블의 이름을 Character로 변경
            db.execSQL("ALTER TABLE Character_new RENAME TO character")
        }
    }

    @Provides
    @Singleton // synchronized와 유사효과
    fun provideCharacterDB(@ApplicationContext appContext: Context): CharacterDB {
        return Room.databaseBuilder(
            context = appContext,
            klass = CharacterDB::class.java,
            name = "character.db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(characterDB: CharacterDB): CharacterDao {
        return characterDB.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(characterDao: CharacterDao): CharacterRepository {
        return CharacterRepository(characterDao)
    }
}