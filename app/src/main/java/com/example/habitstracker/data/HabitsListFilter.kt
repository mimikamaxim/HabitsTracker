package com.example.habitstracker.data

object HabitsListFilter {
    fun getBadHabitItemsList(): List<HabitItem>{
        val list = mutableListOf<HabitItem>()
        HabitItemsDB.getHabitItemsList().forEach {
            if (!it.isGood) list.add(it)
        }
        return list
    }

    fun getGoodHabitItemsList(): List<HabitItem>{
        val list = mutableListOf<HabitItem>()
        HabitItemsDB.getHabitItemsList().forEach {
            if (it.isGood) list.add(it)
        }
        return list
    }

    fun findByHabitName(request: String): List<HabitItem>{
        val list = mutableListOf<HabitItem>()
        if (request.isEmpty()) return HabitItemsDB.getHabitItemsList()
        HabitItemsDB.getHabitItemsList().forEach {
            if (it.name.contains(request))
                list.add(it)
        }
        return list
    }

    fun findInHabitDescription (request: String): List<HabitItem>{
        val list = mutableListOf<HabitItem>()
        if (request.isEmpty()) return HabitItemsDB.getHabitItemsList()
        HabitItemsDB.getHabitItemsList().forEach {
            if (it.description.contains(request))
                list.add(it)
        }
        return list
    }
}