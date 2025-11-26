package com.miusou.andromeda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jz.air.IWiFiMessenger;
import com.jz.wifi.utils.WiFiMessengerUtil;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
    }

    public void onclick(View view) {
        if (view.getId() == R.id.btnServerRegister) {
            WiFiMessengerUtil.getInstance().init(ServerActivity.this, false);
            WiFiMessengerUtil.getInstance().registerWiFiRemoteService(IWiFiMessenger.class, WiFiMessengerService.getInstance());
        }
    }
}
