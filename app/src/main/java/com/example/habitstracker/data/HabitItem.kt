package com.example.habitstracker.data


data class HabitItem(
    val name: String,
    val description: String,
    val priority: Int,
    val isGood: Boolean,
    val amountDone: Int,
    val period: String,
    val color: Int,
    private val id: Int = -1
) {
    fun getID() = id
}