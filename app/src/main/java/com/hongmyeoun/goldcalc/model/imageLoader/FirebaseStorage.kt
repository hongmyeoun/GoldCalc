package com.hongmyeoun.goldcalc.model.imageLoader

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.hongmyeoun.goldcalc.model.constants.raid.Raid

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

        fun getImagePath(raidName: String) : String {
            return FirebaseStoragePath.RaidImage.ROOT + raidImgPath(raidName) + FirebaseStoragePath.JPG
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