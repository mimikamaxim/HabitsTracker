package com.example.habitstracker.presentation.habitsList

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.domain.Converter
//import com.example.habitstracker.domain.ConverterFactory
import com.example.habitstracker.myLogger

class ListViewModel(listType: HabitsListType = HabitsListType.ALL, context: Context, lifecycleOwner: LifecycleOwner): ViewModel() {

    private var originalList = listOf<HabitItemPresentationModel>()
    private val _list: MutableLiveData<List<HabitItemPresentationModel>> = MutableLiveData()
    val list: LiveData<List<HabitItemPresentationModel>> = _list
//    val converter: Converter = ConverterFactory(context).getInstance()
    val converter = Converter
    val converterList = converter.presentationList
    init {
        converterList.observe(lifecycleOwner){
            Log.i("LIST",converterList.value.toString())
            _list.value = when (listType){
                HabitsListType.ALL -> it
                HabitsListType.BAD -> HabitsListFilter.filterBadHabit(it)
                HabitsListType.GOOD -> it//HabitsListFilter.filterGoodHabit(it)
            }
            originalList = list.value!!
        }
        myLogger("VM list init $listType")
    }

    fun findByHabitName (request: String){
        _list.value = HabitsListFilter.filterHabitName(request,originalList)
    }

    fun findInHabitDescription (request: String){
        _list.value = HabitsListFilter.filterHabitDescription(request,originalList)
    }

    fun sortByHabitName() {
        _list.value = HabitsListFilter.sortByHabitName(list.value!!)
    }

    fun sortByHabitId(){
        _list.value = HabitsListFilter.sortByHabitId(list.value!!)
    }
}