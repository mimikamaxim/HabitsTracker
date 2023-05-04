package com.example.habitstracker.domain

import com.example.habitstracker.data.room.HabitSQLEntity
import kotlinx.coroutines.flow.Flow

interface IHabitsSQLRepository {
    val listHabits: Flow<List<HabitSQLEntity>>

    suspend fun insert(habitSQLEntity: HabitSQLEntity)

    suspend fun update(habitSQLEntity: HabitSQLEntity)

    suspend fun getItem(id: Int): HabitSQLEntity

    suspend fun getLastItem(): HabitSQLEntity
}