package com.dexterous.flutterlocalnotifications.models;

import androidx.annotation.Keep;

import java.util.Map;

@Keep
public class NotificationAction {

    private static final String LABEL = "label";
    private static final String PAYLOAD = "payload";
    private static final String BACKGROUND_ACTION = "backgroundAction";

    public String label;

    public String payload;

    public Boolean backgroundAction;

    public static NotificationAction from(Map<String, Object> arguments) {
        NotificationAction notificationAction = new NotificationAction();

        notificationAction.label = (String) arguments.get(LABEL);
        notificationAction.payload = (String) arguments.get(PAYLOAD);
        notificationAction.backgroundAction = (Boolean) arguments.get(BACKGROUND_ACTION);

        return notificationAction;
    }
}
