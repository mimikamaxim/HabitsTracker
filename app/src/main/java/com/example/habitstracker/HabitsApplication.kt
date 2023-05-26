package com.example.habitstracker

import android.app.Application
import com.example.habitstracker.dagger2.AppComponent
import com.example.habitstracker.dagger2.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HabitsApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        val applicationScope = CoroutineScope(SupervisorJob())
    }
}
