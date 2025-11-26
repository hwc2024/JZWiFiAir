package org.qiyi.video.svg.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import org.qiyi.video.svg.config.BroadcastConstants;
import org.qiyi.video.svg.listener.OnBroadcastListener;
import org.qiyi.video.svg.log.Logger;

public class BroadcastUtil {

    private String TAG = BroadcastUtil.class.getSimpleName();

    private boolean mReceiverTag = false;

    private Context context;

    private OnBroadcastListener onBroadcastListener;

    private volatile static BroadcastUtil instance;

    public static BroadcastUtil getInstance() {
        if (instance == null) {
            synchronized (BroadcastUtil.class) {
                if (instance == null) {
                    instance = new BroadcastUtil();
                }
            }
        }
        return instance;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), BroadcastConstants.PROCESS_COMMUNICATION_ACTION)) {
                String stringExtra = intent.getStringExtra(BroadcastConstants.EXTRA_AUTHORITY);
                Logger.d( "Receive process message：" + stringExtra);
                if (onBroadcastListener != null && !TextUtils.isEmpty(stringExtra)) {
                    onBroadcastListener.onExtra(stringExtra);
                }
            }
        }
    };

    /**
     * 注册广播
     */
    public void register(Context context) {
        this.context = context;
        if (!mReceiverTag && context != null) {
            mReceiverTag = true;
            IntentFilter myIntentFilter = new IntentFilter();
            myIntentFilter.addAction(BroadcastConstants.PROCESS_COMMUNICATION_ACTION);
            context.registerReceiver(broadcastReceiver, myIntentFilter);
        }
    }

    public void setOnBroadcastListener(OnBroadcastListener onBroadcastListener) {
        this.onBroadcastListener = onBroadcastListener;
    }

    /**
     * 注销广播
     */
    public void unregister() {
        if (mReceiverTag && context != null) {
            mReceiverTag = false;
            context.unregisterReceiver(broadcastReceiver);
        }
        onBroadcastListener = null;
    }
}
