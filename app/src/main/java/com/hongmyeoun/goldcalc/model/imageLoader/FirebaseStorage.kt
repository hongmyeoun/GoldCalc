package com.hongmyeoun.goldcalc.model.imageLoader

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseStorage {
    companion object {
        fun getFirebaseImageUrl(imagePath: String, onUrlReceived: (String) -> Unit) {
            val storageRef = Firebase.storage.reference.child(imagePath)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
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
}