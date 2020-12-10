package com.dexterous.flutterlocalnotifications.models;

import androidx.annotation.Keep;

import java.util.Map;

@Keep
public class NotificationAction {

    private static final String LABEL = "label";

    public String label;
    public MakeBackgroundHttpCallsActionType makeBackgroundHttpCallsActionType;
    public OpenAppPayloadActionType openAppPayloadActionType;

    public static NotificationAction from(Map<String, Object> arguments) {
        NotificationAction notificationAction = new NotificationAction();

        notificationAction.label = (String) arguments.get(LABEL);

        String makeHttpCallKey = MakeBackgroundHttpCallsActionType.HTTP_CALLS_ACTION;
        String openAppPayloadKey = OpenAppPayloadActionType.PAYLOAD_ACTION;

        if (arguments.containsKey(makeHttpCallKey)) {

            Map<String, Object> map = (Map<String, Object>) arguments.get(makeHttpCallKey);

            notificationAction.makeBackgroundHttpCallsActionType = MakeBackgroundHttpCallsActionType.from(map);

        } else if (arguments.containsKey(openAppPayloadKey)) {

            Map<String, Object> map = (Map<String, Object>) arguments.get(openAppPayloadKey);

            notificationAction.openAppPayloadActionType = OpenAppPayloadActionType.from(map);
        }
        return notificationAction;
    }
}
