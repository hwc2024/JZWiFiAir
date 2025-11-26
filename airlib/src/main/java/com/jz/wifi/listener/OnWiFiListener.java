package com.jz.wifi.listener;

public interface OnWiFiListener {

    default void onConnect(boolean isConnect) {

    }

    default void onConnectSsid(String ssid) {

    }

    default void onConnectIp(String ip) {

    }

}
