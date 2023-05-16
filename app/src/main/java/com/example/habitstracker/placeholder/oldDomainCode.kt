package com.example.habitstracker.placeholder

//class ConverterFactory(
//    private val context: Context,
////    private val lifecycleOwner: LifecycleOwner
//) {
//    companion object {
//        var converter: Converter?=null
//    }
//
//    fun getInstance(): Converter{
//        if (converter==null)
//            converter = Converter(context)
////        converter = Converter(context,lifecycleOwner)
//        else
////            converter!!.setParam(context,lifecycleOwner)
//            converter!!.setParam(context)
//        return converter!!
//    }
//}


//class Converter(var context: Context) {
//object Interactor {
//    class Converter(var context: Context,var lifecycleOwner: LifecycleOwner) {

//init {
//        refill()
//        repoList.observe(lifecycleOwner) {
//            refill()
//        }
//        MainScope().launch {
//            dataList.collect { repositoryList ->
//                val innerList = mutableListOf<HabitItemPresentationModel>()
//                repositoryList.forEach {
//                    innerList.add(
//                        HabitItemPresentationModel(
//                            it.name,
//                            it.description,
//                            it.priority,
//                            it.isGood,
//                            it.periodInDays,
//                            it.period,
//                            it.color,
//                            it.id ?: throw Exception("no ID")
//                        )
//                    )
//                }
//                _presentationList.value = innerList
//            }
//        }
//    }

//    fun setParam(context: Context){ //, lifecycleOwner: LifecycleOwner
//        this.context = context
//        this.lifecycleOwner = lifecycleOwner
//    }

//    private fun refill() {
//        _presentationList.value = mutableListOf()
//        repoList.value?.forEach {
//            _presentationList.value?.add(
//                HabitItemPresentationModel(
//                    it.name,
//                    it.description,
//                    it.priority,
//                    it.isGood,
//                    it.periodInDays,
//                    it.period,
//                    it.color,
//                    it.id ?: throw Exception("no ID")
//                )
//            )
//        }
//}

//    private val _presentationList: MutableLiveData<MutableList<HabitItemPresentationModel>> =
//        MutableLiveData()
//    val presentationList: LiveData<MutableList<HabitItemPresentationModel>> = _presentationList

//    val repositorySQL = Helper.repositorySQL
//    val database by lazy { HabitsRoomDatabase.getDatabase(MainActivity.contextBase!!) }
//    val repositorySQL by lazy { HabitsLocalSQLRepository(database.habitsDAO()) }
//    @Inject
//    lateinit var repositorySQL: HabitsLocalSQLRepository
//    var repositorySQL1: HabitsLocalSQLRepository? =null
//    var repositorySQL: HabitsLocalSQLRepository

//    init {
//        HabitsApplication.appComponent.inject(this)
//        repositorySQL = repositorySQL1!!
//    }

//    init {
//        val daggerComponent = DaggerAppComponent
//            .builder()
//            .build()//todo как инжектить в поле если нет application
//        daggerComponent.inject(this)
////        HabitsApplication.appComponent.inject(this)
//    }