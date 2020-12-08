package com.dexterous.flutterlocalnotifications.models;

import com.dexterous.flutterlocalnotifications.NotificationStyle;

import java.util.Map;

public class MakeBackgroundHttpCallActionType {

    public static String HTTP_CALL_ACTION = "http_call_action";
    public static String URL = "url";
    public static String CALL_METHOD = "method";
    public static String HEADERS = "headers";
    public static String BODY = "body";

    public String url;
    public HttpCallMethod callMethod;
    public Map<String, Object> headers;
    public Map<String, Object> body;

    public static MakeBackgroundHttpCallActionType from(Map<String, Object> arguments) {
        MakeBackgroundHttpCallActionType makeBackgroundHttpCallActionType = new MakeBackgroundHttpCallActionType();

        makeBackgroundHttpCallActionType.url = (String) arguments.get(URL);
        makeBackgroundHttpCallActionType.callMethod = HttpCallMethod.values()[(Integer) arguments.get(CALL_METHOD)];
        makeBackgroundHttpCallActionType.headers = (Map<String, Object>) arguments.get(HEADERS);
        makeBackgroundHttpCallActionType.body = (Map<String, Object>) arguments.get(BODY);

        return makeBackgroundHttpCallActionType;
    }
}
