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
    if (actionType is MakeBackgroundHttpCallActionType) {
      return <String, Object>{
        'http_call_action': (actionType as MakeBackgroundHttpCallActionType).toMap(),
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

/// Do a http call in background
class MakeBackgroundHttpCallActionType extends NotificationActionType {
  MakeBackgroundHttpCallActionType(this.url, this.method, this.headers, {this.body});

  final String url;
  final HttpCallMethod method;

  /// Use primitive values
  final Map<String, Object> headers;

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
