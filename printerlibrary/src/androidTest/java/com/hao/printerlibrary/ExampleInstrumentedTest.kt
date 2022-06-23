package com.hao.printerlibrary

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val mPrinter: IPrintProvider = SunmiPrintProvider(appContext)
        Thread.sleep(5000)
        mPrinter.initPrinter(0,0,0,0)
        mPrinter.addText("HH---------为啥\n sfewfaefaeewafewafewafewafewf", 1, 15)
        mPrinter.addText("HH", 1, 15)
        assertEquals("com.hao.printerlibrary.test", appContext.packageName)
    }


}