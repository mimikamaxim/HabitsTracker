package com.example.habitstracker.presentation.habitsList


data class HabitItemPresentationModel(
    val name: String,
    val description: String,
    val priority: Int,
    val isGood: Boolean,
    val amountDone: Int,
    val period: String,
    val color: Int,
    private val id: Int = -1
) {
    fun getID(): Int {
        if (id == -1) throw Exception("id of habit was not set correctly")
        return id
    }

    companion object {
        const val NewId = -2
    }
}