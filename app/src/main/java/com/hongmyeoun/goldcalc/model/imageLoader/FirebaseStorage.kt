package com.hongmyeoun.goldcalc.model.imageLoader

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.hongmyeoun.goldcalc.model.constants.ClassName
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseStorage {
    companion object {
        fun getFirebaseImageUrl(imagePath: String, onUrlReceived: (String) -> Unit) {
            val storageRef = Firebase.storage.reference.child(imagePath)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d("헤응", "$uri")
                onUrlReceived(uri.toString())
            }.addOnFailureListener {
                Log.e("Firebase", "이미지 URL 가져오기 실패", it)
            }
        }

        suspend fun getUrlList(raidNames: List<String>, pathProvider: (String) -> String): List<String?> {
            return raidNames.map { raidName ->
                val imagePath = pathProvider(raidName)
                suspendCoroutine { continuation ->
                    getFirebaseImageUrl(imagePath) { url ->
                        continuation.resume(url)
                    }
                }
            }
        }
    }

    object RaidImageLoader {
        fun getRaidMainPath(raidName: String) : String {
            return FirebaseStoragePath.RaidImage.MAIN_ROUTE + raidImgPath(raidName) + FirebaseStoragePath.JPG
        }

        fun getRaidLogoPath(raidName: String) : String {
            return FirebaseStoragePath.RaidImage.LOGO_ROUTE + raidImgPath(raidName) + FirebaseStoragePath.PNG
        }

        private fun raidImgPath(raidName: String) : String {
            return when (raidName) {
                Raid.Name.VALTAN -> FirebaseStoragePath.RaidImage.COMMAND + Raid.EngName.VALTAN
                Raid.Name.BIACKISS -> FirebaseStoragePath.RaidImage.COMMAND + Raid.EngName.BIACKISS
                Raid.Name.KOUKU_SATON -> FirebaseStoragePath.RaidImage.COMMAND + Raid.EngName.KOUKU_SATON
                Raid.Name.ABRELSHUD -> FirebaseStoragePath.RaidImage.COMMAND + Raid.EngName.ABRELSHUD
                Raid.Name.ILLIAKAN -> FirebaseStoragePath.RaidImage.COMMAND + Raid.EngName.ILLIAKAN
                Raid.Name.KAMEN -> FirebaseStoragePath.RaidImage.COMMAND + Raid.EngName.KAMEN
                Raid.Name.KAYANGEL -> FirebaseStoragePath.RaidImage.ABYSS_DUNGEON + Raid.EngName.KAYANGEL
                Raid.Name.IVORY_TOWER_LONG -> FirebaseStoragePath.RaidImage.ABYSS_DUNGEON + Raid.EngName.IVORY_TOWER
                Raid.Name.BEHEMOTH -> FirebaseStoragePath.RaidImage.EPIC + Raid.EngName.BEHEMOTH
                Raid.Name.ECHIDNA -> FirebaseStoragePath.RaidImage.KAZEROTH + Raid.EngName.ECHIDNA
                Raid.Name.EGIR -> FirebaseStoragePath.RaidImage.KAZEROTH + Raid.EngName.EGIR
                Raid.Name.ABRELSHUD_2 -> FirebaseStoragePath.RaidImage.KAZEROTH + Raid.EngName.ABRELSHUD
                Raid.Name.MORDUM -> FirebaseStoragePath.RaidImage.KAZEROTH + Raid.EngName.MORDUM
                else -> FirebaseStoragePath.RaidImage.KAZEROTH + Raid.EngName.ECHIDNA
            }
        }
    }

    object CharacterImageLoader {
        fun getCharDetailPath(characterClassName: String): String {
            return FirebaseStoragePath.CharacterImage.MAIN_ROUTE + FirebaseStoragePath.CharacterImage.DETAIL + characterImagePath(characterClassName, false) + FirebaseStoragePath.PNG
        }

        fun getCharEmblemPath(characterClassName: String): String {
            return FirebaseStoragePath.CharacterImage.MAIN_ROUTE + FirebaseStoragePath.CharacterImage.EMBLEM + characterImagePath(characterClassName, true) + FirebaseStoragePath.PNG
        }

        private fun characterImagePath(characterClassName: String, isEmblem: Boolean): String {
            val classImageMap = mapOf(
                ClassName.Warrior.DEFAULT to if (isEmblem) ClassName.Warrior.DEFAULT_EN else ClassName.Warrior.WARLOARD_EN,
                ClassName.Warrior.DESTROYER to ClassName.Warrior.DESTROYER_EN,
                ClassName.Warrior.WARLOARD to ClassName.Warrior.WARLOARD_EN,
                ClassName.Warrior.BERSERKER to ClassName.Warrior.BERSERKER_EN,
                ClassName.Warrior.HOLYKNIGHT to ClassName.Warrior.HOLYKNIGHT_EN,

                ClassName.Warrior.DEFAULT_FEMALE to if (isEmblem) ClassName.Warrior.DEFAULT_FEMALE_EN else ClassName.Warrior.SLAYER_EN2,
                ClassName.Warrior.SLAYER to ClassName.Warrior.SLAYER_EN2,

                ClassName.Fighter.DEFAULT_MALE to if (isEmblem) ClassName.Fighter.DEFAULT_MALE_EN else ClassName.Fighter.STRIKER_EN2,
                ClassName.Fighter.STRIKER to ClassName.Fighter.STRIKER_EN2,
                ClassName.Fighter.BREAKER to ClassName.Fighter.BREAKER_EN2,

                ClassName.Fighter.DEFAULT to if (isEmblem) ClassName.Fighter.DEFAULT_EN else ClassName.Fighter.BATTLE_MASTER_EN,
                ClassName.Fighter.BATTLE_MASTER to ClassName.Fighter.BATTLE_MASTER_EN,
                ClassName.Fighter.INFIGHTER to ClassName.Fighter.INFIGHTER_EN,
                ClassName.Fighter.SOUL_MASTER to ClassName.Fighter.SOUL_MASTER_EN,
                ClassName.Fighter.LANCE_MASTER to ClassName.Fighter.LANCE_MASTER_EN,

                ClassName.Hunter.DEFAULT to if (isEmblem) ClassName.Hunter.DEFAULT_EN else ClassName.Hunter.DEVILHUNTER_EN,
                ClassName.Hunter.DEVILHUNTER to ClassName.Hunter.DEVILHUNTER_EN,
                ClassName.Hunter.BLASTER to ClassName.Hunter.BLASTER_EN,
                ClassName.Hunter.HAWKEYE to ClassName.Hunter.HAWKEYE_EN,
                ClassName.Hunter.SCOUTER to ClassName.Hunter.SCOUTER_EN,

                ClassName.Hunter.DEFAULT_FEMALE to if (isEmblem) ClassName.Hunter.DEFAULT_FEMALE_EN else ClassName.Hunter.GUNSLINGER_EN2,
                ClassName.Hunter.GUNSLINGER to ClassName.Hunter.GUNSLINGER_EN2,

                ClassName.Magician.DEFAULT to if (isEmblem) ClassName.Magician.DEFAULT_EN else ClassName.Magician.SUMMONER_EN,
                ClassName.Magician.BARD to ClassName.Magician.BARD_EN,
                ClassName.Magician.SUMMONER to ClassName.Magician.SUMMONER_EN,
                ClassName.Magician.ARCANA to ClassName.Magician.ARCANA_EN,
                ClassName.Magician.SORCERESS to ClassName.Magician.SORCERESS_EN,

                ClassName.Delain.DEFAULT to if(isEmblem) ClassName.Delain.DEFAULT_EN else ClassName.Delain.REAPER_EN,
                ClassName.Delain.BLADE to ClassName.Delain.BLADE_EN,
                ClassName.Delain.DEMONIC to ClassName.Delain.DEMONIC_EN,
                ClassName.Delain.REAPER to ClassName.Delain.REAPER_EN,
                ClassName.Delain.SOUL_EATER to ClassName.Delain.SOUL_EATER_EN2,

                ClassName.Specialist.DEFAULT to if(isEmblem) ClassName.Specialist.DEFAULT_EN else ClassName.Specialist.ARTIST_EN,
                ClassName.Specialist.ARTIST to ClassName.Specialist.ARTIST_EN,
                ClassName.Specialist.AEROMANCER to ClassName.Specialist.AEROMANCER_EN2,
                ClassName.Specialist.ALCHEMIST to ClassName.Specialist.ALCHEMIST_EN2,
            )

            return classImageMap[characterClassName] ?: ClassName.Specialist.ALCHEMIST_EN2
        }
    }
}