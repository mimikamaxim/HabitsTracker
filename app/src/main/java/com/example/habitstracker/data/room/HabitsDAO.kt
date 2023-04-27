package com.example.habitstracker.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Room Magic is in this file, where you map a method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface HabitsDAO {
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM habits_table")
    fun getHabitsList(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits_table WHERE isGood = false")
    fun getBadHabitsList(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits_table WHERE isGood = true")
    fun getGoodHabitsList(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits_table WHERE id = :id")
    suspend fun getItem(id: Int): HabitEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitEntity: HabitEntity)

    @Update
    suspend fun update(habitEntity: HabitEntity)

    @Delete
    suspend fun delete(habitEntity: HabitEntity)

    @Query("DELETE FROM habits_table")
    suspend fun deleteAll()

    @Query(
        "SELECT * FROM habits_table\n" +
                "ORDER BY id DESC\n" +
                "LIMIT 1;\n"
    )
    fun getLastItem(): HabitEntity
}