package com.example.habitstracker.domain

import com.example.habitstracker.data.room.HabitEntity

interface IHabitsRepository {
    suspend fun insert(habitEntity: HabitEntity)

    suspend fun update(habitEntity: HabitEntity)

    suspend fun getItem(id: Int): HabitEntity
}