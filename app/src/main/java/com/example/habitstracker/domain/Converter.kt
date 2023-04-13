package com.example.habitstracker.domain

import android.content.ContentProvider
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habitstracker.data.room.HabitEntity
import com.example.habitstracker.data.HabitsRepository
import com.example.habitstracker.data.room.HabitsRoomDatabase
import com.example.habitstracker.presentation.MainActivity
import com.example.habitstracker.presentation.habitsList.HabitItemPresentationModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

//class ConverterFactory(
//    private val context: Context,
////    private val lifecycleOwner: LifecycleOwner
//) {
//    companion object {
//        var converter: Converter?=null
//    }
//
//    fun getInstance(): Converter{
//        if (converter==null)
//            converter = Converter(context)
////        converter = Converter(context,lifecycleOwner)
//        else
////            converter!!.setParam(context,lifecycleOwner)
//            converter!!.setParam(context)
//        return converter!!
//    }
//}


//class Converter(var context: Context) {
object Converter {
//    class Converter(var context: Context,var lifecycleOwner: LifecycleOwner) {
    private val repository by lazy {
        HabitsRepository(
            HabitsRoomDatabase.getDatabase(
                MainActivity.contextBase!!
            ).habitsDAO()
        )
    }

    private val repoList: Flow<List<HabitEntity>> =
        repository.listHabits

    private val _presentationList: MutableLiveData<MutableList<HabitItemPresentationModel>> =
        MutableLiveData()
    val presentationList: LiveData<MutableList<HabitItemPresentationModel>> = _presentationList

    init {
//        refill()
//        repoList.observe(lifecycleOwner) {
//            refill()
//        }
        MainScope().launch {
            repoList.collect { repositoryList ->
                _presentationList.value = mutableListOf()
                repositoryList.forEach {
                    _presentationList.value?.add(
                        HabitItemPresentationModel(
                            it.name,
                            it.description,
                            it.priority,
                            it.isGood,
                            it.amountDone,
                            it.period,
                            it.color,
                            it.id ?: throw Exception("no ID")
                        )
                    )
                }
            }
        }
    }

//    fun setParam(context: Context){ //, lifecycleOwner: LifecycleOwner
//        this.context = context
//        this.lifecycleOwner = lifecycleOwner
//    }

//    private fun refill() {
//        _presentationList.value = mutableListOf()
//        repoList.value?.forEach {
//            _presentationList.value?.add(
//                HabitItemPresentationModel(
//                    it.name,
//                    it.description,
//                    it.priority,
//                    it.isGood,
//                    it.amountDone,
//                    it.period,
//                    it.color,
//                    it.id ?: throw Exception("no ID")
//                )
//            )
//        }
//    }

    fun addNewHabitFromPresentation(habit: HabitItemPresentationModel) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.insert(
                HabitEntity(
                    habit.name,
                    habit.description,
                    habit.priority,
                    habit.isGood,
                    habit.amountDone,
                    habit.period,
                    habit.color,
                    null
                )
            )
        }
    }

    fun updateHabitFromPresentation(habit: HabitItemPresentationModel, id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.update(
                HabitEntity(
                    habit.name,
                    habit.description,
                    habit.priority,
                    habit.isGood,
                    habit.amountDone,
                    habit.period,
                    habit.color,
                    id
                )
            )
        }
    }

    fun getPresentationHabit(id: Int): HabitItemPresentationModel? {
        val _inlist = _presentationList.value
//        GlobalScope.launch { delay(500) }
        return _inlist?.find { it.getID() == id }
    }
}