package com.example.habitstracker.presentation.habitsList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.IInteraction
import com.example.domain.ResultAddDate
import com.example.domain.entitys.DomainHabitEntity
import com.example.habitstracker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class HabitsListViewModel(
    listType: HabitsListType = HabitsListType.ALL,
    val interaction: IInteraction,
    context: Context
) : ViewModel() {
    private val context = WeakReference(context)
    private var originalList = listOf<DomainHabitEntity>()
    private val _list: MutableLiveData<List<DomainHabitEntity>> = MutableLiveData()
    val list: LiveData<List<DomainHabitEntity>> = _list

    val toastChannel = Channel<String?>(Channel.CONFLATED)

    private val presentationList = interaction.getPresentationList()

    init {
        viewModelScope.launch {
            presentationList.collect {
                _list.value = when (listType) {
                    HabitsListType.ALL -> it
                    HabitsListType.BAD -> HabitsListFilter.filterBadHabit(it)
                    HabitsListType.GOOD -> HabitsListFilter.filterGoodHabit(it)
                }
                originalList = list.value!!
            }
        }
    }

    fun findByHabitName(request: String) {
        _list.value = HabitsListFilter.filterHabitName(request, originalList)
    }

    fun findInHabitDescription(request: String) {
        _list.value = HabitsListFilter.filterHabitDescription(request, originalList)
    }

    fun sortByHabitName() {
        _list.value = HabitsListFilter.sortByHabitName(list.value!!)
    }

    fun sortByHabitId() {
        _list.value = HabitsListFilter.sortByHabitId(list.value!!)
    }

    fun deleteHabit(id: Int) {
        interaction.deleteHabit(id)
    }

    fun addDone(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = interaction.addDone(id)
            val resultType = result.first
            val resultReminder = result.second
            when (resultType) {
                ResultAddDate.do_more -> sendToast(
                    context.get()?.getString(R.string.do_more_prefix_message, resultReminder)
                )
                ResultAddDate.can_do_more -> sendToast(
                    context.get()?.getString(R.string.can_do_it_more_prefix_message, resultReminder)
                )
                ResultAddDate.you_done -> sendToast(
                    context.get()?.getString(R.string.you_done_message)
                )
                ResultAddDate.stop_doing_it -> sendToast(
                    context.get()?.getString(R.string.stop_doing_this_message)
                )
            }
        }
    }

    private fun sendToast(s: String?) {
        viewModelScope.launch {
            toastChannel.send(s)
        }
    }
}