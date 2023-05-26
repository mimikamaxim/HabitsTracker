package com.example.habitstracker.dagger2

import com.example.data.HabitsLocalSQLRepository
import com.example.data.NetRepository
import com.example.domain.IInteraction
import com.example.domain.Interaction
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
object DomainModule {

    @Singleton
    @Provides
    fun getInteraction(
        sqlRepository: HabitsLocalSQLRepository,
        netRepository: NetRepository,
        scope: CoroutineScope
    ): Interaction {
        return Interaction(sqlRepository, netRepository, scope)
    }

    @Provides
    fun injectIInteraction(interaction: Interaction): IInteraction {
        return interaction
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