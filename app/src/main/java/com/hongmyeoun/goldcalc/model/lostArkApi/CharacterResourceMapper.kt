package com.hongmyeoun.goldcalc.model.lostArkApi

import com.hongmyeoun.goldcalc.model.constants.ClassName
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig

object CharacterResourceMapper {
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