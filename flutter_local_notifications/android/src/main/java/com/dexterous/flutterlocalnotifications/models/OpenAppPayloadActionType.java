package com.dexterous.flutterlocalnotifications.models;

import java.util.Map;

public class OpenAppPayloadActionType {

    public static String PAYLOAD_ACTION = "payload_action";
    public static String PAYLOAD = "payload";

    public String payload;

    public static OpenAppPayloadActionType from(Map<String, Object> arguments) {
        OpenAppPayloadActionType openAppPayloadActionType = new OpenAppPayloadActionType();

        openAppPayloadActionType.payload = (String) arguments.get(PAYLOAD);

        return openAppPayloadActionType;
    }
}
