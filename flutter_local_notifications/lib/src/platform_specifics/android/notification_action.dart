/// Android Notification Action
class NotificationAction {
  /// Android Notification Action
  NotificationAction(
    this.label, {
    this.payload,
    this.backgroundAction = false,
  });

  /// Action Label
  final String label;

  /// Action Payload
  final String payload;

  /// Run action intent without open app
  final bool backgroundAction;

  /// Convert action to Map
  Map<String, Object> toMap() => {
        'label': label,
        'payload': payload,
        'backgroundAction': backgroundAction,
      };
}
