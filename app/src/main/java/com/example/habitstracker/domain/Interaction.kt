package com.example.habitstracker.domain

import com.example.habitstracker.HabitsApplication.Companion.applicationScope
import com.example.habitstracker.data.HabitsRepository
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.data.room.HabitsRoomDatabase
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.MainActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object Interaction {
    val database by lazy { HabitsRoomDatabase.getDatabase(MainActivity.contextBase!!) }
    val repository by lazy { HabitsRepository(database.habitsDAO()) }
    private val dataList: Flow<List<HabitEntity>> = repository.listHabits

    fun getPresentationList() = dataList.map { list ->
        Mapper.dataListToPresentationList(list)
    }

    fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
            repository.insert(Mapper.presentationHabitToDataEntity(habit))
        }
    }

    fun updateHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
            repository.update(Mapper.presentationHabitToDataEntity(habit))
        }
    }

    suspend fun getPresentationHabit(id: Int): HabitItemPresentationModel {
        val habit = repository.getItem(id)
        return Mapper.dataEntityToPresentationModel(habit)
    }
}