package com.hao.printerlibrary

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hao.printerlibrary.hs6500.HS650PrintProvider
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
    lateinit var mPrinter: IPrintProvider
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
        mPrinter.addText("Hello printer\nHi sunmi printer ", 1, 15)
        mPrinter.addQrCode("www.baidu.com",1,2)
        mPrinter.closePrinter()
        assertEquals("看看答应效果吧", "看看答应效果吧")
    }
    @Test
    fun hs6500Printer(){
        mPrinter =HS650PrintProvider()
        mPrinter.initPrinter(115200,1,0,1)
        mPrinter.addText("Hello printer\nHi hs6500 printer ", 1, 15)
        mPrinter.addQrCode("www.baidu.com",1,2)
        mPrinter.closePrinter()
        assertEquals("看看答应效果吧", "看看答应效果吧")
    }
}