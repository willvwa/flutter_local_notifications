package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;

public class CustomActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "ENTROU NO RECEIVER");
        String payload = intent.getStringExtra(FlutterLocalNotificationsPlugin.PAYLOAD);
        FlutterLocalNotificationsPlugin.getInstance().sendPayloadMessageToFlutter(payload);
        Log.d("RECEIVER", "SAIU DO RECEIVER");
    }
}
