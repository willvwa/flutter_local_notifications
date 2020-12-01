package com.dexterous.flutterlocalnotifications.models;

import androidx.annotation.Keep;

import java.util.Map;

@Keep
public class NotificationAction {

    private static final String LABEL = "label";
    private static final String PAYLOAD = "payload";

    public String label;

    public String payload;

    public static NotificationAction from(Map<String, Object> arguments) {
        NotificationAction notificationAction = new NotificationAction();

        notificationAction.label = (String) arguments.get(LABEL);
        notificationAction.payload = (String) arguments.get(PAYLOAD);

        return notificationAction;
    }
}
