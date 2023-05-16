package com.example.habitstracker.presentation.habitsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.domain.Interaction
import com.example.habitstracker.presentation.HabitItemPresentationModel
import kotlinx.coroutines.launch

class HabitsListViewModel(
    listType: HabitsListType = HabitsListType.ALL,
    interaction: Interaction
) : ViewModel() {

    private var originalList = listOf<HabitItemPresentationModel>()
    private val _list: MutableLiveData<List<HabitItemPresentationModel>> = MutableLiveData()
    val list: LiveData<List<HabitItemPresentationModel>> = _list
//    @Inject
//    lateinit var interaction : Interaction
//    val inter = Interaction()


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
        //получить апп компонент

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