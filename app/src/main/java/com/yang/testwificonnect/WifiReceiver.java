package com.yang.testwificonnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

//import com.rpdzkj.yangbin.rp_rk_testapp.model.Wifi;
//import com.rpdzkj.yangbin.rp_rk_testapp.utils.Constant;

import java.util.List;

public class WifiReceiver extends BroadcastReceiver {

    private static final String TAG = WifiReceiver.class.getSimpleName();

//    private Wifi wifi;
    private Handler postHandler;

    public WifiReceiver() {
    }

    public WifiReceiver(Handler postHandler) {
//        this.wifi = wifi;
        this.postHandler = postHandler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

//        if(!wifi.isEnableConnect()){
//            return;
//        }


        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String action = intent.getAction();

        Log.d(TAG, "action=" + action);
        if(mWifiManager.getConnectionInfo().getSSID().contains("711")){
            Log.d(TAG,"rpdzkj is connected");
            return;
        }

        if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
            List<ScanResult> wifiResults = mWifiManager.getScanResults();
//            postHandler.obtainMessage(Constant.MSG_GET_WIFI_SCAN_RESULT,mWifiManager).sendToTarget();
            for(ScanResult result : wifiResults){
                Log.d(TAG,"SSID"+result.SSID);
                if((result.SSID).contains("711")){
                    postHandler.obtainMessage(MainActivity.MSG_CONNECT_WIFI
                            ,new WifiInfo(result.SSID,result.BSSID,context.getString(R.string.test_wifi_password))).sendToTarget();
                    break;
                }
            }
//            wifi.setEnableConnect(false);
        }

    }
}
