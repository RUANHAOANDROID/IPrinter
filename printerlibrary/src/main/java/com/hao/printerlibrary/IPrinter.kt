package com.hao.printerlibrary

import android.graphics.Bitmap

/**
 * 打印功能
 * @date 2022/6/22
 * @author 锅得铁
 * @since v1.0
 */
interface IPrinter {

    /**
     * 初始化打印机
     *
     * @param prnBaudRate 波特率
     * @param prnPort     端口
     * @param prnType     打印类型（650 or 550）
     * @param feedLine    走纸行数
     */
    fun initPrinter(prnBaudRate: Int, prnPort: Int, prnType: Int, feedLine: Int): Boolean

    /**
     * 增加文本
     *
     * @param text  打印内容
     * @param align 对齐方式
     * @param font  字体大小
     */
    fun addText(text: String?, align: Int, font: Int)

    /**
     * 增加文本
     *
     * @param barCode 打印内容
     */
    fun addBarCode(barCode: String?)

    /**
     * 增加文本
     *
     * @param qrCode         打印内容
     * @param offset         起始位置
     * @param expectedHeight 打印高度
     */
    fun addQrCode(qrCode: String?, offset: Int, expectedHeight: Int)

    /**
     * 增加文本
     *
     * @param offset
     * @param width
     * @param height
     */
    fun addImage(imageData: ByteArray?, bitmap: Bitmap?, offset: Int, width: Int, height: Int)

    /**
     * 打印状态
     *
     * @return 0 正常
     */
    fun getStatus(): Int

    /**
     * 走纸行数
     *
     * @param lines 行数
     */
    fun feedLine(lines: Int)

    /**
     * 启动打印
     */
    fun startPrint()

    /**
     * 切纸
     */
    fun cutPaper()

    /**
     * 关闭打印机
     */
    fun closePrinter()
}