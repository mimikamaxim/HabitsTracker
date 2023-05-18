package com.example.habitstracker.presentation.test

import android.graphics.Color
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.IInteraction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

class TestInteraction : IInteraction {
    var list = mutableListOf<HabitItemPresentationModel>()

    init {
        addSample()
    }

    private fun addSample() {
        list.add(
            HabitItemPresentationModel(
                "Читать новости",
                "Читать свежие новости",
                2,
                false,
                Color.YELLOW,
                2,
                2,
                mutableListOf(),
                LocalDateTime.now(),
                1,
                1,
                list.size
            )
        )
        list.add(
            HabitItemPresentationModel(
                "Физические упражнения",
                "Заниматься физической нагрузкой в течении часа",
                2,
                false,
                Color.CYAN,
                2,
                2,
                mutableListOf<LocalDateTime>(),
                LocalDateTime.now(),
                1,
                1,
                list.size
            )
        )
    }

    override fun getPresentationList(): Flow<List<HabitItemPresentationModel>> {
        return flowOf(list)
    }

    override fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        list.add(
            HabitItemPresentationModel(
                name = habit.name,
                description = habit.description,
                priority = habit.priority,
                isGood = habit.isGood,
                color = habit.color,
                frequencyOfAllowedExecutions = habit.frequencyOfAllowedExecutions,
                periodInDays = habit.periodInDays,
                doneDates = habit.doneDates,
                initialDate = habit.initialDate,
                totalCompleteTimes = habit.totalCompleteTimes,
                currentCompleteTimes = habit.currentCompleteTimes,
                list.size
            )
        )
    }

    override fun updateHabitFromPresentation(habit: HabitItemPresentationModel) {
        list[habit.getID()] = habit
    }

    override suspend fun getPresentationHabit(id: Int): HabitItemPresentationModel {
        return list[id]
    }

    override fun deleteHabit(id: Int) {
        list.removeAt(id)
    }

    override fun addDone(id: Int) {
        list[id].doneDates.add(LocalDateTime.now())
    }
}