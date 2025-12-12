package com.jz.wifi.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.jz.air.IWiFiMessenger;
import com.jz.wifi.listener.OnWiFiListener;

import org.qiyi.video.svg.Andromeda;
import org.qiyi.video.svg.callback.BaseCallback;
import org.qiyi.video.svg.config.DispatcherConstants;
import org.qiyi.video.svg.transfer.RemoteTransfer;
import org.qiyi.video.svg.utils.BaseProcessUtil;

public class WiFiMessengerUtil extends BaseProcessUtil {

    private boolean isDebug = false;

    @SuppressLint("StaticFieldLeak")
    private volatile static WiFiMessengerUtil instance;

    public static WiFiMessengerUtil getInstance() {
        if (instance == null) {
            synchronized (WiFiMessengerUtil.class) {
                if (instance == null) {
                    instance = new WiFiMessengerUtil();
                }
            }
        }
        return instance;
    }

    private WiFiMessengerUtil() {
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    private IBinder checkIsConnect() {
        if (context == null) {
            Log.d(TAG, "WiFiMessengerUtil Not init Context Is Null");
            return null;
        }

        Log.d(TAG, "context: " + context.getPackageName());
        RemoteTransfer.getInstance().setCurrentAuthority(DispatcherConstants.AUTHORITY_JZ_WIFI_AIR);

        IBinder binder = Andromeda.with(context).getRemoteService(IWiFiMessenger.class);
        Log.d(TAG, "iWiFiMessenger : " + binder);

        if (binder == null) {
            Log.d(TAG, "iWiFiMessenger is Null");
        }
        return binder;
    }

    private IWiFiMessenger getWiFiService() {
        IBinder binder = checkIsConnect();
        return binder != null ? IWiFiMessenger.Stub.asInterface(binder) : null;
    }

    private void notifyConnectFailure(OnWiFiListener listener, String ssid, String psk) {
        if (listener != null) {
            listener.onConnectComplete(false, ssid, psk);
        }
    }

    private void notifyEmptyResult(OnWiFiListener listener, String type) {
        if (listener == null) return;

        switch (type) {
            case "ssid":
                listener.onCurrentSsid("");
                break;
            case "ip":
                listener.onCurrentIp("");
                break;
            case "mac":
                listener.onCurrentMac("");
                break;
        }
    }

    public String getExternalWiFiName() {
        IWiFiMessenger service = getWiFiService();
        if (service == null) return null;

        try {
            return service.getExternalWiFiName();
        } catch (RemoteException e) {
            log("getExternalWiFiName failed");
            return null;
        }
    }

    public boolean isExternalWiFi() {
        IWiFiMessenger service = getWiFiService();
        if (service == null) return false;

        try {
            return service.isExternalWiFi();
        } catch (RemoteException e) {
            log("isExternalWiFi failed");
            return false;
        }
    }

    public boolean connect(String ssid, String psk, OnWiFiListener listener) {
        IWiFiMessenger service = getWiFiService();
        if (service == null) {
            notifyConnectFailure(listener, ssid, psk);
            return false;
        }

        try {
            if (listener != null) {
                listener.onConnecting();
            }
            BaseCallback callback = new BaseCallback() {
                @Override
                public void onSucceed(Bundle bundle) {
                    if (listener == null) return;

                    boolean isSuccess = bundle != null && bundle.getBoolean("isConnect", false);
                    listener.onConnectComplete(isSuccess, ssid, psk);
                }

                @Override
                public void onFailed(String reason) {
                    log("connect failed: " + reason);
                    notifyConnectFailure(listener, ssid, psk);
                }
            };

            int result = service.connect(ssid, psk, callback);

            // 如果立即返回失败，也需要通知回调
            if (result != 1) {
                notifyConnectFailure(listener, ssid, psk);
            }

            return result == 1;
        } catch (RemoteException e) {
            log("connect RemoteException");
            notifyConnectFailure(listener, ssid, psk);
            return false;
        }
    }

    public boolean disConnect() {
        IWiFiMessenger service = getWiFiService();
        if (service == null) return false;

        try {
            int result = service.disConnectAll();
            return result == 1;
        } catch (RemoteException e) {
            log("disConnect failed");
            return false;
        }
    }

    public boolean getConnectSsid(OnWiFiListener listener) {
        return getConnectInfo("ssid", listener);
    }

    public boolean getConnectIp(OnWiFiListener listener) {
        return getConnectInfo("ip", listener);
    }

    public boolean getConnectMac(OnWiFiListener listener) {
        return getConnectInfo("mac", listener);
    }

    private boolean getConnectInfo(String type, OnWiFiListener listener) {
        IWiFiMessenger service = getWiFiService();
        if (service == null) {
            notifyEmptyResult(listener, type);
            return false;
        }

        try {
            BaseCallback callback = createInfoCallback(type, listener);
            int result = callServiceMethod(service, type, callback);

            if (result != 1) {
                notifyEmptyResult(listener, type);
            }

            return result == 1;
        } catch (RemoteException e) {
            log("getConnect" + type + " failed");
            notifyEmptyResult(listener, type);
            return false;
        }
    }

    private BaseCallback createInfoCallback(final String type, final OnWiFiListener listener) {
        return new BaseCallback() {
            @Override
            public void onSucceed(Bundle bundle) {
                if (listener == null || bundle == null) {
                    notifyEmptyResult(listener, type);
                    return;
                }

                String value = bundle.getString(type, "");
                notifyResult(type, listener, value);
            }

            @Override
            public void onFailed(String reason) {
                log("get" + type + " failed: " + reason);
                notifyEmptyResult(listener, type);
            }
        };
    }

    private void notifyResult(String type, OnWiFiListener listener, String value) {
        if (listener == null) return;

        switch (type) {
            case "ssid":
                listener.onCurrentSsid(value);
                break;
            case "ip":
                listener.onCurrentIp(value);
                break;
            case "mac":
                listener.onCurrentMac(value);
                break;
        }
    }

    private int callServiceMethod(IWiFiMessenger service, String type, BaseCallback callback)
            throws RemoteException {
        switch (type) {
            case "ssid":
                return service.getConnectSsid(callback);
            case "ip":
                return service.getConnectIp(callback);
            case "mac":
                return service.getConnectMac(callback);
            default:
                return 0;
        }
    }

    public boolean isTask() {
        IWiFiMessenger service = getWiFiService();
        if (service == null) return false;

        try {
            return service.isTask();
        } catch (RemoteException e) {
            log("isTask failed");
            return false;
        }
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    private void log(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }
}