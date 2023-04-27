package com.example.habitstracker.data.net

import com.example.habitstracker.data.net.entity.NetHabitEntity
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val netInt = NetRepository()
//        var list = netInt.getNetHabits()
//        list.forEach { println(it) }
//        println("_______________________________________________________")
//        val t1:String = netInt.uploadNewHabit(NetNewHabitEntity(0,1,2,"t2", listOf(),1,1,"t2",1))
//        println("_______________________________________________________")
//        println(t1)
//        list = netInt.getNetHabits()
//        list.forEach { println(it) }
//        println("_______________________________________________________")
//        netInt.deleteHabit(t1)
//        list = netInt.getNetHabits()
//        list.forEach { println(it) }
//        netInt.deleteHabit(t1)
        netInt.updateHabit(
            NetHabitEntity(
                0,
                1,
                4,
                "test2",
                listOf(),
                1,
                1,
                "t2",
                1,
                "0b23236f-d74f-4cad-a5d8-d3126c9a300a"
            )
        )
//        netInt.addDoneDate(2,"0b23236f-d74f-4cad-a5d8-d3126c9a300a")
    }
}