package com.dexterous.flutterlocalnotifications;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Keep;

import com.dexterous.flutterlocalnotifications.models.MakeBackgroundHttpCallActionType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Keep
public class CustomActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "ENTROU NO RECEIVER");

        final Intent asyncIntent = intent;

        final PendingResult pendingResult = goAsync();
        @SuppressLint("StaticFieldLeak") final AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {
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

                    OkHttpClient okHttpClient = new OkHttpClient();
                    
                    Request.Builder builder = new Request.Builder();
                    builder.url(httpCallActionType.getUrl());
                    
                    if (httpCallActionType.getHeaders() != null && !httpCallActionType.getHeaders().isEmpty()) {

                        for (Map.Entry<String, String> entry : httpCallActionType.getHeaders().entrySet()) {
                            
                            builder.header(entry.getKey(), entry.getValue());
                        }
                    }
                    
                    if (httpCallActionType.getCallMethod() != null) {
                        
                        switch (httpCallActionType.getCallMethod()) {

                            case GET: break;
                            case POST: {
                                Gson gson = new Gson();
                                Type gsonType = new TypeToken<HashMap>(){}.getType();
                                String gsonStringBody = gson.toJson(httpCallActionType.getBody(), gsonType);

                                Log.d("HTTP_POST", "String Body: " + gsonStringBody);

                                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gsonStringBody);
                                builder.post(requestBody);
                                break;
                            }
                            case PUT:
                                break;
                            case DELETE:
                                break;
                        }
                    }
                    Request request = builder.build();
                    
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
