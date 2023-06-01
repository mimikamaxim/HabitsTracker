package com.example.domain

import com.example.domain.entitys.DomainHabitEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime

val badHabit = DomainHabitEntity(
    "tes1",
    "descr",
    uid = "uid",
    priority = 0,
    frequencyOfAllowedExecutions = 2,
    isGood = false,
    periodInDays = 1,
    color = 0,
    currentCompleteTimes = 0,
    doneDates = mutableListOf(),
    initialDate = LocalDateTime.of(2020, 1, 1, 3, 1),
    totalCompleteTimes = 0,
    id = 1
)

val badHabit2 = DomainHabitEntity(
    "tes3",
    "descr",
    uid = "uid",
    priority = 0,
    frequencyOfAllowedExecutions = 1,
    isGood = false,
    periodInDays = 1,
    color = 0,
    currentCompleteTimes = 0,
    doneDates = mutableListOf(),
    initialDate = LocalDateTime.of(2021, 1, 1, 3, 1),
    totalCompleteTimes = 0,
    id = 2
)

val testList: MutableList<DomainHabitEntity> =
    mutableListOf(
        DomainHabitEntity(
            "tes1",
            "descr",
            uid = "uid",
            priority = 0,
            frequencyOfAllowedExecutions = 1,
            isGood = false,
            periodInDays = 1,
            color = 0,
            currentCompleteTimes = 0,
            doneDates = mutableListOf(),
            initialDate = LocalDateTime.of(2022, 1, 1, 3, 1),
            totalCompleteTimes = 0,
            id = 1
        )
    )

class InteractionTest {

    @Test
    fun testAddNewHabitFromPresentation() = runBlocking {
        val interaction = Interaction(
            repositoryNet = NetTestRepository(),
            repositorySQL = SQLTestRepository(testList),
            scope = this@runBlocking
        )
        interaction.addNewHabitFromPresentation(badHabit)
        val presentationFlow = interaction.getPresentationList()
        var presentationList: List<DomainHabitEntity> = listOf()
        presentationFlow.collect {
            presentationList = it
        }
        assertEquals(testList.size, presentationList.size - 1)
    }

    @Test
    fun testUpdateHabitFromPresentation() = runBlocking {
        val habit = badHabit2
        val interaction = Interaction(
            repositoryNet = NetTestRepository(),
            repositorySQL = SQLTestRepository(testList),
            scope = this@runBlocking
        )
        interaction.updateHabitFromPresentation(habit)
        val presentationFlow = interaction.getPresentationList()
        var presentationList: List<DomainHabitEntity> = listOf()
        presentationFlow.collect {
            presentationList = it
        }
        assertTrue(presentationList.contains(habit))
    }

    @Test
    fun testGetPresentationHabit() = runBlocking {
        val habit = testList[0]
        val interaction = Interaction(
            repositoryNet = NetTestRepository(),
            repositorySQL = SQLTestRepository(testList),
            scope = this@runBlocking
        )
        val presentationHabit = interaction.getPresentationHabit(0)
        assertEquals(true, habit == presentationHabit)
    }

    @Test
    fun testDeleteHabit() = runBlocking {
        val habit = badHabit2
        val interaction = Interaction(
            repositoryNet = NetTestRepository(),
            repositorySQL = SQLTestRepository(testList),
            scope = this@runBlocking
        )
        interaction.addNewHabitFromPresentation(habit)
        val presentationFlow = interaction.getPresentationList()
        var presentationList: List<DomainHabitEntity> = listOf()
        presentationFlow.collect {
            presentationList = it
        }
        interaction.deleteHabit(presentationList.size - 1)
        assertFalse(presentationList.contains(habit))
    }

    @Test
    fun addDoneTest() = runBlocking {
        val habit = badHabit
        val interaction = Interaction(
            repositoryNet = NetTestRepository(),
            repositorySQL = SQLTestRepository(testList),
            scope = this@runBlocking
        )
        interaction.addNewHabitFromPresentation(habit)
        val presentationFlow = interaction.getPresentationList()
        var presentationList: List<DomainHabitEntity> = listOf()
        presentationFlow.collect {
            presentationList = it
        }
        val lastIndex = presentationList.lastIndex
        val result = interaction.addDone(lastIndex)
        assertTrue(result is CanDoMore)
    }
}


class SQLTestRepository(@Volatile var testList: MutableList<DomainHabitEntity>) :
    IHabitsSQLRepository {

    override val listHabits: Flow<List<DomainHabitEntity>>
        get() = flowOf(testList)

    override suspend fun insert(habit: DomainHabitEntity): Long {
        testList.add(habit)
        return 0
    }

    override suspend fun update(habit: DomainHabitEntity) {
        testList.set(0, habit)
    }

    override suspend fun getItem(id: Int): DomainHabitEntity {
        return testList[id]
    }

    override suspend fun getLastItem(): DomainHabitEntity {
        return testList.last()
    }

    override suspend fun addRemoteUid(id: Long, uid: String) {
    }

    override suspend fun delete(id: Long) {
    }
}

class NetTestRepository : INetRepository {

    override suspend fun getNetHabits(): List<DomainHabitEntity> {
        return testList
    }

    override suspend fun uploadNewHabit(habit: DomainHabitEntity): String {
        return "uid"
    }

    override suspend fun updateHabit(habit: DomainHabitEntity) {
        return Unit
    }

    override suspend fun addDoneDate(date: Long, iud: String) {
    }

    override suspend fun deleteHabit(uid: String) {
    }

}