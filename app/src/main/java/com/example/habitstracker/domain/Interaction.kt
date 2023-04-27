package com.example.habitstracker.domain

import com.example.habitstracker.HabitsApplication.Companion.applicationScope
import com.example.habitstracker.data.HabitsRepository
import com.example.habitstracker.data.net.NetRepository
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.data.room.HabitsRoomDatabase
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.MainActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object Interaction {
    val database by lazy { HabitsRoomDatabase.getDatabase(MainActivity.contextBase!!) }
    val repositorySQL by lazy { HabitsRepository(database.habitsDAO()) }
    val repositoryNet = NetRepository()
    private val dataList: Flow<List<HabitEntity>> = repositorySQL.listHabits

    fun getPresentationList() = dataList.map { list ->
        Mapper.dataListToPresentationList(list)
    }

    fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
//            repositorySQL.insert(Mapper.presentationHabitToDataEntity(habit,null))
            val uid = repositoryNet.uploadNewHabit(Mapper.presentationToNewNet(habit))
//            val id = repositorySQL.getItem()
            repositorySQL.insert(Mapper.presentationHabitToDataEntity(habit, uid))
//            Log.e("FU", uid)
//            repositorySQL.update(Mapper.presentationHabitToDataEntity(habit, uid))
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