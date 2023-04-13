package com.example.habitstracker.presentation

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.presentation.habitsList.HabitItemPresentationModel
import com.example.habitstracker.domain.Converter
//import com.example.habitstracker.domain.ConverterFactory

class DetailHabitFragmentViewModel(val id: Int?, context: Context, lifecycleOwner: LifecycleOwner): ViewModel() {
    private val _habit: MutableLiveData<HabitItemPresentationModel?> = MutableLiveData()
    val habit: LiveData<HabitItemPresentationModel?> = _habit

//    val converter: Converter = ConverterFactory(context).getInstance()
    val converter = Converter
    init {
        if (id!=null) _habit.value = converter.getPresentationHabit(id)
        else _habit.value = null
    }

    fun saveHabit (habitItemPresentationModel: HabitItemPresentationModel) {
        if (id!=null) {
            converter.updateHabitFromPresentation(habitItemPresentationModel, id)
        } else {
            converter.addNewHabitFromPresentation(habitItemPresentationModel)
        }
    }
}