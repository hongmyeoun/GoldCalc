package com.hongmyeoun.goldcalc.model.constants

class ClassName {
    object Warrior {
        const val DEFAULT = "전사(남)"
        const val DESTROYER = "디스트로이어"
        const val WARLOARD = "워로드"
        const val BERSERKER = "버서커"
        const val HOLYKNIGHT = "홀리나이트"

        const val DEFAULT_FEMALE = "전사(여)"
        const val SLAYER = "슬레이어"
        const val VALKYRIE = "발키리"

        const val DEFAULT_EN = "warrior"
        const val DESTROYER_EN = "destroyer"
        const val WARLOARD_EN = "warlord"
        const val BERSERKER_EN = "berserker"
        const val HOLYKNIGHT_EN = "holyknight"

        const val DEFAULT_FEMALE_EN = "warrior_female"
        const val SLAYER_EN = "berserker_female"
        const val VALKYRIE_EN = "holyknight_female"
    }

    object Fighter {
        const val DEFAULT = "무도가(여)"
        const val BATTLE_MASTER = "배틀마스터"
        const val INFIGHTER = "인파이터"
        const val SOUL_MASTER = "기공사"
        const val LANCE_MASTER = "창술사"

        const val DEFAULT_MALE = "무도가(남)"
        const val STRIKER = "스트라이커"
        const val BREAKER = "브레이커"

        const val DEFAULT_EN = "fighter"
        const val BATTLE_MASTER_EN = "battle_master"
        const val INFIGHTER_EN = "infighter"
        const val SOUL_MASTER_EN = "force_master"
        const val LANCE_MASTER_EN = "lance_master"

        const val DEFAULT_MALE_EN = "fighter_male"
        const val STRIKER_EN = "battle_master_male"
        const val BREAKER_EN = "infighter_male"
    }

    object Hunter {
        const val DEFAULT = "헌터(남)"
        const val DEVILHUNTER = "데빌헌터"
        const val BLASTER = "블래스터"
        const val HAWKEYE = "호크아이"
        const val SCOUTER = "스카우터"

        const val DEFAULT_FEMALE = "헌터(여)"
        const val GUNSLINGER = "건슬링어"

        const val DEFAULT_EN = "hunter"
        const val DEVILHUNTER_EN = "devil_hunter"
        const val BLASTER_EN = "blaster"
        const val HAWKEYE_EN = "hawk_eye"
        const val SCOUTER_EN = "scouter"

        const val DEFAULT_FEMALE_EN = "hunter_female"
        const val GUNSLINGER_EN = "devil_hunter_female"
    }

    object Magician {
        const val DEFAULT = "마법사"
        const val BARD = "바드"
        const val SUMMONER = "서머너"
        const val ARCANA = "아르카나"
        const val SORCERESS = "소서리스"

        const val DEFAULT_EN = "magician"
        const val BARD_EN = "bard"
        const val SUMMONER_EN = "summoner"
        const val ARCANA_EN = "arcana"
        const val SORCERESS_EN = "elemental_master"
    }

    object Delain {
        const val DEFAULT = "암살자"
        const val BLADE = "블레이드"
        const val DEMONIC = "데모닉"
        const val REAPER = "리퍼"
        const val SOUL_EATER = "소울이터"

        const val DEFAULT_EN = "delain"
        const val BLADE_EN = "blade"
        const val DEMONIC_EN = "demonic"
        const val REAPER_EN = "reaper"
        const val SOUL_EATER_EN = "soul_eater"
    }

    object Specialist {
        const val DEFAULT = "스페셜리스트"
        const val ARTIST = "도화가"
        const val AEROMANCER = "기상술사"
        const val ALCHEMIST = "환수사"

        const val DEFAULT_EN = "specialist"
        const val ARTIST_EN = "yinyangshi"
        const val AEROMANCER_EN = "weather_artist"
        const val ALCHEMIST_EN = "alchemist"
    }

    object ClassLists {
        private val WARRIOR_LIST = listOf(
            Warrior.DEFAULT,
            Warrior.DESTROYER,
            Warrior.WARLOARD,
            Warrior.BERSERKER,
            Warrior.HOLYKNIGHT,
            Warrior.DEFAULT_FEMALE,
            Warrior.SLAYER,
            Warrior.VALKYRIE
        )
        private val FIGHTER_LIST = listOf(
            Fighter.DEFAULT,
            Fighter.BATTLE_MASTER,
            Fighter.INFIGHTER,
            Fighter.SOUL_MASTER,
            Fighter.LANCE_MASTER,
            Fighter.DEFAULT_MALE,
            Fighter.STRIKER,
            Fighter.BREAKER
        )
        private val HUNTER_LIST = listOf(
            Hunter.DEFAULT,
            Hunter.DEVILHUNTER,
            Hunter.BLASTER,
            Hunter.HAWKEYE,
            Hunter.SCOUTER,
            Hunter.DEFAULT_FEMALE,
            Hunter.GUNSLINGER
        )
        private val MAGICIAN_LIST = listOf(
            Magician.DEFAULT,
            Magician.BARD,
            Magician.SUMMONER,
            Magician.ARCANA,
            Magician.SORCERESS
        )
        private val DELAIN_LIST = listOf(
            Delain.DEFAULT,
            Delain.BLADE,
            Delain.DEMONIC,
            Delain.REAPER,
            Delain.SOUL_EATER
        )
        private val SPECIALIST_LIST = listOf(
            Specialist.DEFAULT,
            Specialist.ARTIST,
            Specialist.AEROMANCER,
            Specialist.ALCHEMIST
        )

        val CHARACTER_LIST = WARRIOR_LIST + FIGHTER_LIST + HUNTER_LIST + MAGICIAN_LIST + DELAIN_LIST + SPECIALIST_LIST
    }

}