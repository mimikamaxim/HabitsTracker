package com.example.habitstracker

import android.app.Application
import com.example.habitstracker.data.HabitsRepository
import com.example.habitstracker.data.room.HabitsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HabitsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { HabitsRoomDatabase.getDatabase(this) }
    val repository by lazy { HabitsRepository(database.habitsDAO()) }
}