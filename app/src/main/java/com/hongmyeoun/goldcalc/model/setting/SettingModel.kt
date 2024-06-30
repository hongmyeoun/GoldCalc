package com.hongmyeoun.goldcalc.model.setting

import android.content.Context
import java.io.File

object SettingModel {

    fun clearAppCache(context: Context, onCleared: () -> Unit) {
        try {
            val cacheDir: File = context.cacheDir
            cacheDir.deleteRecursively()
            onCleared()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCacheSize(context: Context): Long {
        return getFolderSize(context.cacheDir)
    }

    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        if (file.isDirectory) {
            for (child in file.listFiles() ?: emptyArray()) {
                size += getFolderSize(child)
            }
        } else {
            size += file.length()
        }
        return size
    }
}