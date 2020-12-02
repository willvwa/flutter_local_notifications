/// Android Notification Action
class NotificationAction {
  /// Android Notification Action
  NotificationAction(
    this.label, {
    this.payload,
  });

  /// Action Label
  final String label;

  /// Action Payload
  final String payload;

  /// Convert action to Map
  Map<String, Object> toMap() => {
        'label': label,
        'payload': payload,
      };
}
