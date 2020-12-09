package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Keep;

import com.dexterous.flutterlocalnotifications.models.MakeBackgroundHttpCallActionType;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Keep
public class CustomActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "ENTROU NO RECEIVER");

        final Intent asyncIntent = intent;

        final PendingResult pendingResult = goAsync();
        final AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {

                String result = "";

                try {

                    Log.d("INTENT", "RECEBENDO HTTP CALL ACTION DO INTENT");

                    MakeBackgroundHttpCallActionType httpCallActionType = asyncIntent.getParcelableExtra(MakeBackgroundHttpCallActionType.HTTP_CALL_ACTION);

                    Log.d("HTTP_CALL_ACTION", httpCallActionType.getUrl());
                    Log.d("HTTP_CALL_ACTION", httpCallActionType.getCallMethod().getStrValue());
                    Log.d("HTTP_CALL_ACTION", httpCallActionType.getHeaders().toString());
                    Log.d("HTTP_CALL_ACTION", httpCallActionType.getBody().toString());

                    String body = "{\"to\":\"cMgO1ZsDSY-d68W3jzVTWM:APA91bFXd-bZ__gZ0sTNj7SddJFwVP6VdbxulhQ2afEK_FJt6PVEolHTkm1G993Otkuer9eo0lznMhcCZenmXyLmNa1XgxS-LqJCZXfuYwUujCZm6Xx0cx7Qb8j3ux-apGN9L-U17yCN\",\"data\":{\"sound\":\"default\",\"title\":\"test title\",\"message\":\"test body\",\"content_available\":true,\"priority\":\"high\",\"click_action\":\"FLUTTER_NOTIFICATION_CLICK\",\"values\":{\"type\":\"video-call\",\"id\":192610,\"name\":\"teste\",\"photo\":\"\"}}}";

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);

                    OkHttpClient okHttpClient = new OkHttpClient();

                    Request request = new Request.Builder()
                            .header("Authorization", "teste")
                            .header("Content-Type", "application/json")
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(requestBody)
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    Log.d("HTTP_POST", "Code: " + response.code());

                    result = response.body().string();

                    if (result != null) Log.d("HTTP_POST", result);

                    Log.d("INTENT", "HTTP CALL ACTION RECEBIDA DO INTENT");

                } catch (Exception e) {

                    if (e.getMessage() != null) Log.d("ERRO", e.getMessage());
                } finally {
                    pendingResult.finish();
                    return result;
                }
            }
        };
        asyncTask.execute();

        Log.d("RECEIVER", "SAIU DO RECEIVER");
    }
}
