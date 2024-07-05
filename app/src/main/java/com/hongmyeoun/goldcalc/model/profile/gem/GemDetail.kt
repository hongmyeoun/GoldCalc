package com.hongmyeoun.goldcalc.model.profile.gem

class GemDetail(private val gemAndEffect: GemAndEffect) {
    fun getCharacterGemDetails(): List<Gem> {
        val gemMap = gemAndEffect.gems.associateBy { it.slot }

        return gemAndEffect.effects.mapNotNull { effect ->
            gemMap[effect.gemSlot]?.let { gem ->
                val type = if (effect.description.trim().split(" ").last() == "증가") "멸화" else "홍염"
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