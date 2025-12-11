// IWiFiMessenger.aidl
package com.jz.air;
import org.qiyi.video.svg.IPCCallback;
interface IWiFiMessenger {

    String getExternalWiFiName();

    boolean isExternalWiFi();

    int connect(in String ssid, in String psk, IPCCallback callback);

    int disConnectAll();

    int getConnectSsid(IPCCallback callback);

    int getConnectIp(IPCCallback callback);

    int getConnectMac(IPCCallback callback);

    boolean isIdle();

}
