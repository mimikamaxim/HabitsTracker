package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.data.HabitsListFilter
import com.example.habitstracker.data.HabitsListType

class ListViewModel(listType: HabitsListType = HabitsListType.ALL): ViewModel() {
    private var originalList = listOf<HabitItem>()
    private val _list: MutableLiveData<List<HabitItem>> = MutableLiveData()
    val list: LiveData<List<HabitItem>> = _list
    init {
        _list.value = when (listType){
            HabitsListType.ALL -> HabitItemsDB.getHabitItemsList()
            HabitsListType.BAD -> HabitsListFilter.getBadHabitItemsList()
            HabitsListType.GOOD -> HabitsListFilter.getGoodHabitItemsList()
        }
        originalList = list.value!!
    }

    fun findByHabitName (request: String){
        _list.value = HabitsListFilter.findByHabitName(request,originalList)
    }

    fun findInHabitDescription (request: String){
        _list.value = HabitsListFilter.findInHabitDescription(request,originalList)
    }

    fun sortByHabitName() {
        _list.value = HabitsListFilter.sortByHabitName(list.value!!)
    }

    fun setDefaultList(){
        _list.value = HabitsListFilter.sortByHabitId(list.value!!)
    }
}