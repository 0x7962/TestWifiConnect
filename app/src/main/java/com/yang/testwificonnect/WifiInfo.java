package com.yang.testwificonnect;


/**
 * author: ybjaychou@gmail.com
 * data: 2015-10-14
 */
public class WifiInfo {
    private static final String TAG = "WifiInfo";

    private boolean enableConnect;

    private String bssid;

    private String ssid;

    private String passWord;


    public WifiInfo(String ssid, String bssid, String passWord) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.passWord = passWord;
    }


    public WifiInfo() {
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public boolean isEnableConnect() {
        return enableConnect;
    }

    public void setEnableConnect(boolean enableConnect) {
        this.enableConnect = enableConnect;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Override
    public String toString() {
        return "WifiInfo{" +
                "bssid='" + bssid + '\'' +
                ", enableConnect=" + enableConnect +
                ", ssid='" + ssid + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
