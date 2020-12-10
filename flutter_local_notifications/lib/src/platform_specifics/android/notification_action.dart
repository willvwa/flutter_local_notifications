import 'package:flutter/foundation.dart';

/// Android Notification Action
class NotificationAction {
  NotificationAction(this.label, this.actionType);

  final String label;

  final NotificationActionType actionType;

  Map<String, Object> toMap() => {
        'label': label,
      }..addAll(_convertActionTypeToMap());

  Map<String, Object> _convertActionTypeToMap() {
    if (actionType is OpenAppPayloadActionType) {
      return <String, Object>{
        'payload_action': (actionType as OpenAppPayloadActionType).toMap(),
      };
    }
    if (actionType is MakeBackgroundHttpCallsActionType) {
      return <String, Object>{
        'http_calls_action': (actionType as MakeBackgroundHttpCallsActionType).toMap(),
      };
    }
    return <String, Object>{};
  }
}

/// Notification action base type
abstract class NotificationActionType {}

/// Open App and send payload back to Flutter
class OpenAppPayloadActionType extends NotificationActionType {
  OpenAppPayloadActionType(this.payload);

  final String payload;

  Map<String, Object> toMap() => {
        'payload': payload,
      };
}

/// List of http calls to do in background
class MakeBackgroundHttpCallsActionType extends NotificationActionType {
  MakeBackgroundHttpCallsActionType(this.calls);

  List<HttpCall> calls;

  Map<String, Object> toMap() => {
        'calls': {for (HttpCall e in calls) calls.indexOf(e).toString(): e.toMap()}
      };
}

class HttpCall {
  HttpCall(this.url, this.method, this.headers, {this.body});

  final String url;
  final HttpCallMethod method;
  final Map<String, String> headers;

  /// Use primitive values
  final Map<String, Object> body;

  Map<String, Object> toMap() => {
        'url': url,
        'method': method.strValue,
        'headers': headers,
        'body': body,
      };
}

enum HttpCallMethod { get, post, put, delete }

extension _HttpCallMethodExtension on HttpCallMethod {
  String get strValue => describeEnum(this);
}
