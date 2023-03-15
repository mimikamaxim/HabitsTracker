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
        habitItems.add(HabitItem("Читать новости", "Читать свежие новости", 5, false, 1, "Раз в день",Color.GRAY))
        habitItems.add(HabitItem("Физические упражнения", "Заниматься физической нагрузкой в течении часа", 5, true, 1, "Раз в неделю",Color.RED))
        habitItems.add(HabitItem("Магазин", "Ходить за продуктами", 3, true, 1, "Раз в неделю", Color.CYAN))
        habitItems.add(HabitItem("Уборка", "Прибраться в комнате", 1, true, 1, "Раз в неделю", Color.MAGENTA))
        habitItems.add(HabitItem("Ходить в бар","Ходить с друзьями в бар",3,false,2,"Раз в месяц",Color.YELLOW))
    }
}