package com.example.data

import androidx.annotation.WorkerThread
import com.example.data.room.HabitSQLEntity
import com.example.data.room.HabitsDAO
import com.example.domain.IHabitsSQLRepository
import com.example.domain.Mapper
import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class HabitsLocalSQLRepository(private val habitsDAO: HabitsDAO) : IHabitsSQLRepository {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val listHabits: Flow<List<DomainHabitEntity>> = habitsDAO.getHabitsList().map {
        Mapper.sqlListToDomainList(it)
    }
//    val listBadHabits = habitsDAO.getBadHabitsList()
//    val listGoodHabits = habitsDAO.getGoodHabitsList()

    override suspend fun insert(habit: DomainHabitEntity): Long {
        return insert(
            Mapper.domainToSqlEntity(
                habit,
                habit.uid.ifEmpty { null }
            )
        )
    }

    override suspend fun update(habit: DomainHabitEntity) {
        update(Mapper.domainToSqlEntity(
            habit,
            habit.uid.ifEmpty { null }
        ))
    }

    override suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            val habit = habitsDAO.getItem(id.toInt())
            delete(habit)
        }
    }

    override suspend fun addRemoteUid(id: Long, uid: String) {
        withContext(Dispatchers.IO) { habitsDAO.addRemoteUid(id.toInt(), uid) }
    }


    override suspend fun getItem(id: Int): DomainHabitEntity {
        return withContext(Dispatchers.IO) {
            Mapper.sqlEntityToDomainEntity(habitsDAO.getItem(id))
        }
    }

    override suspend fun getLastItem(): DomainHabitEntity {
        return withContext(Dispatchers.IO) {
            Mapper.sqlEntityToDomainEntity(habitsDAO.getLastItem())
        }
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun insert(habitSQLEntity: HabitSQLEntity): Long {
        return withContext(Dispatchers.IO) { habitsDAO.insert(habitSQLEntity) }
    }

    @WorkerThread
    private suspend fun update(habitSQLEntity: HabitSQLEntity) {
        withContext(Dispatchers.IO) { habitsDAO.update(habitSQLEntity) }
    }

    private suspend fun delete(habitSQLEntity: HabitSQLEntity) {
        withContext(Dispatchers.IO) { habitsDAO.delete(habitSQLEntity) }
    }
}