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
//                            it.amountDone,
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
//                    it.amountDone,
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