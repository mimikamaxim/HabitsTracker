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

//open class BaseObject @Inject constructor(val repositorySQL: HabitsLocalSQLRepository) {
////    val repositorySQL = repositorySQL
////    @Inject // Аннотировать поле для внедрения зависимости
////    lateinit var repositorySQL: HabitsLocalSQLRepository
//
//    init {
//        // Получить экземпляр компонента Dagger2 и выполнить внедрение зависимостей
//        val databaseComponent = DaggerAppComponent.create()
//        databaseComponent.inject(this)
//    }
//}


class Interaction @Inject constructor(var repositorySQL: HabitsLocalSQLRepository) {
    //    val repositorySQL = Helper.repositorySQL
//    val database by lazy { HabitsRoomDatabase.getDatabase(MainActivity.contextBase!!) }
//    val repositorySQL by lazy { HabitsLocalSQLRepository(database.habitsDAO()) }
//    @Inject
//    lateinit var repositorySQL: HabitsLocalSQLRepository
//    var repositorySQL1: HabitsLocalSQLRepository? =null
//    var repositorySQL: HabitsLocalSQLRepository

//    init {
//        HabitsApplication.appComponent.inject(this)
//        repositorySQL = repositorySQL1!!
//    }

//    init {
//        val daggerComponent = DaggerAppComponent
//            .builder()
//            .build()//todo как инжектить в поле если нет application
//        daggerComponent.inject(this)
////        HabitsApplication.appComponent.inject(this)
//    }

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
            repositorySQL.update(
                HabitSQLEntity(
                    ourHabit.title,
                    ourHabit.description,
                    ourHabit.priority,
                    ourHabit.isGood,
                    ourHabit.amountDone,
                    ourHabit.period,
                    ourHabit.color,
                    ourHabit.id,
                    uid
                )
            )
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