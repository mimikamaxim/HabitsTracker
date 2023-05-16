package com.example.habitstracker.domain

import com.example.habitstracker.HabitsApplication.Companion.applicationScope
import com.example.habitstracker.data.HabitsLocalSQLRepository
import com.example.habitstracker.data.net.NetRepository
import com.example.habitstracker.data.room.HabitSQLEntity
import com.example.habitstracker.presentation.HabitItemPresentationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class Interaction @Inject constructor(var repositorySQL: HabitsLocalSQLRepository) { //todo принимать интерфейс

    private val repositoryNet = NetRepository()
    private val dataList: Flow<List<HabitSQLEntity>> = repositorySQL.listHabits

    fun getPresentationList() = dataList.map { list ->
        Mapper.dataListToPresentationList(list)
    }

    fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
            repositorySQL.insert(Mapper.presentationHabitToDataEntity(habit, null))
            val uid = repositoryNet.uploadNewHabit(Mapper.presentationToNewNet(habit))
//            repositorySQL.insert(Mapper())
            val ourHabit = repositorySQL.getLastItem()
//            repositorySQL.update(
//                HabitSQLEntity(
//                    ourHabit.title,
//                    ourHabit.description,
//                    ourHabit.priority,
//                    ourHabit.isGood,
//                    ourHabit.periodInDays,
//                    ourHabit.period,
//                    ourHabit.color,
//                    ourHabit.id,
//                    uid
//                )
//            )
        }
    }

    fun updateHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
            val sqlHabit = repositorySQL.getItem(habit.getID())
            val uid = sqlHabit.uid!!
            repositorySQL.update(Mapper.presentationHabitToDataEntity(habit, uid))
            repositoryNet.updateHabit(Mapper.presentationToNet(habit, uid))
        }
    }

    suspend fun getPresentationHabit(id: Int): HabitItemPresentationModel {
        val habit = repositorySQL.getItem(id)
        return Mapper.dataEntityToPresentationModel(habit)
    }
}