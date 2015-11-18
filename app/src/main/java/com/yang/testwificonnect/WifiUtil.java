package com.yang.testwificonnect;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.Iterator;

/**
 * author: ybjaychou@gmail.com
 * data: 2015-10-13
 */
public class WifiUtil {
    private static final String TAG = "WifiUtil";

    private static WifiConfiguration setupWifiParams(String ssid,String bssid,String password){
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.BSSID = bssid;
        wifiConfiguration.SSID = "\""+ssid+"\"";
        wifiConfiguration.preSharedKey = "\""+password+"\"";
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return wifiConfiguration;
    }

    public static boolean connectWifi(Context context,String ssid,String bssid,String password){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Iterator iterator = wifiManager.getConfiguredNetworks().iterator();
        int networkId = 0;
        while (iterator.hasNext()){
            WifiConfiguration configuration = (WifiConfiguration) iterator.next();
            if(bssid.equals(configuration.BSSID)){
                networkId = configuration.networkId;
                Log.d(TAG,"Configured networkId="+networkId);
                return wifiManager.enableNetwork(networkId,true);
            }
        }

        networkId = wifiManager.addNetwork(setupWifiParams(ssid,bssid,password));
        Log.d(TAG,"networkId="+networkId);
        return wifiManager.enableNetwork(networkId,true);

    }


}
