package com.example.habitstracker

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented converterList, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under converterList.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.habitstracker", appContext.packageName)
    }

    @Test
    fun test2() {
        println("Hi")
    }
}