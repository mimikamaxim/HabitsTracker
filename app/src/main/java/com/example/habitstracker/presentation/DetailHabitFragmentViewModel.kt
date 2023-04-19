package com.example.habitstracker.presentation

import android.os.Bundle
import androidx.lifecycle.*
import com.example.habitstracker.domain.Interaction
import com.example.habitstracker.presentation.habitsList.HabitItemPresentationModel
import kotlinx.coroutines.launch

class DetailHabitFragmentViewModel(val id: Int?) : ViewModel() {
    class ViewModelFactory(
        private val arguments: Bundle?
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailHabitFragmentViewModel(arguments?.getIntOrNull(KEY_ID)) as T
        }

        private fun android.os.Bundle.getIntOrNull(key: String): Int? {
            return if (this.containsKey(key))
                this.getInt(key)
            else
                null
        }
    }

    private val _habit: MutableLiveData<HabitItemPresentationModel?> = MutableLiveData()
    val habit: LiveData<HabitItemPresentationModel?> = _habit

    init {
        viewModelScope.launch {
            if (id != null) _habit.value = Interaction.getPresentationHabit(id)
            else _habit.value = null
        }
    }

    fun saveHabit (habitItemPresentationModel: HabitItemPresentationModel) {
        if (id != null) {
            Interaction.updateHabitFromPresentation(habitItemPresentationModel)
        } else {
            Interaction.addNewHabitFromPresentation(habitItemPresentationModel)
        }
    }
}