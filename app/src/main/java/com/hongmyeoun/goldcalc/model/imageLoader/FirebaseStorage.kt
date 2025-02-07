package com.hongmyeoun.goldcalc.model.imageLoader

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

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
    }
}