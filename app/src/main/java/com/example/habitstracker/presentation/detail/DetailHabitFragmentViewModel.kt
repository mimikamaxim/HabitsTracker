package com.example.habitstracker.presentation.detail

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.IInteraction
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DetailHabitFragmentViewModel(val id: Int?, val interaction: IInteraction) : ViewModel() {

    private val _habit: MutableLiveData<HabitItemPresentationModel> = MutableLiveData()
    val habit: LiveData<HabitItemPresentationModel> = _habit
    private var editableHabit: HabitItemPresentationModel = HabitItemPresentationModel(
        "",
        "",
        0,
        true,
        color = Color.GRAY,
        1,
        1,
        doneDates = mutableListOf(),
        initialDate = LocalDateTime.now(),
        0,
        0,
        id = HabitItemPresentationModel.NoId
    )

    init {
        viewModelScope.launch {
            if (id != null) {
                editableHabit = interaction.getPresentationHabit(id)
            }
            _habit.value = editableHabit
        }
    }

    fun saveHabit() {
        if (id != null) {
            interaction.updateHabitFromPresentation(editableHabit)
        } else {
            interaction.addNewHabitFromPresentation(editableHabit)
        }
    }

    fun setName(string: String) {
        editableHabit.name = string
    }

    fun setDescription(string: String) {
        editableHabit.description = string
    }

    fun setPriority(int: Int) {
        editableHabit.priority = int
    }

    fun setIsGood(isGood: Boolean) {
        editableHabit.isGood = isGood
    }

    fun setFreqExec(str: String) {
        editableHabit.frequencyOfAllowedExecutions = str.toInt()
    }

    fun setPeriod(str: String) {
        editableHabit.periodInDays = str.toInt()
    }

    fun setTotalCompleteTimes(str: String) {
        editableHabit.totalCompleteTimes = str.toInt()
    }
}