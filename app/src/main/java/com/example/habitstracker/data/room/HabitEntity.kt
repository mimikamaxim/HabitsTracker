package com.example.habitstracker.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits_table")
data class HabitEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: Int,
    @ColumnInfo val isGood: Boolean,
    @ColumnInfo val amountDone: Int,
    @ColumnInfo val period: String,
    @ColumnInfo val color: Int,
    @PrimaryKey(true) val id: Int? = null,
    @ColumnInfo var uid: String? = null
)
