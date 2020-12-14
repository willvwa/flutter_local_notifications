package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Keep;

import com.dexterous.flutterlocalnotifications.models.HttpCall;
import com.dexterous.flutterlocalnotifications.models.HttpCallMethod;
import com.dexterous.flutterlocalnotifications.models.MakeBackgroundHttpCallsActionType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Keep
public class HttpCallsActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("RECEIVER", "ENTROU NO RECEIVER");

        if (intent.getAction().equals(MakeBackgroundHttpCallsActionType.HTTP_CALLS_ACTION)) {

            Log.d("RECEIVER", "ENTROU NO ACTION");

            final MakeBackgroundHttpCallsActionType httpCalls = intent.getParcelableExtra(MakeBackgroundHttpCallsActionType.HTTP_CALLS_ACTION);

            if (httpCalls.getCalls() != null) {

                Log.d("RECEIVER", "ENTROU NA LISTA DE HTTP CALLS");

                doAsyncTask(httpCalls.getCalls());
            }
        }
    }

    private void doAsyncTask(final List<HttpCall> httpCalls) {

        final PendingResult pendingResult = goAsync();
        final AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {

                for (HttpCall httpCall : httpCalls) {

                    try {

                        makeBackgroundHttpCall(httpCall);

                    } catch (Exception e) {

                        Log.d("HTTP_CALL_ERROR", e.getMessage());
                    }
                }
                Log.d("ASYNC_TASK", "EXECUTOU ASYNC TASK");

                pendingResult.finish();

                return "";
            }
        };
        asyncTask.execute();
    }

    private void makeBackgroundHttpCall(final HttpCall httpCall) throws IOException {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();

            Request.Builder builder = new Request.Builder();

            builder.url(httpCall.getUrl());

            if (httpCall.getHeaders() != null && !httpCall.getHeaders().isEmpty()) {

                for (Map.Entry<String, String> entry : httpCall.getHeaders().entrySet()) {

                    builder.header(entry.getKey(), entry.getValue());

                    Log.d("HTTP_CALL", "HEADER: " + entry.getKey() + ": " + entry.getValue());
                }
            }
            if (httpCall.getCallMethod() != null) {

                if (httpCall.getCallMethod() == HttpCallMethod.POST
                        || httpCall.getCallMethod() == HttpCallMethod.PUT) {

                    Type gsonType = new TypeToken<HashMap>() {
                    }.getType();

                    String gsonStringBody = new Gson().toJson(httpCall.getBody(), gsonType);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gsonStringBody);

                    if (httpCall.getCallMethod() == HttpCallMethod.POST) {

                        Log.d("HTTP_CALL", "HTTP METHOD: POST");

                        builder.post(requestBody);

                    } else {

                        builder.put(requestBody);
                    }
                }
            }
            Request request = builder.build();

            Response response = okHttpClient.newCall(request).execute();

            Log.d("HTTP_CALL", response.code() + ", " + response.body().toString());

            Log.d("HTTP_CALL", "BODY: " + response.body().toString());

            response.body().close();

        } catch (Exception e) {

            throw e;
        }
    }
}
