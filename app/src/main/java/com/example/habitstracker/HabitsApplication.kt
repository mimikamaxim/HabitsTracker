package com.example.habitstracker

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HabitsApplication : Application() {
    companion object {
        val applicationScope = CoroutineScope(SupervisorJob())
        val mainToastFlow: Flow<String> = flow {
            //todo ?
            fun newToastMessage(message: String) {
                applicationScope.launch { emit(message) }
            }
        }
    }
}