package com.example.habitstracker.domain

import com.example.habitstracker.data.net.entity.NetHabitEntity
import com.example.habitstracker.data.net.entity.NetNewHabitEntity
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.HabitItemPresentationModel.Companion.NoId

internal object Mapper {

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

    fun presentationHabitToDataEntity(
        habit: HabitItemPresentationModel,
        uid: String?
    ): HabitEntity {
        return HabitEntity(
            habit.name,
            habit.description,
            habit.priority,
            habit.isGood,
            habit.amountDone,
            habit.period,
            habit.color,
            if (habit.getID() == NoId) null else habit.getID(),
            uid
        )
    }

    fun presentationToNewNet(habit: HabitItemPresentationModel): NetNewHabitEntity {
        return NetNewHabitEntity(
            habit.color,
            habit.amountDone,
            System.currentTimeMillis().toInt(),
            habit.description,
            listOf(),
            habit.period.toIntOrNull() ?: 1,
            habit.priority,
            habit.name,
            if (habit.isGood) 1 else 0
        )
    }

    fun presentationToNet(habit: HabitItemPresentationModel, uid: String): NetHabitEntity {
        return NetHabitEntity(
            habit.color,
            habit.amountDone,
            System.currentTimeMillis().toInt(),
            habit.description,
            listOf(),
            habit.period.toIntOrNull() ?: 1,
            habit.priority,
            habit.name,
            if (habit.isGood) 1 else 0,
            uid
        )
    }
}