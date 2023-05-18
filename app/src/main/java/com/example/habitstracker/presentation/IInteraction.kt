package com.example.habitstracker.presentation

import kotlinx.coroutines.flow.Flow

interface IInteraction {
    fun getPresentationList(): Flow<List<HabitItemPresentationModel>>
    fun addNewHabitFromPresentation(habit: HabitItemPresentationModel)
    fun updateHabitFromPresentation(habit: HabitItemPresentationModel)

    suspend fun getPresentationHabit(id: Int): HabitItemPresentationModel
    fun deleteHabit(id: Int)
    fun addDone(id: Int)
}