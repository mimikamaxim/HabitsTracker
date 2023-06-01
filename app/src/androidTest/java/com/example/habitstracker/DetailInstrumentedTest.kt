package com.example.habitstracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.habitstracker.presentation.detail.DetailHabitFragment
import com.example.habitstracker.presentation.detail.DetailHabitFragmentViewModel
import com.example.habitstracker.presentation.detail.DetailViewModelFactory
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented converterList, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)

class DetailInstrumentedTest {

    val testInteraction = TestInteraction()

    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private val detailVM: DetailHabitFragmentViewModel =
        DetailViewModelFactory(
            null,
            testInteraction
        ).create(DetailHabitFragmentViewModel::class.java)

    private lateinit var scenario: AutoCloseable

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer { DetailHabitFragment() }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testFragment() {
        onView(withId(R.id.detailHabitFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnSave() {
        onView(withId(R.id.save_btn)).perform(click())
        verify {
            detailVM.saveHabit()
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under converterList.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.habitstracker", appContext.packageName)
    }
}