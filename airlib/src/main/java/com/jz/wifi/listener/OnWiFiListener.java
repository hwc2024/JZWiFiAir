package com.jz.wifi.listener;

public interface OnWiFiListener {

    /**
     * 连接结果回调
     *
     * @param isConnect
     * @param ssid
     * @param psk
     */
    default void onConnectComplete(boolean isConnect, String ssid, String psk) {

    }

    /**
     * 连接中
     */
    default void onConnecting() {

    }

    /**
     * 查询当前连接的SSID
     *
     * @param ssid
     */
    default void onCurrentSsid(String ssid) {

    }

    /**
     * 查询当前连接的IP
     *
     * @param ip
     */
    default void onCurrentIp(String ip) {

    }

    /**
     * 查询当前连接的mac地址
     *
     * @param mac
     */
    default void onCurrentMac(String mac) {

    }

}
