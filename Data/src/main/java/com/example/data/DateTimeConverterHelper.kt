package com.example.data

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

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