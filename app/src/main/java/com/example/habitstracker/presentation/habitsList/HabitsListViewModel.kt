package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.presentation.HabitItemPresentationModel
import com.example.habitstracker.presentation.IInteraction
import kotlinx.coroutines.launch

class HabitsListViewModel(
    listType: HabitsListType = HabitsListType.ALL,
    val interaction: IInteraction //injected
) : ViewModel() {

    private var originalList = listOf<HabitItemPresentationModel>()
    private val _list: MutableLiveData<List<HabitItemPresentationModel>> = MutableLiveData()
    val list: LiveData<List<HabitItemPresentationModel>> = _list


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
        interaction.addDone(id)
    }
}