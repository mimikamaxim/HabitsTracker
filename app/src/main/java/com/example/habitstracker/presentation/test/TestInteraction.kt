package com.example.habitstracker.presentation.test

import android.graphics.Color
import com.example.domain.AddDoneResult
import com.example.domain.IInteraction
import com.example.domain.YouDone
import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

class TestInteraction : IInteraction {
    var list = mutableListOf<DomainHabitEntity>()

    init {
        addSample()
    }

    private fun addSample() {
        list.add(
            DomainHabitEntity(
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
                id = list.size
            )
        )
        list.add(
            DomainHabitEntity(
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
                id = list.size
            )
        )
    }

    override fun getPresentationList(): Flow<List<DomainHabitEntity>> {
        return flowOf(list)
    }

    override fun addNewHabitFromPresentation(habit: DomainHabitEntity) {
        list.add(
            DomainHabitEntity(
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
                id = list.size
            )
        )
    }

    override fun updateHabitFromPresentation(habit: DomainHabitEntity) {
        list[habit.getID()] = habit
    }

    override suspend fun getPresentationHabit(id: Int): DomainHabitEntity {
        return list[id]
    }

    override fun deleteHabit(id: Int) {
        list.removeAt(id)
    }

    override suspend fun addDone(id: Int): AddDoneResult {
        list[id].doneDates.add(LocalDateTime.now())
        return YouDone
    }
}