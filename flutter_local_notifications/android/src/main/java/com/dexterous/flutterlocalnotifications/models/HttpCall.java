package com.dexterous.flutterlocalnotifications.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class HttpCall implements Parcelable {

    public static String URL = "url";
    public static String CALL_METHOD = "method";
    public static String HEADERS = "headers";
    public static String BODY = "body";

    private String url;
    private HttpCallMethod callMethod;
    private Map<String, String> headers;
    private Map<String, Object> body;

    public HttpCall(String url, HttpCallMethod callMethod, Map<String, String> headers, Map<String, Object> body) {
        this.url = url;
        this.callMethod = callMethod;
        this.headers = headers;
        this.body = body;
    }

    protected HttpCall(Parcel in) {
        url = in.readString();
        callMethod = HttpCallMethod.fromString(in.readString());
        headers = new HashMap<String, String>();
        in.readMap(headers, String.class.getClassLoader());
        body = new HashMap<String, Object>();
        in.readMap(body, Object.class.getClassLoader());
    }

    public static final Creator<HttpCall> CREATOR = new Creator<HttpCall>() {
        @Override
        public HttpCall createFromParcel(Parcel in) {
            return new HttpCall(in);
        }

        @Override
        public HttpCall[] newArray(int size) {
            return new HttpCall[size];
        }
    };

    public static HttpCall from(Map<String, Object> arguments) {

        String url = (String) arguments.get(URL);
        HttpCallMethod callMethod = HttpCallMethod.fromString((String) arguments.get(CALL_METHOD));
        Map<String, String> headers = (Map<String, String>) arguments.get(HEADERS);
        Map<String, Object> body = (Map<String, Object>) arguments.get(BODY);

        return new HttpCall(url, callMethod, headers, body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(callMethod.getStrValue());
        dest.writeMap(headers);
        dest.writeMap(body);
    }

    public String getUrl() {
        return url;
    }

    public HttpCallMethod getCallMethod() {
        return callMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, Object> getBody() {
        return body;
    }
}
