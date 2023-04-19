package com.example.habitstracker.placeholder

//, context: Context, lifecycleOwner: LifecycleOwner,
//    val repository: HabitsRepository
//
//    private val habitList = repository.listHabits
//    private val filters = MutableStateFlow(ExFilters())
//    private val filteredHabits = combine(habitList,filters) { habits, filter ->
//
//    }
//    val converter: Converter = ConverterFactory(context).getInstance()

//        presentationList.observe(lifecycleOwner) {
//            Log.i("LIST", presentationList.value.toString())
//            _list.value = when (listType) {
//                HabitsListType.ALL -> it
//                HabitsListType.BAD -> HabitsListFilter.filterBadHabit(it)
//                HabitsListType.GOOD -> HabitsListFilter.filterGoodHabit(it)
//            }
//            originalList = list.value!!
//        }
//        myLogger("VM list init $listType")