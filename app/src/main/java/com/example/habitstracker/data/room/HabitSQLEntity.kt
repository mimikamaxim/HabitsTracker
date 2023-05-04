package com.example.habitstracker.data.room

import androidx.room.*

@Entity(tableName = "habits_table")
@TypeConverters(ListConverter::class)
data class HabitSQLEntity(
    @ColumnInfo(name = "name") val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: Int,
    @ColumnInfo val isGood: Boolean,
    @ColumnInfo val amountDone: Int,
    @ColumnInfo val period: String,
    @ColumnInfo val color: Int,
    @PrimaryKey(true) val id: Int? = null,
    /**
     * Represents Remote repository id
     */
    @ColumnInfo val uid: String? = null,
    @ColumnInfo val lastEditData: Long = System.currentTimeMillis(),
    @ColumnInfo val initialDate: Long = System.currentTimeMillis(),
    @ColumnInfo var doneDates: List<Int> = listOf()
)

private class ListConverter {
    @TypeConverter
    fun fromListIntToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListIntFromString(string: String): List<Int> {
        return string.split(",").map { it.toInt() }
    }
}
