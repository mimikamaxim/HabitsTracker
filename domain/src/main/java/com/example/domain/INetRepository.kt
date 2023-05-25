package com.example.domain

import com.example.domain.entitys.DomainHabitEntity


interface INetRepository {
    suspend fun getNetHabits(): List<DomainHabitEntity>

    /**
     * after success upload returns server uid
     */
    suspend fun uploadNewHabit(habit: DomainHabitEntity): String

    /**
     * date should be bigger than existing
     * done dates list ignored by server
     */
    suspend fun updateHabit(habit: DomainHabitEntity)

    suspend fun addDoneDate(date: Long, iud: String)

    suspend fun deleteHabit(uid: String)
}