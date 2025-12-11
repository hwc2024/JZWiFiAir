package com.jz.wifi.listener;

public interface OnWiFiListener {

    default void onConnect(boolean isConnect, String ssid, String psk) {

    }

    default void onConnectSsid(String ssid) {

    }

    default void onConnectIp(String ip) {

    }

    default void onConnectMac(String mac) {

    }

}
