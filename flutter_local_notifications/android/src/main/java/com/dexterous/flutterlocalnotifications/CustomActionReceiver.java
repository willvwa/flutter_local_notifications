package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

        try {

            Log.d("INTENT", "RECEBENDO HTTP CALL ACTION DO INTENT");

            MakeBackgroundHttpCallActionType httpCallActionType = intent.getParcelableExtra(MakeBackgroundHttpCallActionType.HTTP_CALL_ACTION);

            Log.d("HTTP_CALL_ACTION", httpCallActionType.getUrl());
            Log.d("HTTP_CALL_ACTION", httpCallActionType.getCallMethod().getStrValue());
            Log.d("HTTP_CALL_ACTION", httpCallActionType.getHeaders().toString());
            Log.d("HTTP_CALL_ACTION", httpCallActionType.getBody().toString());

            String body = "{\n" +
                    "\"to\": \"fBDxv18zT7uN9W-AaQts_d:APA91bHt0chl2IAVvXvqTt--98MArwx7Ai_6O3uB-irMpmLjWUxdIYuHDYFZDL1E2xnwHKlRHNEOuj-Dq1MaFe6uFsoN7_pMgyz0WvJTH7VWs9V6ves6m2bVS_6VTqZNwgUOTDVVxFqU\",\n" +
                    "\"data\": {\n" +
                    "\"sound\": \"default\",\n" +
                    "\"title\": \"test title\",\n" +
                    "\"message\": \"test body\",\n" +
                    "\"content_available\": true,\n" +
                    "\"priority\": \"high\",\n" +
                    "\"click_action\": \"FLUTTER_NOTIFICATION_CLICK\",\n" +
                    "\"values\": {\n" +
                    "\"type\": \"video-call\",\n" +
                    "\"id\": 192610,\n" +
                    "\"name\": \"teste\",\n" +
                    "\"photo\": \"\"\n" +
                    "}\n" +
                    "}\n" +
                    "}";

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .header("Authorization", "key=AAAAiXUhzjc:APA91bFSpAvRCr010_all2JWP9RpuMplaB1W8mRpOiw5PTXWA9azIpKVehxAluylmM-CrfcJT3EcXTMCzJ35PnFbA-G7CP1jqFuXlazYmugQoUn3iimJvi8IOQ1oZYK5I0SBt59ldhxy")
                    .header("Content-Type", "application/json")
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(requestBody)
                    .build();

            Response response = okHttpClient.newCall(request).execute();

            Log.d("HTTP_POST", "Code: " + response.code());

            String result = response.body().string();

            if (result != null) Log.d("HTTP_POST", result);

            Log.d("INTENT", "HTTP CALL ACTION RECEBIDA DO INTENT");

        } catch (Exception e) {

            Log.d("ERRO", e.getMessage());
        }
        Log.d("RECEIVER", "SAIU DO RECEIVER");
    }
}
