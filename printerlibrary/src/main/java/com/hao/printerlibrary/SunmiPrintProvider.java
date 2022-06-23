package com.hao.printerlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


public class SunmiPrintProvider implements IPrintProvider {
    private static final String TAG = "SunmiPrintProvider";
    private final Context context;

    public SunmiPrintProvider(Context context) {
        this.context = context;
        SunmiPrintHelper.getInstance().initSunmiPrinterService(context);
    }

    @Override
    public boolean initPrinter(int prnBaudRate, int prnPort, int prnType, int feedLine) {
        SunmiPrintHelper.getInstance().initPrinter();
        Log.d(TAG, "商米T2机型内置打印机--初始化");
        return true;
    }

    @Override
    public void addText(String text, int align, int font) {
        try {
            Log.d(TAG, "商米T2内置打印机--打印文本");
            SunmiPrintHelper.getInstance().setAlign(align);
            SunmiPrintHelper.getInstance().printText(text, (float) 24.0, false, false, "typeface");
            SunmiPrintHelper.getInstance().feedPaper();
        } catch (Exception e) {
            Log.d(TAG, "商米T2内置打印机添加文本异常" + e.getMessage());
        }
    }

    @Override
    public void addBarCode(String barCode) {
        try {
            Log.d(TAG, "商米T2内置打印机--打印条形码");
            SunmiPrintHelper.getInstance().setAlign(1);
            SunmiPrintHelper.getInstance().printBarCode(barCode, 8, 160, 2, 2);
            SunmiPrintHelper.getInstance().feedPaper();
        } catch (Exception e) {
            Log.d(TAG, "-------------商米T2内置打印机 exception----------" + e.toString());
        }
    }

    @Override
    public void addQrCode(String qrCode, int offset, int expectedHeight) {
        try {
            Log.d(TAG, "商米T2内置打印机--打印二维码");
            SunmiPrintHelper.getInstance().setAlign(1);
            SunmiPrintHelper.getInstance().printQr(qrCode, 4, 3);
            SunmiPrintHelper.getInstance().feedPaper();
        } catch (Exception e) {
            Log.d(TAG, "-------------商米T2内置打印机 printQR exception----------" + e.toString());
        }
    }

    @Override
    public void addImage(byte[] imageData, Bitmap bitmap, int offset, int width, int height) {
        try {
            Log.d(TAG, "商米T2内置打印机--打印图片");
            SunmiPrintHelper.getInstance().printBitmap(bitmap, 0);
            SunmiPrintHelper.getInstance().feedPaper();
        } catch (Exception e) {
            Log.d(TAG, "-------------商米T2内置打印机 printImage exception----------" + e.toString());
        }
    }

    @Override
    public int getStatus() {
        try {
            Log.d(TAG, "商米T2内置打印机--获取打印状态");
            return SunmiPrintHelper.getInstance().showPrinterStatus();
        } catch (Exception e) {
            Log.d(TAG, "商米T2内置打印机--获取打印状态异常" + e.getMessage());
            return -1;
        }
    }

    @Override
    public void feedLine(int lines) {
        try {
            Log.d(TAG, "商米T2内置打印机--走纸 lines");
            SunmiPrintHelper.getInstance().feedPaper();
        } catch (Exception e) {
            Log.d(TAG, "商米T2内置打印机--走纸异常" + e.getMessage());
        }
    }

    @Override
    public void startPrint() {
        Log.d(TAG, "商米T2内置打印机--启动打印");
    }

    @Override
    public void cutPaper() {
        try {
            Log.d(TAG, "商米T2内置打印机--切纸打印");
            SunmiPrintHelper.getInstance().cutpaper();
        } catch (Exception e) {
            Log.d(TAG, "商米T2内置打印机--切纸异常" + e.getMessage());
        }
    }

    @Override
    public void closePrinter() {
        Log.d(TAG, "商米T2内置打印机--执行关闭打印机语句");
    }
}
