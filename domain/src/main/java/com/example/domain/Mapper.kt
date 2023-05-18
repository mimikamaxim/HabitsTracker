package com.example.domain

import com.example.data.net.entity.NetHabitEntity
import com.example.data.net.entity.NetNewHabitEntity
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.HabitItemPresentationModel.Companion.NoId
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.example.data.room.HabitSQLEntity as HabitSQLEntity1

internal object Mapper {

    fun dataListToPresentationList(dataList: List<HabitSQLEntity1>): List<HabitItemPresentationModel> {
        val result = mutableListOf<HabitItemPresentationModel>()
        dataList.forEach {
            result.add(dataEntityToPresentationModel(it))
        }
        return result
    }

    fun dataEntityToPresentationModel(habitSQLEntity: HabitSQLEntity1): HabitItemPresentationModel {
        return HabitItemPresentationModel(
            name = habitSQLEntity.name,
            description = habitSQLEntity.description,
            priority = habitSQLEntity.priority,
            isGood = habitSQLEntity.isGood,
            color = habitSQLEntity.color,
            frequencyOfAllowedExecutions = habitSQLEntity.frequencyOfAllowedExecutions,
            periodInDays = habitSQLEntity.periodInDays,
            doneDates = DateTimeConverterHelper.listSecondsToDataTime(habitSQLEntity.doneDates),
            initialDate = DateTimeConverterHelper.timeInEpochSecondsToLocalDateTime(habitSQLEntity.initialDate),
            totalCompleteTimes = habitSQLEntity.totalCompleteTimes,
            currentCompleteTimes = habitSQLEntity.currentCompleteTimes,
            id = habitSQLEntity.id ?: throw Exception("No ID for element from base")
        )
    }

    fun presentationHabitToDataEntity(
        habit: HabitItemPresentationModel,
        uid: String?
    ): HabitSQLEntity1 {
        return HabitSQLEntity1(
            id = if (habit.getID() == NoId) null else habit.getID(),
            name = habit.name,
            description = habit.description,
            priority = habit.priority,
            isGood = habit.isGood,
            color = habit.color,
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
            periodInDays = habit.periodInDays,
            uid = uid,
            currentCompleteTimes = habit.currentCompleteTimes,
            doneDates = DateTimeConverterHelper.listDataTimeToSeconds(habit.doneDates),
            initialDate = DateTimeConverterHelper.localDateTimeToCurrentTimeInEpochSeconds(habit.initialDate),
            totalCompleteTimes = habit.totalCompleteTimes,
            lastEditData = DateTimeConverterHelper.localDateTimeToCurrentTimeInEpochSeconds(
                LocalDateTime.now()
            ),
            listEditDates = listOf()// TODO add for good sync
        )
    }

    fun sqlToNewNet(habit: com.example.data.room.HabitSQLEntity): NetNewHabitEntity {
        return NetNewHabitEntity(
            name = habit.name,
            description = habit.description,
            priority = habit.priority,
            isGood = if (habit.isGood) 1 else 0,
            color = habit.color,
            lastEditData = habit.lastEditData,
            periodInDays = habit.periodInDays,
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions
        )
    }

    fun sqlToNet(habit: HabitSQLEntity1, uid: String): NetHabitEntity {
        return NetHabitEntity(
            name = habit.name,
            description = habit.description,
            priority = habit.priority,
            isGood = if (habit.isGood) 1 else 0,
            color = habit.color,
            lastEditData = habit.lastEditData,
            periodInDays = habit.periodInDays,
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
            uid = uid,
            done_dates = listOf()
        )
    }

    fun netToSQL(netEn: NetHabitEntity): HabitSQLEntity1 {
        return HabitSQLEntity1(
            id = null,
            name = netEn.name,
            description = netEn.description,
            priority = netEn.priority,
            isGood = netEn.isGood == 1,
            color = netEn.color,
            frequencyOfAllowedExecutions = netEn.frequencyOfAllowedExecutions,
            periodInDays = netEn.periodInDays,
            uid = netEn.uid,
            lastEditData = netEn.lastEditData,
            listEditDates = listOf(),
            totalCompleteTimes = 0,
            initialDate = netEn.lastEditData,
            doneDates = netEn.done_dates.toMutableList(),
            currentCompleteTimes = 0//todo calculate it
        )
    }
}

object DateTimeConverterHelper {
    val currentOffset = ZoneOffset.systemDefault().rules.getOffset(Instant.now())

    fun getCurrentTimeInEpochSeconds(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun localDateTimeToCurrentTimeInEpochSeconds(dateTime: LocalDateTime): Long {
        return dateTime.toEpochSecond(currentOffset)
    }

    fun timeInEpochSecondsToLocalDateTime(time: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(time, 0, currentOffset)
    }

    fun listSecondsToDataTime(list: List<Long>): MutableList<LocalDateTime> {
        val mutableList = mutableListOf<LocalDateTime>()
        list.forEach {
            mutableList.add(timeInEpochSecondsToLocalDateTime(it))
        }
        return mutableList
    }

    fun listDataTimeToSeconds(list: List<LocalDateTime>): MutableList<Long> {
        val mList = mutableListOf<Long>()
        list.forEach {
            mList.add(localDateTimeToCurrentTimeInEpochSeconds(it))
        }
        return mList
    }
}