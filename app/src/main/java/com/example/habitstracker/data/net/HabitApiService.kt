package com.example.habitstracker.data.net

import com.example.habitstracker.data.net.entity.DoneDateEntity
import com.example.habitstracker.data.net.entity.NetHabitEntity
import com.example.habitstracker.data.net.entity.NetNewHabitEntity
import com.example.habitstracker.data.net.entity.UidEntity
import retrofit2.http.*

interface HabitApiService {

    @GET("api/habit")
    suspend fun getHabits(): List<NetHabitEntity>

    @PUT("api/habit")
    suspend fun uploadNewHabit(@Body body: NetNewHabitEntity): UidEntity

    @PUT("api/habit")
    suspend fun updateExistingHabit(@Body body: NetHabitEntity)//OK

    @POST("api/habit_done")
    suspend fun addDoneHabitDate(@Body body: DoneDateEntity)//OK

    @HTTP(method = "DELETE", hasBody = true, path = "api/habit")
//    @DELETE("api/habit")
    suspend fun deleteHabit(@Body body: UidEntity)//OK
}

