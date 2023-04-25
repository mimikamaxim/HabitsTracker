package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitsListViewModelFactory(
    private val habitsListType: HabitsListType
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitsListViewModel(habitsListType) as T
    }
}