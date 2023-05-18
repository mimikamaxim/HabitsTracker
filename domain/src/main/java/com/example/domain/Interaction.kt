package com.example.domain

//import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.data.HabitsLocalSQLRepository
import com.example.data.net.NetRepository
import com.example.data.room.HabitSQLEntity
import com.example.habitstracker.HabitsApplication.Companion.applicationScope
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.IInteraction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class Interaction @Inject constructor(var repositorySQL: HabitsLocalSQLRepository) : IInteraction {

    private val repositoryNet = NetRepository()
    private val dataList: Flow<List<HabitSQLEntity>> = repositorySQL.listHabits

    init {
        sync()
    }

    override fun getPresentationList() = dataList.map { list ->
        Mapper.dataListToPresentationList(list)
    }

    override fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
            val sqlEntity: HabitSQLEntity = Mapper.presentationHabitToDataEntity(habit, null)
            repositorySQL.insert(sqlEntity)
            val uid = repositoryNet.uploadNewHabit(Mapper.sqlToNewNet(sqlEntity))
            val ourHabit = repositorySQL.getLastItem()
            repositorySQL.addRemoteUid(ourHabit.id!!, uid)
        }
    }

    override fun updateHabitFromPresentation(habit: HabitItemPresentationModel) {
        applicationScope.launch {
            val sqlHabit = repositorySQL.getItem(habit.getID())
            val uid = sqlHabit.uid!!
            val habitForUpdate = Mapper.presentationHabitToDataEntity(habit, uid)
            repositorySQL.update(habitForUpdate)
            repositoryNet.updateHabit(Mapper.sqlToNet(habitForUpdate, uid))
        }
    }

    override suspend fun getPresentationHabit(id: Int): HabitItemPresentationModel {
        val habit = repositorySQL.getItem(id)
        return Mapper.dataEntityToPresentationModel(habit)
    }

    override fun deleteHabit(id: Int) {
        applicationScope.launch {
            val sqlEntity = repositorySQL.getItem(id)
            val uid = sqlEntity.uid
            if (uid != null) repositoryNet.deleteHabit(uid)
            repositorySQL.delete(sqlEntity)
        }
    }

    override fun addDone(id: Int) {
        applicationScope.launch {
            val sqlEntity = repositorySQL.getItem(id)
            val timeStamp = DateTimeConverterHelper.getCurrentTimeInEpochSeconds()
            sqlEntity.doneDates.add(timeStamp)
            val curLocal = LocalDateTime.now()
            val initialDate =
                DateTimeConverterHelper.timeInEpochSecondsToLocalDateTime(sqlEntity.initialDate)
            //reset period
            if (curLocal > initialDate.plusDays(sqlEntity.periodInDays.toLong())) {
                sqlEntity.currentCompleteTimes = 0
                sqlEntity.initialDate = timeStamp
            }
            sqlEntity.currentCompleteTimes++
            repositorySQL.update(sqlEntity)
            if (sqlEntity.uid != null) repositoryNet.addDoneDate(timeStamp, sqlEntity.uid!!)
        }
    }

    fun sync() {
        applicationScope.launch {
            var localList = listOf<HabitSQLEntity>()
            dataList.collect {
                localList = it
            }
            val netList = repositoryNet.getNetHabits()
            netList.forEach { netEn ->
                var isSync = false
                localList.forEach { locEn ->
                    if (netEn.uid == locEn.uid) isSync = true
                }
                if (!isSync) repositorySQL.insert(Mapper.netToSQL(netEn))
            }
            localList.forEach {
                if (it.uid == null) {
                    val uid = repositoryNet.uploadNewHabit(Mapper.sqlToNewNet(it))
                    val ourHabit = repositorySQL.getLastItem()
                    repositorySQL.addRemoteUid(ourHabit.id!!, uid)
                    //todo done dates
                }
            }
        }
    }
}