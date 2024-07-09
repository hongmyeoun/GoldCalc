package com.hongmyeoun.goldcalc.model.constants

class NetworkConfig {
    companion object {
        const val DEV_URL = "https://developer-lostark.game.onstove.com"

        const val SEARCH_PATH = "characterName"

        const val SIBLINGS = "/characters/{characterName}/siblings"

        private const val ARMORIES_ENTER_POINT = "/armories/characters/{characterName}/"
        const val PROFILES = ARMORIES_ENTER_POINT + "profiles"
        const val EQUIPMENT = ARMORIES_ENTER_POINT + "equipment"
        const val GEMS = ARMORIES_ENTER_POINT + "gems"
        const val CARDS = ARMORIES_ENTER_POINT + "cards"
        const val COMBAT_SKILLS = ARMORIES_ENTER_POINT + "combat-skills"
        const val ENGRAVINGS = ARMORIES_ENTER_POINT + "engravings"

        const val CHARACTER_THUMB = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/"

        const val ITEM_LEVEL_ICON = "https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_12.png"
        const val TRANSCENDENCE_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png"

        const val API_HEADER = "Authorization"

        const val END_POINT_PNG = ".png"

        const val NETWORK_ERROR = "네트워크 오류"
    }
}
