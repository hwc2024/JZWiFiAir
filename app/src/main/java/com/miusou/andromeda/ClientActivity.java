package com.miusou.andromeda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jz.wifi.listener.OnWiFiListener;
import com.jz.wifi.utils.WiFiMessengerUtil;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        WiFiMessengerUtil.getInstance().init(this);
    }

    public void onclick(View view) {
        if (view.getId() == R.id.btnSend) {
            String externalWiFiName = WiFiMessengerUtil.getInstance().getExternalWiFiName();
            Log.d("wifiaaa", "externalWiFiName: " + externalWiFiName);

        }
    }
}
