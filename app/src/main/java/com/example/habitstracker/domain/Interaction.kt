package com.example.habitstracker.domain

import com.example.habitstracker.data.HabitsRepository
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.data.room.HabitsRoomDatabase
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Interaction {
    //    private val repository = HabitsApplication().repository
    val database by lazy { HabitsRoomDatabase.getDatabase(MainActivity.contextBase!!) }
    val repository by lazy { HabitsRepository(database.habitsDAO()) }
    private val scope = CoroutineScope(SupervisorJob())

    private val dataList: Flow<List<HabitEntity>> =
        repository.listHabits

    fun getPresentationList() = dataList.map { list ->
        Mapper.dataListToPresentationList(list)
    }

    fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        scope.launch {
            withContext(Dispatchers.IO) {
                repository.insert(Mapper.presentationHabitToDataEntity(habit))
            }
        }
    }

    fun updateHabitFromPresentation(habit: HabitItemPresentationModel) {
        scope.launch {
            withContext(Dispatchers.IO) {
                repository.update(Mapper.presentationHabitToDataEntity(habit))
            }
        }
    }

    suspend fun getPresentationHabit(id: Int): HabitItemPresentationModel {
        val habit = withContext(Dispatchers.IO) { repository.getItem(id) }
        return Mapper.dataEntityToPresentationModel(habit)
    }
}