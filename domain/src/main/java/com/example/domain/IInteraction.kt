package com.example.domain

import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.flow.Flow

//to domain
interface IInteraction {
    fun getPresentationList(): Flow<List<DomainHabitEntity>>
    fun addNewHabitFromPresentation(habit: DomainHabitEntity)
    fun updateHabitFromPresentation(habit: DomainHabitEntity)

    suspend fun getPresentationHabit(id: Int): DomainHabitEntity
    fun deleteHabit(id: Int)
    suspend fun addDone(id: Int): Pair<ResultAddDate, Int>
}