package com.hongmyeoun.goldcalc.model.lostArkApi

import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.ClassName
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig

object CharacterResourceMapper {
    fun getClassEmblem(characterClassName: String): Int {
        val classImageMap = mapOf(
            ClassName.Warrior.DEFAULT to R.drawable.emblem_warrior,
            ClassName.Warrior.DESTROYER to R.drawable.emblem_destroyer,
            ClassName.Warrior.WARLOARD to R.drawable.emblem_warlord,
            ClassName.Warrior.BERSERKER to R.drawable.emblem_berserker,
            ClassName.Warrior.HOLYKNIGHT to R.drawable.emblem_holyknight,

            ClassName.Warrior.DEFAULT_FEMALE to R.drawable.emblem_warrior_female,
            ClassName.Warrior.SLAYER to R.drawable.emblem_berserker_female,

            ClassName.Fighter.DEFAULT_MALE to R.drawable.emblem_fighter_male,
            ClassName.Fighter.STRIKER to R.drawable.emblem_battle_master_male,
            ClassName.Fighter.BREAKER to R.drawable.emblem_infighter_male,

            ClassName.Fighter.DEFAULT to R.drawable.emblem_fighter,
            ClassName.Fighter.BATTLE_MASTER to R.drawable.emblem_battle_master,
            ClassName.Fighter.INFIGHTER to R.drawable.emblem_infighter,
            ClassName.Fighter.SOUL_MASTER to R.drawable.emblem_force_master,
            ClassName.Fighter.LANCE_MASTER to R.drawable.emblem_lance_master,

            ClassName.Hunter.DEFAULT to R.drawable.emblem_hunter,
            ClassName.Hunter.DEVILHUNTER to R.drawable.emblem_devil_hunter,
            ClassName.Hunter.BLASTER to R.drawable.emblem_blaster,
            ClassName.Hunter.HAWKEYE to R.drawable.emblem_hawk_eye,
            ClassName.Hunter.SCOUTER to R.drawable.emblem_scouter,

            ClassName.Hunter.DEFAULT_FEMALE to R.drawable.emblem_hunter_female,
            ClassName.Hunter.GUNSLINGER to R.drawable.emblem_devil_hunter_female,

            ClassName.Magician.DEFAULT to R.drawable.emblem_magician,
            ClassName.Magician.BARD to R.drawable.emblem_bard,
            ClassName.Magician.SUMMONER to R.drawable.emblem_summoner,
            ClassName.Magician.ARCANA to R.drawable.emblem_arcana,
            ClassName.Magician.SORCERESS to R.drawable.emblem_elemental_master,

            ClassName.Delain.DEFAULT to R.drawable.emblem_delain,
            ClassName.Delain.BLADE to R.drawable.emblem_blade,
            ClassName.Delain.DEMONIC to R.drawable.emblem_demonic,
            ClassName.Delain.REAPER to R.drawable.emblem_reaper,
            ClassName.Delain.SOUL_EATER to R.drawable.emblem_soul_eater,

            ClassName.Specialist.DEFAULT to R.drawable.emblem_specialist,
            ClassName.Specialist.ARTIST to R.drawable.emblem_yinyangshi,
            ClassName.Specialist.AEROMANCER to R.drawable.emblem_weather_artist,
            ClassName.Specialist.ALCHEMIST to R.drawable.emblem_alchemist
        )

        return classImageMap[characterClassName] ?: R.drawable.emblem_specialist
    }

    fun getClassDefaultImg(characterClassName: String): Int {
        val classImageMap = mapOf(
            ClassName.Warrior.DEFAULT to R.drawable.img_detail_warlord,
            ClassName.Warrior.DESTROYER to R.drawable.img_detail_destroyer,
            ClassName.Warrior.WARLOARD to R.drawable.img_detail_warlord,
            ClassName.Warrior.BERSERKER to R.drawable.img_detail_berserker,
            ClassName.Warrior.HOLYKNIGHT to R.drawable.img_detail_holyknight,

            ClassName.Warrior.DEFAULT_FEMALE to R.drawable.img_detail_slayer,
            ClassName.Warrior.SLAYER to R.drawable.img_detail_slayer,

            ClassName.Fighter.DEFAULT_MALE to R.drawable.img_detail_striker,
            ClassName.Fighter.STRIKER to R.drawable.img_detail_striker,
            ClassName.Fighter.BREAKER to R.drawable.img_detail_breaker,

            ClassName.Fighter.DEFAULT to R.drawable.img_detail_battlemaster,
            ClassName.Fighter.BATTLE_MASTER to R.drawable.img_detail_battlemaster,
            ClassName.Fighter.INFIGHTER to R.drawable.img_detail_infighter,
            ClassName.Fighter.SOUL_MASTER to R.drawable.img_detail_soulmaster,
            ClassName.Fighter.LANCE_MASTER to R.drawable.img_detail_lancemaster,

            ClassName.Hunter.DEFAULT to R.drawable.img_detail_devilhunter,
            ClassName.Hunter.DEVILHUNTER to R.drawable.img_detail_devilhunter,
            ClassName.Hunter.BLASTER to R.drawable.img_detail_blaster,
            ClassName.Hunter.HAWKEYE to R.drawable.img_detail_hawkeye,
            ClassName.Hunter.SCOUTER to R.drawable.img_detail_scouter,

            ClassName.Hunter.DEFAULT_FEMALE to R.drawable.img_detail_gunslinger,
            ClassName.Hunter.GUNSLINGER to R.drawable.img_detail_gunslinger,

            ClassName.Magician.DEFAULT to R.drawable.img_detail_summoner,
            ClassName.Magician.BARD to R.drawable.img_detail_bard,
            ClassName.Magician.SUMMONER to R.drawable.img_detail_summoner,
            ClassName.Magician.ARCANA to R.drawable.img_detail_arcana,
            ClassName.Magician.SORCERESS to R.drawable.img_detail_sorceress,

            ClassName.Delain.DEFAULT to R.drawable.img_detail_reaper,
            ClassName.Delain.BLADE to R.drawable.img_detail_blade,
            ClassName.Delain.DEMONIC to R.drawable.img_detail_demonic,
            ClassName.Delain.REAPER to R.drawable.img_detail_reaper,
            ClassName.Delain.SOUL_EATER to R.drawable.img_detail_souleater,

            ClassName.Specialist.DEFAULT to R.drawable.img_detail_artist,
            ClassName.Specialist.ARTIST to R.drawable.img_detail_artist,
            ClassName.Specialist.AEROMANCER to R.drawable.img_detail_aeromancer,
            ClassName.Specialist.ALCHEMIST to R.drawable.img_detail_wildsoul
        )

        return classImageMap[characterClassName] ?: R.drawable.img_detail_breaker
    }

