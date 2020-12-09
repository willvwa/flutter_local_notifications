package com.dexterous.flutterlocalnotifications.models;

public enum HttpCallMethod {

    GET ("get"), POST("post"), PUT("put"), DELETE("delete");

    private final String strValue;

    HttpCallMethod(String value) {
        strValue = value;
    }

    public static HttpCallMethod fromString(String str) {
        for (HttpCallMethod method:
             HttpCallMethod.values()) {
            if (str.equals(method.strValue)) {
                return method;
            }
        }
        return null;
    }
}
