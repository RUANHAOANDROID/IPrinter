package com.hao.printerlibrary

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hao.printerlibrary.sunmi.SunmiPrintProvider
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * printer test
 * @date 2022/6/23
 * @author 锅得铁
 * @since v1.0
 */
@RunWith(AndroidJUnit4::class)
class PrinterTest {
    lateinit var mPrinter: SunmiPrintProvider
    lateinit var appContext: Context

    @Before
    fun initContext() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun sunmiD2SPrinter() {
        mPrinter = SunmiPrintProvider(appContext)
        Thread.sleep(5000)
        mPrinter.initPrinter(0, 0, 0, 0)
        mPrinter.addText("HH---------为啥\n sfewfaefaeewafewafewafewafewf", 1, 15)
        mPrinter.addText("HH", 1, 15)
        mPrinter.addQrCode("www.baidu.com",1,2)
        assertEquals("看看答应效果吧", "看看答应效果吧")
    }

}