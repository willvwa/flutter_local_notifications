package com.dexterous.flutterlocalnotifications;

import android.annotation.SuppressLint;
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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Keep
public class HttpCallsActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(MakeBackgroundHttpCallsActionType.HTTP_CALLS_ACTION)) {

            final MakeBackgroundHttpCallsActionType httpCalls = intent.getParcelableExtra(MakeBackgroundHttpCallsActionType.HTTP_CALLS_ACTION);

            if (httpCalls.getCalls() != null) {

                for (HttpCall httpCall : httpCalls.getCalls()) {

                    doAsyncTask(httpCall);
                }
            }

        }
    }

    private void doAsyncTask(final HttpCall httpCall) {

        final PendingResult pendingResult = goAsync();

        @SuppressLint("StaticFieldLeak") final AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {

                makeBackgroundHttpCall(httpCall, pendingResult);

                return "";
            }
        };
        asyncTask.execute();
    }

    private void makeBackgroundHttpCall(final HttpCall httpCall, PendingResult pendingResult) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();

            Request.Builder builder = new Request.Builder();

            builder.url(httpCall.getUrl());

            if (httpCall.getHeaders() != null && !httpCall.getHeaders().isEmpty()) {

                for (Map.Entry<String, String> entry : httpCall.getHeaders().entrySet()) {

                    builder.header(entry.getKey(), entry.getValue());
                }
            }
            if (httpCall.getCallMethod() != null) {

                if (httpCall.getCallMethod() == HttpCallMethod.POST
                        || httpCall.getCallMethod() == HttpCallMethod.PUT) {

                    Type gsonType = new TypeToken<HashMap>(){}.getType();

                    String gsonStringBody = new Gson().toJson(httpCall.getBody(), gsonType);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gsonStringBody);

                    if (httpCall.getCallMethod() == HttpCallMethod.POST) {

                        builder.post(requestBody);

                    } else {

                        builder.put(requestBody);
                    }
                }
            }
            Request request = builder.build();

            okHttpClient.newCall(request).execute();

        } catch (Exception e) {

            Log.d("ERRO", e.getMessage());

        } finally {

            pendingResult.finish();
        }
    }
}
