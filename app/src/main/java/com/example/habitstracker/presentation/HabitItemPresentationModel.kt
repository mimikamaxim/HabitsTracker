package com.example.habitstracker.presentation


data class HabitItemPresentationModel(
    val name: String,
    val description: String,
    val priority: Int,
    val isGood: Boolean,
    val color: Int,
    val frequencyOfAllowedExecutions: Int,
    val periodInDays: Int,
    val doneDates: List<Long>,
    val initialDate: Long,
    private val id: Int = -1
) {
    fun getID(): Int {
        if (id == -1) throw Exception("id of habit was not set correctly")
        return id
    }

    companion object {
        const val NoId = -2
    }
}