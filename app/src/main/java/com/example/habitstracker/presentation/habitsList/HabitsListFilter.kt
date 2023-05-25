package com.example.habitstracker.presentation.habitsList

import com.example.domain.entitys.DomainHabitEntity
import java.util.*

object HabitsListFilter {
    fun filterBadHabit(originalList: List<DomainHabitEntity>): List<DomainHabitEntity> {
        val list = mutableListOf<DomainHabitEntity>()
        originalList.forEach {
            if (!it.isGood) list.add(it)
        }
        return list
    }

    fun filterGoodHabit(originalList: List<DomainHabitEntity>): List<DomainHabitEntity> {
        val list = mutableListOf<DomainHabitEntity>()
        originalList.forEach {
            if (it.isGood) list.add(it)
        }
        return list
    }

    fun filterHabitName(
        request: String,
        originalList: List<DomainHabitEntity>
    ): List<DomainHabitEntity> {
        val list = mutableListOf<DomainHabitEntity>()
        if (request.isEmpty()) return originalList
        originalList.forEach {
            if (it.name.lowercase(Locale.getDefault())
                    .contains(request.lowercase(Locale.getDefault()))
            )
                list.add(it)
        }
        return list
    }

    fun filterHabitDescription(
        request: String,
        originalList: List<DomainHabitEntity>
    ): List<DomainHabitEntity> {
        val list = mutableListOf<DomainHabitEntity>()
        if (request.isEmpty()) return originalList
        originalList.forEach {
            if (it.description.lowercase(Locale.getDefault())
                    .contains(request.lowercase(Locale.getDefault()))
            )
                list.add(it)
        }
        return list
    }

    fun sortByHabitName(originalList: List<DomainHabitEntity>): List<DomainHabitEntity> {
        return originalList.sortedBy { it.name }
    }

    fun sortByHabitId(originalList: List<DomainHabitEntity>): List<DomainHabitEntity>? {
        return originalList.sortedBy { it.getID() }
    }
}