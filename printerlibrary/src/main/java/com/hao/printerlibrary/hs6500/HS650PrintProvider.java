package com.hao.printerlibrary.hs6500;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;

import com.hao.printerlibrary.IPrintProvider;
import com.hisense.hs650service.aidl.Printer;


/**
 * HS大屏系列打印提供程序
 */
public class HS650PrintProvider implements IPrintProvider {

    private static final String TAG = "HS650PrintProvider";
    private Printer printer;


    @Override
    public boolean initPrinter(int prnBaudRate, int prnPort, int prnType, int feedLine) {
        boolean result;
        result = HS650DeviceHelper.getInstance().initPrinter(prnBaudRate, prnPort, prnType, feedLine);
        printer = HS650DeviceHelper.getInstance().getPrinter();
        return result;


    }

    @Override
    public void addText(String text, int align, int font) {
        try {
            Log.d(TAG,"打印文本");
            printer.printTextStr(text, 0, align);
            printer.feedPaper(1, 0);
        } catch (RemoteException e) {
            Log.d(TAG,"添加文本异常" + e.getMessage());
        }
    }

    @Override
    public void addBarCode(String barCode) {

        try {
            Log.d(TAG,"打印条形码");
            printer.printBar(barCode);
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 printBar exception----------" + e.toString());
        }

    }

    @Override
    public void addQrCode(String qrCode, int offset, int expectedHeight) {
        try {
            Log.d(TAG,"打印二维码");
            printer.printQR(qrCode, 6);
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 printQR exception----------" + e.toString());
        }
    }

    @Override
    public void addImage(byte[] imageData, Bitmap bitmap, int offset, int width, int height) {
        try {
            Log.d(TAG,"打印图片");
            printer.printImage(bitmap, 1);
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 printImage exception----------" + e.toString());
        }
    }

    @Override
    public int getStatus() {

        try {
            Log.d(TAG,"获取打印状态");

            return printer.printStatus();
        } catch (Exception e) {
            Log.d(TAG,"获取打印状态异常" + e.getMessage());
            return -1;
        }

    }

    @Override
    public void feedLine(int lines) {
        try {
            Log.d(TAG,"走纸 lines");
            printer.feedPaper(lines, 0);
        } catch (Exception e) {
            Log.d(TAG,"走纸异常" + e.getMessage());
        }
    }

    @Override
    public void startPrint() {

        Log.d(TAG,"启动打印");
    }

    @Override
    public void cutPaper() {
        try {
            Log.d(TAG,"切纸打印");
            printer.cutPaper();
        } catch (Exception e) {
            Log.d(TAG,"切纸异常" + e.getMessage());
        }

    }

    @Override
    public void closePrinter() {
        try {
            printer.closePrinter();
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 closePrinter exception----------" + e.toString());
        }
    }
}
