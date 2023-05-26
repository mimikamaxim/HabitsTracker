package com.example.habitstracker.dagger2

import android.content.Context
import com.example.data.HabitsLocalSQLRepository
import com.example.data.NetRepository
import com.example.data.room.HabitsRoomDatabase
import com.example.habitstracker.ApplicationContext
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
object DataModule {
    @Provides
    @Singleton
    fun getNetRepository(scope: CoroutineScope): NetRepository {
        return NetRepository(scope)
    }

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): HabitsRoomDatabase {
        return HabitsRoomDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun getRepository(database: HabitsRoomDatabase): HabitsLocalSQLRepository {
        return HabitsLocalSQLRepository(database.habitsDAO())
    }
}