package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CustomActionReceiver extends BroadcastReceiver {

    private static final String PAYLOAD = "payload";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("RECEIVER", "RECEBIDO");
        String payload = intent.getStringExtra(PAYLOAD);
        FlutterLocalNotificationsPlugin.channelInstance.invokeMethod("selectNotification", payload);
    }
}
