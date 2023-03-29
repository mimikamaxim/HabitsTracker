package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel: ViewModel() {
    private val mutableTest: MutableLiveData<Int> = MutableLiveData()

    val test: LiveData<Int> = mutableTest
}