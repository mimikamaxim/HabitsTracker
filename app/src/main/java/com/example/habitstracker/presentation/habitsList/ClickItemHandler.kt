package com.example.habitstracker.presentation.habitsList

interface ClickItemHandler {
    fun onNavigateToDetails(id: Int)
    fun onDeleteItem(id: Int)
    fun onAddDone(id: Int)
}