package com.example.domain

import com.example.data.DateTimeConverterHelper
import com.example.data.net.entity.NetHabitEntity
import com.example.data.net.entity.NetNewHabitEntity
import com.example.data.room.HabitSQLEntity
import com.example.domain.entitys.DomainHabitEntity
import com.example.domain.entitys.DomainHabitEntity.Companion.NoId
import java.time.LocalDateTime

internal object Mapper {

    fun sqlListToDomainList(dataList: List<HabitSQLEntity>): List<DomainHabitEntity> {
        val result = mutableListOf<DomainHabitEntity>()
        dataList.forEach {
            result.add(sqlEntityToDomainEntity(it))
        }
        return result
    }

    fun sqlEntityToDomainEntity(habitSQLEntity: HabitSQLEntity): DomainHabitEntity {
        return DomainHabitEntity(
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
            id = habitSQLEntity.id ?: throw Exception("No ID for element from base"),
            uid = habitSQLEntity.uid ?: ""
        )
    }

    fun domainToSqlEntity(
        habit: DomainHabitEntity,
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
            currentCompleteTimes = habit.currentCompleteTimes,
            doneDates = DateTimeConverterHelper.listDataTimeToSeconds(habit.doneDates),
            initialDate = DateTimeConverterHelper.localDateTimeToCurrentTimeInEpochSeconds(habit.initialDate),
            totalCompleteTimes = habit.totalCompleteTimes,
            lastEditData = DateTimeConverterHelper.localDateTimeToCurrentTimeInEpochSeconds(
                LocalDateTime.now()
            ),
            listEditDates = listOf()
        )
    }

    fun sqlToNewNet(habit: HabitSQLEntity): NetNewHabitEntity {
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

    fun sqlToNet(habit: HabitSQLEntity, uid: String): NetHabitEntity {
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

    fun netToSQL(netEn: NetHabitEntity): HabitSQLEntity {
        return HabitSQLEntity(
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

    fun listNetToListDomain(habits: List<NetHabitEntity>): List<DomainHabitEntity> {
        val list: MutableList<DomainHabitEntity> = mutableListOf()
        habits.forEach {
            list.add(netToDomain(it))
        }
        return list
    }

    fun netToDomain(netHabitEntity: NetHabitEntity): DomainHabitEntity {
        return DomainHabitEntity(
            name = netHabitEntity.name,
            description = netHabitEntity.description,
            priority = netHabitEntity.priority,
            isGood = netHabitEntity.isGood == 1,
            color = netHabitEntity.color,
            frequencyOfAllowedExecutions = netHabitEntity.frequencyOfAllowedExecutions,
            periodInDays = netHabitEntity.periodInDays,
            doneDates = DateTimeConverterHelper.listSecondsToDataTime(netHabitEntity.done_dates),
            initialDate = DateTimeConverterHelper.timeInEpochSecondsToLocalDateTime(netHabitEntity.lastEditData),
            totalCompleteTimes = 0,
            currentCompleteTimes = 0,//todo calculate
            id = NoId,
            uid = netHabitEntity.uid
        )
    }

    fun domainToNewNet(habit: DomainHabitEntity): NetNewHabitEntity {
        return NetNewHabitEntity(
            name = habit.name,
            description = habit.description,
            color = habit.color,
            lastEditData = DateTimeConverterHelper.getCurrentTimeInEpochSeconds(),
            periodInDays = habit.periodInDays,
            isGood = if (habit.isGood) 1 else 0,
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
            priority = habit.priority
        )
    }

    fun domainToNet(habit: DomainHabitEntity): NetHabitEntity {
        return NetHabitEntity(
            name = habit.name,
            description = habit.description,
            color = habit.color,
            lastEditData = DateTimeConverterHelper.getCurrentTimeInEpochSeconds(),
            periodInDays = habit.periodInDays,
            isGood = if (habit.isGood) 1 else 0,
            frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
            priority = habit.priority,
            uid = habit.uid,
            done_dates = listOf() // Stub because server ignoring done dates
        )
    }
}

