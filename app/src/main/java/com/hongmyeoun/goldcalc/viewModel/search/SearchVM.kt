package com.hongmyeoun.goldcalc.viewModel.search

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterInfo
import com.hongmyeoun.goldcalc.model.lostArkApi.LostArkApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class SearchVM : ViewModel() {
    private val _characterName = MutableStateFlow("")
    val characterName: StateFlow<String> = _characterName

    fun onCharacterNameValueChange(newValue: String) {
        _characterName.value = newValue
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _characterList = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characterList: StateFlow<List<CharacterInfo>> = _characterList

    private fun getCharacterList(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val info = getCharacter(context, _characterName.value)
            _characterList.value = info ?: emptyList()
        }
    }

    private fun loadingTrue() {
        _isLoading.value = true
    }

    private fun loadingFalse() {
        _isLoading.value = false
    }

    fun onDone(context: Context) {
        loadingTrue()
        getCharacterList(context)
        loadingFalse()
    }

    private suspend fun getCharacter(context: Context, characterName: String): List<CharacterInfo>? {
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