    fun getCharacterThumbURL(characterClassName: String): String {
        val startURL = NetworkConfig.CHARACTER_THUMB
        val endURL = NetworkConfig.END_POINT_PNG
        val classImageMap = mapOf(
            ClassName.Warrior.DEFAULT to ClassName.Warrior.DEFAULT_EN,
            ClassName.Warrior.DESTROYER to ClassName.Warrior.DESTROYER_EN,
            ClassName.Warrior.WARLOARD to ClassName.Warrior.WARLOARD_EN,
            ClassName.Warrior.BERSERKER to ClassName.Warrior.BERSERKER_EN,
            ClassName.Warrior.HOLYKNIGHT to ClassName.Warrior.HOLYKNIGHT_EN,

            ClassName.Warrior.DEFAULT_FEMALE to ClassName.Warrior.DEFAULT_FEMALE_EN,
            ClassName.Warrior.SLAYER to ClassName.Warrior.SLAYER_EN,

            ClassName.Fighter.DEFAULT_MALE to ClassName.Fighter.DEFAULT_MALE_EN,
            ClassName.Fighter.STRIKER to ClassName.Fighter.STRIKER_EN,
            ClassName.Fighter.BREAKER to ClassName.Fighter.BREAKER_EN,

            ClassName.Fighter.DEFAULT to ClassName.Fighter.DEFAULT_EN,
            ClassName.Fighter.BATTLE_MASTER to ClassName.Fighter.BATTLE_MASTER_EN,
            ClassName.Fighter.INFIGHTER to ClassName.Fighter.INFIGHTER_EN,
            ClassName.Fighter.SOUL_MASTER to ClassName.Fighter.SOUL_MASTER_EN,
            ClassName.Fighter.LANCE_MASTER to ClassName.Fighter.LANCE_MASTER_EN,

            ClassName.Hunter.DEFAULT to ClassName.Hunter.DEFAULT_EN,
            ClassName.Hunter.DEVILHUNTER to ClassName.Hunter.DEVILHUNTER_EN,
            ClassName.Hunter.BLASTER to ClassName.Hunter.BLASTER_EN,
            ClassName.Hunter.HAWKEYE to ClassName.Hunter.HAWKEYE_EN,
            ClassName.Hunter.SCOUTER to ClassName.Hunter.SCOUTER_EN,

            ClassName.Hunter.DEFAULT_FEMALE to ClassName.Hunter.DEFAULT_FEMALE_EN,
            ClassName.Hunter.GUNSLINGER to ClassName.Hunter.GUNSLINGER_EN,

            ClassName.Magician.DEFAULT to ClassName.Magician.DEFAULT_EN,
            ClassName.Magician.BARD to ClassName.Magician.BARD_EN,
            ClassName.Magician.SUMMONER to ClassName.Magician.SUMMONER_EN,
            ClassName.Magician.ARCANA to ClassName.Magician.ARCANA_EN,
            ClassName.Magician.SORCERESS to ClassName.Magician.SORCERESS_EN,

            ClassName.Delain.DEFAULT to ClassName.Delain.DEFAULT_EN,
            ClassName.Delain.BLADE to ClassName.Delain.BLADE_EN,
            ClassName.Delain.DEMONIC to ClassName.Delain.DEMONIC_EN,
            ClassName.Delain.REAPER to ClassName.Delain.REAPER_EN,
            ClassName.Delain.SOUL_EATER to ClassName.Delain.SOUL_EATER_EN,

            ClassName.Specialist.DEFAULT to ClassName.Specialist.DEFAULT_EN,
            ClassName.Specialist.ARTIST to ClassName.Specialist.ARTIST_EN,
            ClassName.Specialist.AEROMANCER to ClassName.Specialist.AEROMANCER_EN,
            ClassName.Specialist.ALCHEMIST to ClassName.Specialist.ALCHEMIST_EN
        )
        return startURL + classImageMap[characterClassName] + endURL
    }
}