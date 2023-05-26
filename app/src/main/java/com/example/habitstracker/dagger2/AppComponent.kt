package com.example.habitstracker.dagger2

import android.app.Application
import com.example.domain.Interaction
import com.example.habitstracker.HabitsApplication
import com.example.habitstracker.presentation.detail.DetailHabitFragment
import com.example.habitstracker.presentation.detail.DetailHabitFragmentViewModel
import com.example.habitstracker.presentation.habitsList.HabitsFragment
import com.example.habitstracker.presentation.habitsList.HabitsListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DomainModule::class, ApplicationModule::class, DataModule::class])
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