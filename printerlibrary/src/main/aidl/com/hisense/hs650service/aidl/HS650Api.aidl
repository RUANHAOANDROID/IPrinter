package com.hisense.hs650service.aidl;

import com.hisense.hs650service.aidl.Printer;
import com.hisense.hs650service.aidl.LedStripe;

 interface HS650Api {

   Printer getPrinter();
   LedStripe getLedStripe();
}
