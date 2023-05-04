package com.example.habitstracker.presentation

import android.os.Bundle
import androidx.lifecycle.*
import com.example.habitstracker.domain.Interaction
import kotlinx.coroutines.launch

class DetailHabitFragmentViewModel(val id: Int?, val interaction: Interaction) : ViewModel() {
    class ViewModelFactory(
        private val arguments: Bundle?,
        private val interaction: Interaction
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailHabitFragmentViewModel(
                arguments?.getIntOrNull(KEY_ID),
                interaction
            ) as T
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
//    @Inject
//    lateinit var interaction : Interaction

    init {
        viewModelScope.launch {
            if (id != null) _habit.value = interaction.getPresentationHabit(id)
            else _habit.value = null
        }
//        DaggerAppComponent.create().inject(this)
//                HabitsApplication.appComponent.inject(this)
//        HabitsApplication.appComponent.inject(this)
    }

    fun saveHabit(habitItemPresentationModel: HabitItemPresentationModel) {
        if (id != null) {
            interaction.updateHabitFromPresentation(habitItemPresentationModel)
        } else {
            interaction.addNewHabitFromPresentation(habitItemPresentationModel)
        }
    }
}