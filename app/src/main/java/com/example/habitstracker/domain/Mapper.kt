package com.example.habitstracker.domain

import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.presentation.habitsList.HabitItemPresentationModel
import com.example.habitstracker.presentation.habitsList.HabitItemPresentationModel.Companion.NewId

object Mapper {

    fun dataListToPresentationList(dataList: List<HabitEntity>): List<HabitItemPresentationModel> {
        val result = mutableListOf<HabitItemPresentationModel>()
        dataList.forEach {
            result.add(dataEntityToPresentationModel(it))
        }
        return result
    }

    fun dataEntityToPresentationModel(habitEntity: HabitEntity): HabitItemPresentationModel {
        return HabitItemPresentationModel(
            habitEntity.name,
            habitEntity.description,
            habitEntity.priority,
            habitEntity.isGood,
            habitEntity.amountDone,
            habitEntity.period,
            habitEntity.color,
            habitEntity.id ?: throw Exception("No ID for element from base")
        )
    }

    fun presentationHabitToDataEntity(habit: HabitItemPresentationModel): HabitEntity {
        return HabitEntity(
            habit.name,
            habit.description,
            habit.priority,
            habit.isGood,
            habit.amountDone,
            habit.period,
            habit.color,
            if (habit.getID() == NewId) null else habit.getID()
        )
    }
}