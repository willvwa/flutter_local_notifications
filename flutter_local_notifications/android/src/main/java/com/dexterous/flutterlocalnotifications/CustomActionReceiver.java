package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;

public class CustomActionReceiver extends BroadcastReceiver {
    private static final String METHOD_CHANNEL = "dexterous.com/flutter/local_notifications";
    private static final String PAYLOAD = "payload";
    private final MethodChannel channel;

    CustomActionReceiver() {
        this.channel = new MethodChannel(FlutterLocalNotificationsPlugin.flutterPluginBinding.getBinaryMessenger(), METHOD_CHANNEL);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "ENVIANDO");
        String payload = intent.getStringExtra(PAYLOAD);
        channel.invokeMethod("selectNotification", payload);
        Log.d("RECEIVER", "ENVIADO");
    }
}
