package com.dexterous.flutterlocalnotifications.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.dexterous.flutterlocalnotifications.HttpCallsActionReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeBackgroundHttpCallsActionType implements Parcelable {

    public static String HTTP_CALLS_ACTION = "http_calls_action";
    public static String CALLS = "calls";

    private List<HttpCall> calls;

    public MakeBackgroundHttpCallsActionType(List<HttpCall> calls) {
        this.calls = calls;
    }

    protected MakeBackgroundHttpCallsActionType(Parcel in) {
        calls = new ArrayList<HttpCall>();
        in.readList(calls, HttpCall.class.getClassLoader());
    }

    public static final Creator<MakeBackgroundHttpCallsActionType> CREATOR = new Creator<MakeBackgroundHttpCallsActionType>() {
        @Override
        public MakeBackgroundHttpCallsActionType createFromParcel(Parcel in) {
            return new MakeBackgroundHttpCallsActionType(in);
        }

        @Override
        public MakeBackgroundHttpCallsActionType[] newArray(int size) {
            return new MakeBackgroundHttpCallsActionType[size];
        }
    };

    public static MakeBackgroundHttpCallsActionType from(Map<String, Object> arguments) {
        
        List<HttpCall> httpCallList = new ArrayList<>();

        if (arguments.containsKey(HTTP_CALLS_ACTION)) {

            Map<String, Object> mapHttpCalls = (Map<String, Object>) arguments.get(CALLS);

            if (mapHttpCalls != null) {

                for (Map.Entry<String, Object> entry : mapHttpCalls.entrySet()) {

                    Map<String, Object> httpCallMap = (Map<String, Object>) entry.getValue();

                    httpCallList.add(HttpCall.from(httpCallMap));
                }
            }
        }
        return new MakeBackgroundHttpCallsActionType(httpCallList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(calls);
    }

    public List<HttpCall> getCalls() {
        return calls;
    }
}
