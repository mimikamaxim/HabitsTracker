package com.example.domain

import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class Interaction @Inject constructor(
    var repositorySQL: IHabitsSQLRepository,
    var repositoryNet: INetRepository,
//    var toastProvider: IToastProvider
) : IInteraction {

    private val dataList: Flow<List<DomainHabitEntity>> = repositorySQL.listHabits
    private val scope = CoroutineScope(SupervisorJob())

    init {
        sync()
    }

    override fun getPresentationList() = dataList

    override fun addNewHabitFromPresentation(habit: DomainHabitEntity) {
        scope.launch {
            val id = repositorySQL.insert(habit)
            val uid = repositoryNet.uploadNewHabit(habit)
            repositorySQL.addRemoteUid(id, uid)
        }
    }

    override fun updateHabitFromPresentation(habit: DomainHabitEntity) {
        scope.launch {
            repositorySQL.update(habit)
            if (habit.uid.isEmpty()) {
                addNewHabitFromPresentation(habit)
            } else {
                repositoryNet.updateHabit(habit)
            }
        }
    }

    override suspend fun getPresentationHabit(id: Int): DomainHabitEntity {
        return repositorySQL.getItem(id)
    }

    override fun deleteHabit(id: Int) {
        scope.launch {
            val habit = repositorySQL.getItem(id)
            repositorySQL.delete(id.toLong())
            if (habit.uid.isNotEmpty()) repositoryNet.deleteHabit(habit.uid)
        }
    }

    override suspend fun addDone(id: Int): Pair<ResultAddDate, Int> {
        val job = scope.async {
            var remainder: Int
            val habit = repositorySQL.getItem(id)
            val timeStamp = System.currentTimeMillis() / 1000
            val curLocal = LocalDateTime.now()
            habit.doneDates.add(curLocal)
            val initialDate = habit.initialDate
            //reset period
            if (curLocal > initialDate.plusDays(habit.periodInDays.toLong())) {
                habit.currentCompleteTimes = 0
                habit.initialDate = curLocal
            }
            habit.currentCompleteTimes++
            remainder = habit.frequencyOfAllowedExecutions - habit.currentCompleteTimes
            var pair: Pair<ResultAddDate, Int>
            if (remainder >= 0)
                pair = if (habit.isGood)
                    Pair(ResultAddDate.do_more, remainder)
                else
                    Pair(ResultAddDate.can_do_more, remainder)
            else
                pair = if (habit.isGood)
                    Pair(ResultAddDate.you_done, remainder)
                else
                    Pair(ResultAddDate.stop_doing_it, remainder)
//            if (remainder < habit.frequencyOfAllowedExecutions)
//                if (habit.isGood)
//                    toastProvider.showToast(
//                        buildString {
//                            append(R.string.can_do_it_more_prefix_message)
//                            append(remainder)
//                            append(R.string.can_do_it_more_postfix_message)
//                        }
//                    )
//                else
//                    toastProvider.showToast(
//                        buildString {
//                            append(R.string.can_do_it_more_prefix_message)
//                            append(remainder)
//                            append(R.string.can_do_it_more_postfix_message)
//                        }
//                    )
//            else
//                if (habit.isGood)
//                    toastProvider.showToast(Resources.getString(R.string.you_done_message))
//                else
//                    toastProvider.showToast(Resources.getString(R.string.you_done_message))

            repositorySQL.update(habit)
            if (habit.uid.isNotEmpty()) repositoryNet.addDoneDate(timeStamp, habit.uid)
            pair
        }
        return job.await()
    }

    fun sync() {
        scope.launch {
            var localList = listOf<DomainHabitEntity>()
            dataList.collect {
                localList = it
            }
            val netList = repositoryNet.getNetHabits()
            netList.forEach { netEn ->
                var isSync = false
                localList.forEach { locEn ->
                    if (netEn.uid == locEn.uid) isSync = true
                }
                if (!isSync) repositorySQL.insert(netEn)
            }
            localList.forEach {
                if (it.uid.isEmpty()) {
                    val uid = repositoryNet.uploadNewHabit(it)
                    repositorySQL.addRemoteUid(it.getID().toLong(), uid)
                    if (it.doneDates.isNotEmpty())
                        it.doneDates.forEach {
                            repositoryNet.addDoneDate(
                                it.toEpochSecond(
                                    ZoneOffset.systemDefault().rules.getOffset(
                                        Instant.now()
                                    )
                                ), uid
                            )
                        }
                }
            }
        }
    }
}