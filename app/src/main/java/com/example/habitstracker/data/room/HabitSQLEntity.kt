package com.example.habitstracker.data.room

import androidx.room.*

@Entity(tableName = "habits_table")
@TypeConverters(ListConverter::class)
data class HabitSQLEntity(
    @PrimaryKey(true) val id: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: Int,
    @ColumnInfo val isGood: Boolean,
    @ColumnInfo val color: Int,
    /**
     * Represents amount execution in specified period
     */
    @ColumnInfo val frequencyOfAllowedExecutions: Int,
    /**
     * Represents time window between acceptable habit executions
     */
    @ColumnInfo val periodInDays: Int = 0,
    /**
     * Represents Remote repository id
     */
    @ColumnInfo val uid: String? = null,
    @ColumnInfo var doneDates: List<Long> = listOf(),
    @ColumnInfo var totalCompleteTimes: Int,
    @ColumnInfo var currentCompleteTimes: Int,
    @ColumnInfo val lastEditData: Long = System.currentTimeMillis(),
    @ColumnInfo val initialDate: Long = System.currentTimeMillis(),
    @ColumnInfo var listEditDates: List<Long>
)

private class ListConverter {
    @TypeConverter
    fun fromListIntToString(list: List<Long>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListIntFromString(string: String): List<Long> {
        return string.split(",").map { it.toLong() }
    }
}
