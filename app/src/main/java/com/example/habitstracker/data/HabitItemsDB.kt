package com.example.habitstracker.data

import android.graphics.Color

object HabitItemsDB {
    private val habitItems = mutableListOf<HabitItem>()

    fun getHabitItemsList(): List<HabitItem> {
        return habitItems
    }

    fun addHabit(habit: HabitItem) {
        habitItems.add(
            HabitItem(
                habit.name,
                habit.description,
                habit.priority,
                habit.isGood,
                habit.amountDone,
                habit.period,
                habit.color,
                habitItems.size
            )
        )
    }

    fun getHabit(i: Int) = habitItems[i]

    fun updateHabit(position: Int, habit: HabitItem) {
        habitItems[position] = HabitItem(
            habit.name,
            habit.description,
            habit.priority,
            habit.isGood,
            habit.amountDone,
            habit.period,
            habit.color,
            position
        )
    }

    fun fillDBsample() {
        addHabit(HabitItem("Читать новости", "Читать свежие новости", 2, false, 1, "Раз в день",Color.GRAY))
        addHabit(HabitItem("Физические упражнения", "Заниматься физической нагрузкой в течении часа", 0, true, 1, "Раз в неделю",Color.RED))
        addHabit(HabitItem("Магазин", "Ходить за продуктами", 3, true, 1, "Раз в неделю", Color.CYAN))
        addHabit(HabitItem("Уборка", "Прибраться в комнате", 1, true, 1, "Раз в неделю", Color.MAGENTA))
        addHabit(HabitItem("Ходить в бар","Ходить с друзьями в бар",3,false,2,"Раз в месяц",Color.YELLOW))
    }
}