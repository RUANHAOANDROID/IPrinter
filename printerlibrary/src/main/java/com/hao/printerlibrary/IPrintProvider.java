package com.hao.printerlibrary;


import android.content.Context;
import android.graphics.Bitmap;

/**
 * 打印提供接口
 *
 * @author xieliyan  2019-11-13
 */
public interface IPrintProvider {

    /**
     * 初始化打印机
     *
     * @param prnBaudRate 波特率
     * @param prnPort     端口
     * @param prnType     打印类型（650 or 550）
     * @param feedLine    走纸行数
     */
    boolean initPrinter(int prnBaudRate, int prnPort, int prnType, int feedLine);

    /**
     * 增加文本
     *
     * @param text  打印内容
     * @param align 对齐方式
     * @param font  字体大小
     */
    void addText(String text, int align, int font);

    /**
     * 增加文本
     *
     * @param barCode 打印内容
     */
    void addBarCode(String barCode);

    /**
     * 增加文本
     *
     * @param qrCode         打印内容
     * @param offset         起始位置
     * @param expectedHeight 打印高度
     */
    void addQrCode(String qrCode, int offset, int expectedHeight);

    /**
     * 增加文本
     *
     * @param offset
     * @param width
     * @param height
     */
    void addImage(byte[] imageData, Bitmap bitmap, int offset, int width, int height);

    /**
     * 打印状态
     *
     * @return 0 正常
     */
    int getStatus();

    /**
     * 走纸行数
     *
     * @param lines 行数
     */
    void feedLine(int lines);

    /**
     * 启动打印
     */
    void startPrint();

    /**
     * 切纸
     */
    void cutPaper();

    /**
     * 关闭打印机
     */
    void closePrinter();
}
