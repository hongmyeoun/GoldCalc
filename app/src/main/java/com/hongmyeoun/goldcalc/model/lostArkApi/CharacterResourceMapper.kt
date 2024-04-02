package com.hongmyeoun.goldcalc.model.lostArkApi

import com.hongmyeoun.goldcalc.R

object CharacterResourceMapper {
    fun getClassImage(isDark: Boolean, characterClassName: String): Int {
        val classImageMap = mapOf(
            "전사(남)" to if(isDark) R.drawable.emblem_warrior else R.drawable.emblem_warrior_dark,
            "디스트로이어" to if(isDark) R.drawable.emblem_destroyer else R.drawable.emblem_destroyer_dark,
            "워로드" to if(isDark) R.drawable.emblem_warlord else R.drawable.emblem_warlord_dark,
            "버서커" to if(isDark) R.drawable.emblem_berserker else R.drawable.emblem_berserker_dark,
            "홀리나이트" to if(isDark) R.drawable.emblem_holyknight else R.drawable.emblem_holyknight_dark,
            "전사(여)" to if(isDark) R.drawable.emblem_warrior_female else R.drawable.emblem_warrior_female_dark,
            "슬레이어" to if(isDark) R.drawable.emblem_berserker_female else R.drawable.emblem_berserker_female_dark,
            "무도가(남)" to if(isDark) R.drawable.emblem_fighter_male else R.drawable.emblem_fighter_male_dark,
            "스트라이커" to if(isDark) R.drawable.emblem_battle_master_male else R.drawable.emblem_battle_master_male_dark,
            "브레이커" to if(isDark) R.drawable.emblem_infighter_male else R.drawable.emblem_infighter_male_dark,
            "무도가(여)" to if(isDark) R.drawable.emblem_fighter else R.drawable.emblem_fighter_dark,
            "배틀마스터" to if(isDark) R.drawable.emblem_battle_master else R.drawable.emblem_battle_master_dark,
            "인파이터" to if(isDark) R.drawable.emblem_infighter else R.drawable.emblem_infighter_dark,
            "기공사" to if(isDark) R.drawable.emblem_force_master else R.drawable.emblem_force_master_dark,
            "창술사" to if(isDark) R.drawable.emblem_lance_master else R.drawable.emblem_lance_master_dark,
            "헌터(남)" to if(isDark) R.drawable.emblem_hunter else R.drawable.emblem_hunter_dark,
            "데빌헌터" to if(isDark) R.drawable.emblem_devil_hunter else R.drawable.emblem_devil_hunter_dark,
            "블래스터" to if(isDark) R.drawable.emblem_blaster else R.drawable.emblem_blaster_dark,
            "호크아이" to if(isDark) R.drawable.emblem_hawk_eye else R.drawable.emblem_hawk_eye_dark,
            "스카우터" to if(isDark) R.drawable.emblem_scouter else R.drawable.emblem_scouter_dark,
            "헌터(여)" to if(isDark) R.drawable.emblem_hunter_female else R.drawable.emblem_hunter_female_dark,
            "건슬링어" to if(isDark) R.drawable.emblem_devil_hunter_female else R.drawable.emblem_devil_hunter_female_dark,
            "마법사" to if(isDark) R.drawable.emblem_magician else R.drawable.emblem_magician_dark,
            "바드" to if(isDark) R.drawable.emblem_bard else R.drawable.emblem_bard_dark,
            "서머너" to if(isDark) R.drawable.emblem_summoner else R.drawable.emblem_summoner_dark,
            "아르카나" to if(isDark) R.drawable.emblem_arcana else R.drawable.emblem_arcana_dark,
            "소서리스" to if(isDark) R.drawable.emblem_elemental_master else R.drawable.emblem_elemental_master_dark,
            "암살자" to if(isDark) R.drawable.emblem_delain else R.drawable.emblem_delain_dark,
            "블레이드" to if(isDark) R.drawable.emblem_blade else R.drawable.emblem_blade_dark,
            "데모닉" to if(isDark) R.drawable.emblem_demonic else R.drawable.emblem_demonic_dark,
            "리퍼" to if(isDark) R.drawable.emblem_reaper else R.drawable.emblem_reaper_dark,
            "소울이터" to if(isDark) R.drawable.emblem_soul_eater else R.drawable.emblem_soul_eater_dark,
            "스페셜리스트" to if(isDark) R.drawable.emblem_specialist else R.drawable.emblem_specialist_dark,
            "도화가" to if(isDark) R.drawable.emblem_yinyangshi else R.drawable.emblem_yinyangshi_dark,
            "기상술사" to if(isDark) R.drawable.emblem_weather_artist else R.drawable.emblem_weather_artist_dark,
        )

        return classImageMap[characterClassName] ?: R.drawable.emblem_specialist
    }
}