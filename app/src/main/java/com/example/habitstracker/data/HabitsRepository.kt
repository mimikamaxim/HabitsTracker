package com.example.habitstracker.data

import androidx.annotation.WorkerThread
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.data.room.HabitsDAO
import com.example.habitstracker.domain.IHabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class HabitsRepository(private val habitsDAO: HabitsDAO) : IHabitsRepository {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val listHabits: Flow<List<HabitEntity>> = habitsDAO.getHabitsList()
//    val listBadHabits = habitsDAO.getBadHabitsList()
//    val listGoodHabits = habitsDAO.getGoodHabitsList()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(habitEntity: HabitEntity) {
        withContext(Dispatchers.IO) { habitsDAO.insert(habitEntity) }
    }

    @WorkerThread
    override suspend fun update(habitEntity: HabitEntity) {
        withContext(Dispatchers.IO) { habitsDAO.update(habitEntity) }
    }


    override suspend fun getItem(id: Int): HabitEntity {
        return withContext(Dispatchers.IO) { habitsDAO.getItem(id) }
    }

    fun getLastItem(): HabitEntity {
        return habitsDAO.getLastItem()
    }
}