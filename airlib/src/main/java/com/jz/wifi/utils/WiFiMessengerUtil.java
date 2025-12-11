package com.jz.wifi.utils;

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
        if (null == context) {
            Log.d(TAG, "BottomMessengerUtil Not init Context Is Null");
            return null;
        } else {
            Log.d(TAG, "context: " + context.getPackageName());
        }
        RemoteTransfer.getInstance().setCurrentAuthority(DispatcherConstants.AUTHORITY_JZ_WIFI_AIR);
        IBinder iBottomMessenger = Andromeda.with(context).getRemoteService(IWiFiMessenger.class);
        Log.d(TAG, "iBottomMessenger : " + iBottomMessenger);
        if (null == iBottomMessenger) {
            Log.d(TAG, "iBottomMessenger is Null");
        }
        return iBottomMessenger;
    }

    public String getExternalWiFiName() {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return null;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        if (null != buyApple) {
            try {
                return buyApple.getExternalWiFiName();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isExternalWiFi() {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return false;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        if (null != buyApple) {
            try {
                return buyApple.isExternalWiFi();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean connect(String ssid, String psk, OnWiFiListener onWiFiListener) {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return false;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        if (null != buyApple) {
            try {
                buyApple.connect(ssid, psk, new BaseCallback() {

                    @Override
                    public void onSucceed(Bundle build) {
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnect(build.getBoolean("isConnect"), ssid, psk);
                        }
                    }

                    @Override
                    public void onFailed(String reason) {
                        org.qiyi.video.svg.log.Logger.e("buyAppleOnNet failed,reason:" + reason);
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnect(false, ssid, psk);
                        }
                    }
                });
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean disConnect() {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return false;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        if (null != buyApple) {
            try {
                buyApple.disConnectAll();
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean getConnectSsid(OnWiFiListener onWiFiListener) {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return false;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        Log.d(TAG, "buyApple: " + buyApple);
        if (null != buyApple) {
            try {
                buyApple.getConnectSsid(new BaseCallback() {

                    @Override
                    public void onSucceed(Bundle build) {
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnectSsid(build.getString("ssid"));
                        }
                    }

                    @Override
                    public void onFailed(String reason) {
                        org.qiyi.video.svg.log.Logger.e("buyAppleOnNet failed,reason:" + reason);
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnectSsid("");
                        }
                    }
                });
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean getConnectIp(OnWiFiListener onWiFiListener) {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return false;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        if (null != buyApple) {
            try {
                buyApple.getConnectIp(new BaseCallback() {

                    @Override
                    public void onSucceed(Bundle build) {
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnectIp(build.getString("ip"));
                        }
                    }

                    @Override
                    public void onFailed(String reason) {
                        org.qiyi.video.svg.log.Logger.e("buyAppleOnNet failed,reason:" + reason);
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnectIp("");
                        }
                    }
                });
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean getConnectMac(OnWiFiListener onWiFiListener) {
        IBinder iBottomMessenger = checkIsConnect();
        if (iBottomMessenger == null) {
            return false;
        }
        IWiFiMessenger buyApple = IWiFiMessenger.Stub.asInterface(iBottomMessenger);
        if (null != buyApple) {
            try {
                buyApple.getConnectMac(new BaseCallback() {

                    @Override
                    public void onSucceed(Bundle build) {
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnectMac(build.getString("mac"));
                        }
                    }

                    @Override
                    public void onFailed(String reason) {
                        org.qiyi.video.svg.log.Logger.e("buyAppleOnNet failed,reason:" + reason);
                        if (onWiFiListener != null) {
                            onWiFiListener.onConnectMac("");
                        }
                    }
                });
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
