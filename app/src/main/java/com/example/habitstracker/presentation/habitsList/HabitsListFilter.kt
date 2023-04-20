package com.example.habitstracker.presentation.habitsList

import com.example.habitstracker.presentation.HabitItemPresentationModel
import java.util.*

object HabitsListFilter {
    fun filterBadHabit(originalList: List<HabitItemPresentationModel>): List<HabitItemPresentationModel> {
        val list = mutableListOf<HabitItemPresentationModel>()
        originalList.forEach {
            if (!it.isGood) list.add(it)
        }
        return list
    }

    fun filterGoodHabit(originalList: List<HabitItemPresentationModel>): List<HabitItemPresentationModel> {
        val list = mutableListOf<HabitItemPresentationModel>()
        originalList.forEach {
            if (it.isGood) list.add(it)
        }
        return list
    }

    fun filterHabitName(request: String, originalList: List<HabitItemPresentationModel>): List<HabitItemPresentationModel> {
        val list = mutableListOf<HabitItemPresentationModel>()
        if (request.isEmpty()) return originalList
        originalList.forEach {
            if (it.name.lowercase(Locale.getDefault())
                    .contains(request.lowercase(Locale.getDefault()))
            )
                list.add(it)
        }
        return list
    }

    fun filterHabitDescription(request: String, originalList: List<HabitItemPresentationModel>): List<HabitItemPresentationModel> {
        val list = mutableListOf<HabitItemPresentationModel>()
        if (request.isEmpty()) return originalList
        originalList.forEach {
            if (it.description.lowercase(Locale.getDefault())
                    .contains(request.lowercase(Locale.getDefault()))
            )
                list.add(it)
        }
        return list
    }

    fun sortByHabitName(originalList: List<HabitItemPresentationModel>): List<HabitItemPresentationModel> {
        return originalList.sortedBy { it.name }
    }

    fun sortByHabitId(originalList: List<HabitItemPresentationModel>): List<HabitItemPresentationModel>? {
        return originalList.sortedBy { it.getID() }
    }
}