package com.example.habitstracker.data

import java.util.*

object HabitsListFilter {
    fun getBadHabitItemsList(): List<HabitItem> {
        val list = mutableListOf<HabitItem>()
        HabitItemsDB.getHabitItemsList().forEach {
            if (!it.isGood) list.add(it)
        }
        return list
    }

    fun getGoodHabitItemsList(): List<HabitItem> {
        val list = mutableListOf<HabitItem>()
        HabitItemsDB.getHabitItemsList().forEach {
            if (it.isGood) list.add(it)
        }
        return list
    }

    fun findByHabitName(request: String, originalList: List<HabitItem>): List<HabitItem> {
        val list = mutableListOf<HabitItem>()
        if (request.isEmpty()) return originalList
        originalList.forEach {
            if (it.name.lowercase(Locale.getDefault())
                    .contains(request.lowercase(Locale.getDefault()))
            )
                list.add(it)
        }
        return list
    }

    fun findInHabitDescription(request: String, originalList: List<HabitItem>): List<HabitItem> {
        val list = mutableListOf<HabitItem>()
        if (request.isEmpty()) return originalList
        originalList.forEach {
            if (it.description.contains(request))
                list.add(it)
        }
        return list
    }

    fun sortByHabitName(originalList: List<HabitItem>): List<HabitItem> {
        return originalList.sortedBy { it.name }
    }

    fun sortByHabitId(originalList: List<HabitItem>): List<HabitItem>? {
        return originalList.sortedBy { it.getID() }
    }
}