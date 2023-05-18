package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.presentation.IInteraction

class HabitsListViewModelFactory(
    private val habitsListType: HabitsListType,
    private val interaction: IInteraction
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitsListViewModel(
            habitsListType,
            interaction
        ) as T
    }
}