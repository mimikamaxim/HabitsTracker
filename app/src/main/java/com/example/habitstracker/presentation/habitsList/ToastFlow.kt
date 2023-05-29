package com.example.habitstracker.presentation.habitsList

sealed class ToastFlow

class Message(val message: String) : ToastFlow()

object NoContext : ToastFlow()
