package com.miusou.andromeda;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.jz.air.IWiFiMessenger;

public class WiFiMessengerService extends IWiFiMessenger.Stub {

    private static final String TAG = WiFiMessengerService.class.getSimpleName();

    private static WiFiMessengerService instance;

    public static WiFiMessengerService getInstance() {
        if (null == instance) {
            synchronized (WiFiMessengerService.class) {
                if (null == instance) {
                    instance = new WiFiMessengerService();
                }
            }
        }
        return instance;
    }

    private WiFiMessengerService() {

    }


    @Override
    public boolean isExternalWiFi() throws RemoteException {
        return false;
    }

    @Override
    public int connect(String ssid, String psk, org.qiyi.video.svg.IPCCallback callback) throws RemoteException {
        return 0;
    }

    @Override
    public int disConnectAll() throws RemoteException {
        return 0;
    }

    @Override
    public int getConnectIp(org.qiyi.video.svg.IPCCallback callback) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("ip", "100");
        callback.onSuccess(bundle);
        return 0;
    }

    @Override
    public int getConnectSsid(org.qiyi.video.svg.IPCCallback callback) throws RemoteException {
        Log.d(TAG, "getConnectSsid");
        Bundle bundle = new Bundle();
        bundle.putString("ssid", "200");
        callback.onSuccess(bundle);
        return 0;
    }
}
