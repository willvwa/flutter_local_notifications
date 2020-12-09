package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Keep;

import com.dexterous.flutterlocalnotifications.models.MakeBackgroundHttpCallActionType;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;

@Keep
public class CustomActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "ENTROU NO RECEIVER");

        try {

            Log.d("INTENT", "RECEBENDO HTTP CALL ACTION DO INTENT");

            MakeBackgroundHttpCallActionType httpCallActionType = intent.getParcelableExtra(MakeBackgroundHttpCallActionType.HTTP_CALL_ACTION);

            Log.d("HTTP_CALL_ACTION", httpCallActionType.getUrl());
            Log.d("HTTP_CALL_ACTION", httpCallActionType.getCallMethod().getStrValue());
            Log.d("HTTP_CALL_ACTION", httpCallActionType.getHeaders().toString());
            Log.d("HTTP_CALL_ACTION", httpCallActionType.getBody().toString());

            Log.d("INTENT", "HTTP CALL ACTION RECEBIDA DO INTENT");

        } catch (Exception e) {

        }
        Log.d("RECEIVER", "SAIU DO RECEIVER");
    }
}
