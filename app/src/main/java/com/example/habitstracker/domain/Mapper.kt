package com.example.habitstracker.domain

import com.example.habitstracker.data.net.entity.NetHabitEntity
import com.example.habitstracker.data.net.entity.NetNewHabitEntity
import com.example.habitstracker.data.room.HabitSQLEntity
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.HabitItemPresentationModel.Companion.NoId

internal object Mapper {

    fun dataListToPresentationList(dataList: List<HabitSQLEntity>): List<HabitItemPresentationModel> {
        val result = mutableListOf<HabitItemPresentationModel>()
        dataList.forEach {
            result.add(dataEntityToPresentationModel(it))
        }
        return result
    }

    fun dataEntityToPresentationModel(habitSQLEntity: HabitSQLEntity): HabitItemPresentationModel {
        return HabitItemPresentationModel(
            name = habitSQLEntity.name,
            description = habitSQLEntity.description,
            priority = habitSQLEntity.priority,
            isGood = habitSQLEntity.isGood,
            color = habitSQLEntity.color,
            frequencyOfAllowedExecutions = habitSQLEntity.frequencyOfAllowedExecutions,
            periodInDays = habitSQLEntity.periodInDays,
            doneDates = habitSQLEntity.doneDates,
            initialDate = habitSQLEntity.initialDate, ,
            id = habitSQLEntity.id ?: throw Exception("No ID for element from base")
        )
    }

    fun presentationHabitToDataEntity(
        habit: HabitItemPresentationModel,
        uid: String?
    ): HabitSQLEntity {
        return HabitSQLEntity(
            id = if (habit.getID() == NoId) null else habit.getID(),
            name = habit.name,
            description = habit.description,
            priority = habit.priority,
            isGood = habit.isGood,
            color = habit.color,
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
            periodInDays = habit.periodInDays,
            uid = uid,
        )
    }

    fun presentationToNewNet(habit: HabitItemPresentationModel): NetNewHabitEntity {
        return NetNewHabitEntity(
            color = habit.color,
            count = habit.periodInDays,
            date = System.currentTimeMillis().toInt(),
            description = habit.description,
            done_dates = listOf(),
            frequency = habit.frequencyOfAllowedExecutions,
            priority = habit.priority,
            title = habit.name,
            type = if (habit.isGood) 1 else 0
        )
    }

    fun presentationToNet(habit: HabitItemPresentationModel, uid: String): NetHabitEntity {
        return NetHabitEntity(
            color = habit.color,
            amountDone = habit.periodInDays,
            lastEditData = System.currentTimeMillis().toInt(),
            description = habit.description,
            done_dates = listOf(),
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
            priority = habit.priority,
            name = habit.name,
            isGood = if (habit.isGood) 1 else 0,
            uid = uid,
        )
    }
}