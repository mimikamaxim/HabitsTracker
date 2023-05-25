package com.example.habitstracker

import android.app.Application
import android.content.Context
import com.example.data.HabitsLocalSQLRepository
import com.example.data.NetRepository
import com.example.data.room.HabitsRoomDatabase
import com.example.domain.IInteraction
import com.example.domain.Interaction
import com.example.habitstracker.presentation.detail.DetailHabitFragment
import com.example.habitstracker.presentation.detail.DetailHabitFragmentViewModel
import com.example.habitstracker.presentation.habitsList.HabitsFragment
import com.example.habitstracker.presentation.habitsList.HabitsListViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Component(modules = [SQL_RoomRepository::class, ApplicationModule::class])
@Singleton
interface AppComponent {

    fun inject(interaction: Interaction)
    fun inject(habitsApplication: HabitsApplication)
    fun inject(detailHabitFragmentViewModel: DetailHabitFragmentViewModel)
    fun inject(habitsListViewModel: HabitsListViewModel)
    fun inject(detailHabitFragment: DetailHabitFragment)
    fun inject(habitsFragment: HabitsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}

@Module()
object SQL_RoomRepository {
    @Provides
    @Singleton
    fun getRepository(database: HabitsRoomDatabase): HabitsLocalSQLRepository {
        return HabitsLocalSQLRepository(database.habitsDAO())
    }

    @Singleton
    @Provides
    fun getInteraction(
        sqlRepository: HabitsLocalSQLRepository,
        netRepository: NetRepository
    ): Interaction {
        return Interaction(sqlRepository, netRepository)
    }

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): HabitsRoomDatabase {
        return HabitsRoomDatabase.getDatabase(context)
    }

    @Provides
    fun injectIInteraction(interaction: Interaction): IInteraction {
        return interaction
    }

    @Provides
    fun getNetRepository(): NetRepository {
        return NetRepository()
    }

//    @Provides//test
//    fun interInt (): IInteraction{
//        return TestInteraction()
//    }

//    @Provides
//    fun getTestInteraction(): TestInteraction {
//        return TestInteraction()
//    }
}

@Module
object ApplicationModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }
}
