package com.example.habitstracker.presentation.habitsList

sealed class ToastType

class Message(val message: String) : ToastType()

object NoContext : ToastType()
