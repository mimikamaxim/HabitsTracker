package com.example.habitstracker

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HabitsApplication : Application() {
    companion object {
        val applicationScope = CoroutineScope(SupervisorJob())
    }
}