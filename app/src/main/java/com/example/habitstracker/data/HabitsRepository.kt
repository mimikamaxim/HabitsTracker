package com.example.habitstracker.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.data.room.HabitsDAO
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class HabitsRepository(private val habitsDAO: HabitsDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val listHabits: Flow<List<HabitEntity>> = habitsDAO.getHabitsList()
    val listBadHabits = habitsDAO.getBadHabitsList()
    val listGoodHabits = habitsDAO.getGoodHabitsList()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(habitEntity: HabitEntity) {
        habitsDAO.insert(habitEntity)
    }

    @WorkerThread
    suspend fun update(habitEntity: HabitEntity) {
        habitsDAO.update(habitEntity)
    }
}