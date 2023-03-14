package com.example.habitstracker.data

import android.graphics.Color

class HabitItemsDB {
    private val habitItems = mutableListOf<HabitItem>()

    fun getHabitItemsList(): List<HabitItem> {
        return habitItems
    }

    fun addHabit(habit: HabitItem) {
        habitItems.add(habit)
    }

    fun getHabit(i: Int) = habitItems[i]

    fun updateHabit(i: Int, habit: HabitItem) {
        habitItems[i] = habit
    }

    fun fillDBsample() {
        habitItems.add(HabitItem("one", "the first", 5, true, 1, Color.GRAY))
        habitItems.add(HabitItem("two", "the second", 5, false, 1, Color.RED))
        habitItems.add(HabitItem("three", "sample", 3, true, 1, Color.CYAN))
        habitItems.add(HabitItem("Four habit", "Four habit description", 1, true, 1, Color.MAGENTA))
    }
}