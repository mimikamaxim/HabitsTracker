package com.example.habitstracker.data.net

import android.util.Log
import com.example.habitstracker.HabitsApplication.Companion.applicationScope
import com.example.habitstracker.data.net.entity.DoneDateEntity
import com.example.habitstracker.data.net.entity.NetHabitEntity
import com.example.habitstracker.data.net.entity.NetNewHabitEntity
import com.example.habitstracker.data.net.entity.UidEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetRepository {
    private val scope = applicationScope
    private val habitApiService = createApiService()
    private val token = "9588724c-8c76-4cb5-9a54-0dfd834c02f4"

    private fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            newBuilder.addHeader("Authorization", token)
            newBuilder.addHeader("accept", "application/json")
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor())
            .addInterceptor(RetryInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private fun createApiService(): HabitApiService {
        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HabitApiService::class.java)
    }

    suspend fun getNetHabits(): List<NetHabitEntity> {
        val job = scope.async(Dispatchers.IO) {
            try {
                val habits = habitApiService.getHabits()
                habits
            } catch (e: Exception) {
                Log.e("NetErr ", "Error: ${e.message}")
                throw e
            }
        }
        return job.await()
    }

    suspend fun uploadNewHabit(habit: NetNewHabitEntity): String {
        val job = scope.async {
            try {
                val result = habitApiService.uploadNewHabit(habit)
                result.uid
            } catch (e: Exception) {
                Log.e("NetErr ", "Error: ${e.message}")
                throw e
            }
        }
        return job.await()
    }

    /**
     * date should be bigger than existing
     * done dates list ignored by server
     */
    suspend fun updateHabit(habit: NetHabitEntity) {
        val job = scope.launch {
            habitApiService.updateExistingHabit(habit)
        }
        job.join()
    }

    suspend fun addDoneDate(date: Int, iud: String) {
        val job = scope.launch {
            habitApiService.addDoneHabitDate(DoneDateEntity(date, iud))
        }
        job.join()
    }

    suspend fun deleteHabit(uid: String) {
        val job = scope.launch {
            try {
                habitApiService.deleteHabit(UidEntity(uid))
            } catch (e: Exception) {
                throw e
            }
        }
        job.join()
    }
}

