package com.example.habitstracker.domain

import com.example.habitstracker.data.room.HabitEntity
import kotlinx.coroutines.flow.Flow

interface IHabitsRepository {
    val listHabits: Flow<List<HabitEntity>>

    suspend fun insert(habitEntity: HabitEntity)

    suspend fun update(habitEntity: HabitEntity)

    suspend fun getItem(id: Int): HabitEntity
}