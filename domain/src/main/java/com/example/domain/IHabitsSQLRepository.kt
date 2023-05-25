package com.example.domain

import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.flow.Flow

interface IHabitsSQLRepository {
    val listHabits: Flow<List<DomainHabitEntity>>

    suspend fun insert(habit: DomainHabitEntity): Long

    suspend fun update(habit: DomainHabitEntity)

    suspend fun getItem(id: Int): DomainHabitEntity

    suspend fun getLastItem(): DomainHabitEntity

    suspend fun addRemoteUid(id: Long, uid: String)

    suspend fun delete(id: Long)
}