package com.example.habitstracker.presentation.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.presentation.IInteraction

class DetailViewModelFactory(
    private val arguments: Bundle?,
    private val interaction: IInteraction //injected
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailHabitFragmentViewModel(
            arguments?.getIntOrNull(KEY_ID),
            interaction
        ) as T
    }

    private fun Bundle.getIntOrNull(key: String): Int? {
        return if (this.containsKey(key))
            this.getInt(key)
        else
            null
    }
}