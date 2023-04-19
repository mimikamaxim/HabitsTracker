package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.*
import com.example.habitstracker.domain.Interaction
import kotlinx.coroutines.launch

class ListViewModel(
    listType: HabitsListType = HabitsListType.ALL
) : ViewModel() {
    class ViewModelFactory( //todo? in class or out
        private val habitsListType: HabitsListType
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListViewModel(habitsListType) as T
        }
    }

    private var originalList = listOf<HabitItemPresentationModel>()
    private val _list: MutableLiveData<List<HabitItemPresentationModel>> = MutableLiveData()
    val list: LiveData<List<HabitItemPresentationModel>> = _list

    private val presentationList = Interaction.getPresentationList()

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
}