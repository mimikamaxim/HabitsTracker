package com.example.habitstracker.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.R
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB

class DetailHabitFragmentViewModel(val id: Int?): ViewModel() {
    private val _habit: MutableLiveData<HabitItem?> = MutableLiveData()
    val habit: LiveData<HabitItem?> = _habit

    init {
        if (id!=null) _habit.value = HabitItemsDB.getHabit(id)
            else _habit.value = null
    }

    fun saveHabit (habitItem: HabitItem) {
        if (id!=null) {
            HabitItemsDB.updateHabit(id, habitItem)
        } else {
            HabitItemsDB.addHabit(habitItem)
        }
    }
}