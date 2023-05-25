package com.example.habitstracker.presentation.habitsList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.IInteraction

class HabitsListViewModelFactory(
    private val habitsListType: HabitsListType,
    private val interaction: IInteraction,
    private val context: Context
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitsListViewModel(
            habitsListType,
            interaction,
            context
        ) as T
    }
}