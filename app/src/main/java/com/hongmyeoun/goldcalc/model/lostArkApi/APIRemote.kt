package com.hongmyeoun.goldcalc.model.lostArkApi

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.hongmyeoun.goldcalc.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object APIRemote {
    suspend fun getCharacter(context: Context, characterName: String): List<CharacterInfo>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

            try {
                val response = lostArkApiService.getCharacters(characterName).execute()
                if (response.isSuccessful) {
                    var characterInfoList = response.body()
                    characterInfoList = characterInfoList?.sortedByDescending { it.itemMaxLevel }
                    characterInfoList
                } else {
                    // 실패 처리
                    Log.d("실패", "서버 응답 실패: ${response.code()}")
                    null
                }
            } catch (e: IOException) {
                // 네트워크 오류 처리
                Log.d("실패", "네트워크 오류: $e")
                null
            }
        }
    }

    suspend fun getCharDetail(context: Context, characterName: String): CharacterDetail? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

            try {
                val response = lostArkApiService.getCharacterDetail(characterName).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.d("실패", "서버 응답 실패: ${response.code()}")
                    null
                }
            } catch (e: IOException) {
                Log.d("실패", "네트워크 오류: $e")
                null
            }
        }
    }

    private fun authorizationHeader(context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", ContextCompat.getString(context, R.string.lost_ark_api))
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}