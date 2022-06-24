package com.hao.printerlibrary.hs6500;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.hisense.hs650service.aidl.HS650Api;
import com.hisense.hs650service.aidl.Printer;

/**
 * HS650 打印服务调用
 */
public class HS650DeviceHelper {

    private static final String TAG = "HS650DeviceHelper";
    private static final String PRINTER_SERVICE_PACKAGE = "com.hisense.hs650api";

    private static final int PRINTER_TYPE_58 = 0;
    private static final int PRINTER_TYPE_80 = 1;

    private static final String BA_T500 = "BA-T500";
    private static final String TPM80 = "TPM80";

    private volatile static HS650DeviceHelper printerHS650Helper;
    private static Printer printer = null;

    private Context context;

    private static HS650Api hs650ApiHandler = null;
    private static int prnBaudRate = 115200;   //打印机当前波特率，默认115200
    private static int prnPort = 1;  //打印机端口号    1 COM2
    private static int prnType = 0;  //打印机型号，0:58MM 1:80mm,型号TPM80
    private static int feedLine = 0;   //打印完成走纸行数

    private boolean isBindService;

    private HS650DeviceHelper() {

    }

    public void init(Context context) {
        this.context = context;
    }

    public static HS650DeviceHelper getInstance() {
        if (printerHS650Helper == null) {
            synchronized (HS650DeviceHelper.class) {
                if (printerHS650Helper == null) {
                    printerHS650Helper = new HS650DeviceHelper();
                }
            }
        }

        return printerHS650Helper;
    }

    public void bindService() {
        if (!isBindService) {
            Intent intent = new Intent();
            intent.setPackage(PRINTER_SERVICE_PACKAGE);
            boolean bindSucc = context.bindService(intent, conn, Service.BIND_AUTO_CREATE);
        }
    }

    public void unbindService() {
        if (isBindService) {
            context.unbindService(conn);
            isBindService = false;
        }
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBindService = true;
            hs650ApiHandler = HS650Api.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBindService = false;
            hs650ApiHandler = null;
        }
    };

    public Printer getPrinter() {
        try {
            if (hs650ApiHandler == null) {
                Log.d(TAG,"-------------hs650 getPrinter hs650ApiHandler null----------");
                return null;
            }

            printer = hs650ApiHandler.getPrinter();
            if (printer == null) {
                Log.d(TAG,"-------------hs650 getPrinter fail----------");
                return null;
            }
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 getPrinter exception----------" + e.toString());
        }

        return printer;
    }

    private void getPrnType() {
        try {
            if (printer == null) {
                getPrinter();
            }

            String strPrnType = printer.getPrnType(prnBaudRate, prnPort, 500);

            if (TPM80.equals(strPrnType)) {
                prnType = PRINTER_TYPE_80;
            }
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 getPrnType exception----------" + e.toString());
        }
    }

    private boolean printerConfig() {
        int result;
        try {
            if (printer == null) {
                getPrinter();
            }

            result = printer.printerConfig(prnBaudRate, prnPort, true);
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printerConfig exception----------" + e.toString());
        }

        return result == 0;
    }

    private boolean initPrinter() {
        int result;
        try {
            result = printer.initPrinter(prnType);
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 initPrinter exception----------" + e.toString());
        }

        return result == 0;
    }


    public boolean initPrinter(int baudRate, int port, int type, int line) {

        prnBaudRate = baudRate;   //打印机当前波特率，默认115200
        prnPort = port;  //打印机端口号    1 COM2
        prnType = type;  //打印机型号，0:58MM 1:80mm,型号TPM80
        feedLine = line;
        boolean result = false;
        getPrinter();
        if (printerConfig()) {
            result = initPrinter();
        }
        Log.d(TAG,prnBaudRate+prnPort+prnType+"");
        if (!result) {
            Log.d(TAG,"-------------hs650 initPrinterV2 fail----------请确认打印机波特率和端口号");
        }

        return result;
    }

    public int printStatus() {
        int result;
        try {
            result = printer.printStatus();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printStatus exception----------" + e.toString());
        }

        return result;
    }


    public boolean printImage(Bitmap bitmap) {
        int result;
        try {
            result = printer.printImage(bitmap, 1);
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printImage exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printImageV2(Bitmap bitmap) {
        int result;
        try {
            result = printer.printImage(bitmap, 1);
            printer.feedPaper(feedLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printImageV2 exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printTextStr(String text, int pattern) {
        int result;
        try {
            result = printer.printTextStr(text, 0, pattern);
            printer.feedPaper(1, 0);
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printTextStr exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printTextStrV2(String text) {
        int result;
        try {
            result = printer.printTextStr(text, 0, 0);
            printer.feedPaper(feedLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printTextStrV2 exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printBar(String barcode) {
        int result;
        try {
            result = printer.printBar(barcode);
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printBar exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printBar(String barcode, int topFeedLine, int bottomFeeLine) {
        int result;
        try {
            printer.feedPaper(topFeedLine, 0);
            result = printer.printBar(barcode);
            printer.feedPaper(bottomFeeLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printBar exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printBarV2(String barcode) {
        int result;
        try {
            result = printer.printBar(barcode);
            printer.feedPaper(feedLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printBarV2 exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printQR(String barcode) {
        int result;
        try {
            result = printer.printQR(barcode, 6);
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printQR exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printQR(String barcode, int mode, int topFeedLine, int bottomFeeLine) {
        int result;
        try {
            printer.feedPaper(topFeedLine, 0);
            result = printer.printQR(barcode, mode);
            printer.feedPaper(bottomFeeLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printQR exception----------" + e.toString());
        }

        return result == 0;
    }

    public boolean printQRV2(String barcode) {
        int result;
        try {
            result = printer.printQR(barcode, 6);
            printer.feedPaper(feedLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            result = 1;
            Log.d(TAG,"-------------hs650 printQRV2 exception----------" + e.toString());
        }

        return result == 0;
    }

    public void feedPaper(int line) {
        try {
            printer.feedPaper(line, 0);
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 feedPaper exception----------" + e.toString());
        }
    }

    public void feedPaper() {
        try {
            printer.feedPaper(feedLine, 0);
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 feedPaper feedLine exception----------" + e.toString());
        }
    }

    public void feedPaperV2() {
        try {
            printer.feedPaper(feedLine, 0);
            printer.cutPaper();
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 feedPaperV2 exception----------" + e.toString());
        }
    }

    public void cutPaper() {
        try {
            printer.cutPaper();
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 cutPaper exception----------" + e.toString());
        }
    }

    public void closePrinter() {
        try {
            printer.closePrinter();
        } catch (RemoteException e) {
            Log.d(TAG,"-------------hs650 closePrinter exception----------" + e.toString());
        }
    }


}
