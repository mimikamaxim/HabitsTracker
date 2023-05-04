package com.example.habitstracker

import android.app.Application
import android.content.Context
import com.example.habitstracker.data.HabitsLocalSQLRepository
import com.example.habitstracker.data.room.HabitsRoomDatabase
import com.example.habitstracker.domain.Interaction
import com.example.habitstracker.presentation.DetailHabitFragment
import com.example.habitstracker.presentation.DetailHabitFragmentViewModel
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

    //    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//        fun build(): AppComponent
//    }
    fun inject(interaction: Interaction)
    fun inject(habitsApplication: HabitsApplication)
    fun inject(detailHabitFragmentViewModel: DetailHabitFragmentViewModel)
    fun inject(habitsListViewModel: HabitsListViewModel)
    fun inject(detailHabitFragment: DetailHabitFragment)
    fun inject(habitsFragment: HabitsFragment)

    //    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//        fun build(): AppComponent
//    }
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}

@Module
object SQL_RoomRepository {
    @Provides
    fun getRepository(@ApplicationContext context: Context): HabitsLocalSQLRepository {
        val database by lazy { HabitsRoomDatabase.getDatabase(context) }
        val repositorySQL by lazy { HabitsLocalSQLRepository(database.habitsDAO()) }
        return repositorySQL
    }

    @Provides
    fun getInteraction(repo: HabitsLocalSQLRepository): Interaction {
        return Interaction(repo)
    }
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
