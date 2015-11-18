package com.yang.testwificonnect;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String WIFI_NAME = "711";

    public static final int MSG_CONNECT_WIFI = 0x100;
    public static final int MSG_HAND_CONNECT_WIFI = 0x101;

    private WifiReceiver wifiReceiver;

    private Handler mHandler;
    private IStaticHandler staticHandler = new IStaticHandler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_CONNECT_WIFI:
                    WifiInfo info = (WifiInfo) msg.obj;
                    if(info == null){
                        Log.d(TAG,"wifi info is null");
                    }else{
                        Log.d(TAG,"wifiinfo="+info);
                        boolean wifiTestResult = WifiUtil.connectWifi(MainActivity.this
                                , info.getSsid(), info.getBssid(), info.getPassWord());
                        Log.d(TAG, "wifiTestResult=" + wifiTestResult);
                    }
                    break;
                case MSG_HAND_CONNECT_WIFI:
                    List<ScanResult> wifiResults = mWifiManager.getScanResults();
                    for(ScanResult result : wifiResults){
                        Log.d(TAG,"SSID"+result.SSID);
                        if((result.SSID).contains(WIFI_NAME)) {
                            mHandler.obtainMessage(MainActivity.MSG_CONNECT_WIFI
                                    ,new WifiInfo(result.SSID,result.BSSID,getString(R.string.test_wifi_password))).sendToTarget();
                            break;
                        }
                    }
                    break;
            }
        }
    };

    private WifiManager mWifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = StaticHandlerFactory.create(staticHandler);

        wifiReceiver = new WifiReceiver(mHandler);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        registerReceiver(wifiReceiver,filter);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(mWifiManager.isWifiEnabled()){
            String connectedSsid = mWifiManager.getConnectionInfo().getSSID();
            Log.d(TAG,"connectedSsid="+connectedSsid);
            if(connectedSsid.contains(WIFI_NAME)){
                Log.d(TAG,"rpdzkj is connected");
            }else{
                //try to connect 'rpdzkj wifi'
                Log.d(TAG, "rpdzkj wifi is not connected,continue to connect it");
                mHandler.sendEmptyMessage(MSG_HAND_CONNECT_WIFI);
            }
        }else{
            Log.d(TAG,"wifi is disabled,try to open it ...");
            boolean enabled =  mWifiManager.setWifiEnabled(true);
            if(enabled){
                Log.d(TAG,"Open wifi success , scanning rpdzkj wifi ssid");
            }else{
                Log.d(TAG,"Open wifi Failure");
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wifiReceiver != null)
            unregisterReceiver(wifiReceiver);
    }
}
