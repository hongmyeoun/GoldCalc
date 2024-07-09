package com.hongmyeoun.goldcalc.model.profile.gem

import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts

class GemDetail(private val gemAndEffect: GemAndEffect) {
    fun getCharacterGemDetails(): List<Gem> {
        val gemMap = gemAndEffect.gems.associateBy { it.slot }

        return gemAndEffect.effects.mapNotNull { effect ->
            gemMap[effect.gemSlot]?.let { gem ->
                val type = if (effect.description.trim().split(" ")
                        .last() == EquipmentConsts.INCREASE
                ) EquipmentConsts.DEAL_GEM_3_TIER else EquipmentConsts.COOLTIME_GEM_3_TIER
                Gem(
                    type = type,
                    grade = gem.grade,
                    level = gem.level,
                    gemIcon = gem.icon,
                    skillIcon = effect.icon,
                    skill = effect.name,
                    effect = effect.description,
                )
            }
        }
    }
}