package com.example.habitstracker.data

import androidx.annotation.WorkerThread
import com.example.habitstracker.data.room.HabitSQLEntity
import com.example.habitstracker.data.room.HabitsDAO
import com.example.habitstracker.domain.IHabitsSQLRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class HabitsLocalSQLRepository(private val habitsDAO: HabitsDAO) : IHabitsSQLRepository {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val listHabits: Flow<List<HabitSQLEntity>> = habitsDAO.getHabitsList()
//    val listBadHabits = habitsDAO.getBadHabitsList()
//    val listGoodHabits = habitsDAO.getGoodHabitsList()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(habitSQLEntity: HabitSQLEntity) {
        withContext(Dispatchers.IO) { habitsDAO.insert(habitSQLEntity) }
    }

    @WorkerThread
    override suspend fun update(habitSQLEntity: HabitSQLEntity) {
        withContext(Dispatchers.IO) { habitsDAO.update(habitSQLEntity) }
    }


    override suspend fun getItem(id: Int): HabitSQLEntity {
        return withContext(Dispatchers.IO) { habitsDAO.getItem(id) }
    }

    override suspend fun getLastItem(): HabitSQLEntity {
        return habitsDAO.getLastItem()
    }
}