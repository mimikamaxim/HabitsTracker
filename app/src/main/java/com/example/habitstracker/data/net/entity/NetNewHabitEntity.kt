package com.example.habitstracker.data.net.entity

import com.google.gson.annotations.SerializedName

data class NetNewHabitEntity(
    @SerializedName("title") val name: String,
    val description: String,
    val priority: Int,
    @SerializedName("type") val isGood: Int,
    val color: Int,
    //___________________date part
    @SerializedName("frequency") val frequencyOfAllowedExecutions: Int,
    @SerializedName("count") val periodInDays: Int,
    @SerializedName("date") val lastEditData: Long,
)