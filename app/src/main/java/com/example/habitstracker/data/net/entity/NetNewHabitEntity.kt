package com.example.habitstracker.data.net.entity

data class NetNewHabitEntity(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int
)