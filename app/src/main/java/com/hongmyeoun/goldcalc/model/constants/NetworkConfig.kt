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
        const val ARK_PASSIVE = ARMORIES_ENTER_POINT + "arkpassive"
        const val ARK_GRID = ARMORIES_ENTER_POINT + "arkgrid"

        const val CHARACTER_THUMB = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/"

        const val ITEM_LEVEL_ICON = "https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_12.png"
        const val COMBAT_POWER_ICON ="https://cdn-lostark.game.onstove.com/efui_iconatlas/tooltip/tooltip_0_11.png"
        const val TRANSCENDENCE_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_tooltip_transcendence.png"

        const val ARK_PASSIVE_ACTIVATION_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/ico_arkpassive.png"
        const val ARK_PASSIVE_ACC = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_arkpassive2.png"
        const val ARK_PASSIVE_EQUIP = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_arkpassive.png"
        const val API_HEADER = "Authorization"

        const val NO_HEAD_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot1.png"
        const val NO_SHOULDER_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot2.png"
        const val NO_CHEST_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot3.png"
        const val NO_PANTS_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot4.png"
        const val NO_GLOVES_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot5.png"
        const val NO_WEAPON_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot6.png"

        const val NO_NECKLACE_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot7.png"
        const val NO_EARRINGS_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot8.png"
        const val NO_EARRINGS_ICON_2 = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot9.png"
        const val NO_RINGS_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot10.png"
        const val NO_RINGS_ICON_2 = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot11.png"

        const val NO_ABILLITY_STONE_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot12.png"

        const val NO_BRACELET_ICON = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_slot19.png"

        const val END_POINT_PNG = ".png"

        const val NETWORK_ERROR = "네트워크 오류"
    }
}
