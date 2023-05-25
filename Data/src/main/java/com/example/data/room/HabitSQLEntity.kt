package com.example.data.room

import androidx.room.*

@Entity(tableName = "habits_table")
@TypeConverters(MyConverter::class)
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
    @ColumnInfo var doneDates: MutableList<Long> = mutableListOf(),
    @ColumnInfo var totalCompleteTimes: Int,
    @ColumnInfo var currentCompleteTimes: Int,
    @ColumnInfo val lastEditData: Long,
    @ColumnInfo var initialDate: Long,
    @ColumnInfo var listEditDates: List<Long>
)

private class MyConverter {
    @TypeConverter
    fun fromListIntToString(list: List<Long>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListIntFromString(string: String): List<Long> {
        return if (string.isNotEmpty()) string.split(",").map { it.toLong() }
        else listOf()
    }

    @TypeConverter
    fun fromStringToLong(string: String): Long {
        return string.toLong()
    }

    @TypeConverter
    fun longToString(long: Long): String {
        return long.toString()
    }
}
