package com.example.data

import android.util.Log
import com.example.data.net.HabitApiService
import com.example.data.net.RetryInterceptor
import com.example.data.net.entity.DoneDateEntity
import com.example.data.net.entity.NetHabitEntity
import com.example.data.net.entity.NetNewHabitEntity
import com.example.data.net.entity.UidEntity
import com.example.domain.INetRepository
import com.example.domain.Mapper
import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetRepository @Inject constructor(var scope: CoroutineScope) : INetRepository {
    //    private val scope = CoroutineScope(SupervisorJob())
    private val habitApiService = createApiService()
    private val token = "9588724c-8c76-4cb5-9a54-0dfd834c02f4"
    private val authorization = "Authorization"
    private val baseUrl = "https://droid-test-server.doubletapp.ru/"
    private val accept = "accept"
    private val appType = "application/json"

    private fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            newBuilder.addHeader(authorization, token)
            newBuilder.addHeader(accept, appType)
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

    //_____________________________________

    override suspend fun getNetHabits(): List<DomainHabitEntity> {
        val job = scope.async(Dispatchers.IO) {
            try {
                val habits = habitApiService.getHabits()
                Mapper.listNetToListDomain(habits)
            } catch (e: Exception) {
                Log.e("NetErr ", "Error: ${e.message}")
                throw e
            }
        }
        return job.await()
    }

    override suspend fun uploadNewHabit(habit: DomainHabitEntity): String {
        return uploadNewNetHabit(Mapper.domainToNewNet(habit))
    }

    override suspend fun updateHabit(habit: DomainHabitEntity) {
        updateNetHabit(Mapper.domainToNet(habit))
    }

    override suspend fun addDoneDate(date: Long, iud: String) {
        val job = scope.launch {
            habitApiService.addDoneHabitDate(DoneDateEntity(date, iud))
        }
        job.join()
    }

    override suspend fun deleteHabit(uid: String) {
        val job = scope.launch {
            try {
                habitApiService.deleteHabit(UidEntity(uid))
            } catch (e: Exception) {
                throw e
            }
        }
        job.join()
    }

    private suspend fun updateNetHabit(habit: NetHabitEntity) {
        val job = scope.launch {
            habitApiService.updateExistingHabit(habit)
        }
        job.join()
    }

    private suspend fun uploadNewNetHabit(habit: NetNewHabitEntity): String {
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
}

