package com.example.habitstracker

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow

class HabitsApplication : Application() {
    //    @Inject
//    lateinit var appComponent: AppComponent
//
////    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
////        return DaggerAppComponent.builder()
////            .application(this)
////            .build()
////    }
//
//    override fun onCreate() {
//        super.onCreate()
//        appComponent.inject(this)
//    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }


    companion object {

        val applicationScope = CoroutineScope(SupervisorJob())
        val mainToastFlow = MutableSharedFlow<String>()
    }
}
