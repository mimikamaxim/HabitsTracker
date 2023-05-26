package com.example.habitstracker.dagger2

import android.app.Application
import android.content.Context
import com.example.habitstracker.ApplicationContext
import com.example.habitstracker.HabitsApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
object ApplicationModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppCoroutineScope(): CoroutineScope {
        return HabitsApplication.applicationScope
    }
}