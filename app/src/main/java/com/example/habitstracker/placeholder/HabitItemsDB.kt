package com.example.habitstracker.placeholder

//object HabitItemsDB {
//    private val habitItems = mutableListOf<HabitItemPresentationModel>()
//
//    fun getHabitItemsList(): List<HabitItemPresentationModel> {
//        return habitItems
//    }
//
//    fun addHabit(habit: HabitItemPresentationModel) {
//        habitItems.add(
//            HabitItemPresentationModel(
//                habit.name,
//                habit.description,
//                habit.priority,
//                habit.isGood,
//                habit.periodInDays,
//                habit.period,
//                habit.color,
//                habitItems.size
//            )
//        )
//    }
//
//    fun getHabit(i: Int) = habitItems[i]
//
//    fun updateHabit(position: Int, habit: HabitItemPresentationModel) {
//        habitItems[position] = HabitItemPresentationModel(
//            habit.name,
//            habit.description,
//            habit.priority,
//            habit.isGood,
//            habit.periodInDays,
//            habit.period,
//            habit.color,
//            position
//        )
//    }
//
//    fun fillDBsample() {
//        addHabit(HabitItemPresentationModel("Читать новости", "Читать свежие новости", 2, false, 1, "Раз в день",Color.GRAY))
//        addHabit(HabitItemPresentationModel("Физические упражнения", "Заниматься физической нагрузкой в течении часа", 0, true, 1, "Раз в неделю",Color.RED))
//        addHabit(HabitItemPresentationModel("Магазин", "Ходить за продуктами", 3, true, 1, "Раз в неделю", Color.CYAN))
//        addHabit(HabitItemPresentationModel("Уборка", "Прибраться в комнате", 1, true, 1, "Раз в неделю", Color.MAGENTA))
//        addHabit(HabitItemPresentationModel("Ходить в бар","Ходить с друзьями в бар",3,false,2,"Раз в месяц",Color.YELLOW))
//    }
//
//    fun clearDB() {
//        habitItems.clear()
//    }
//}