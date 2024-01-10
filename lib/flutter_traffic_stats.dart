library flutter_traffic_stats;

import 'package:flutter/services.dart';
import 'package:intl/intl.dart';

class TrafficStats {
  static const MethodChannel _channel = const MethodChannel('traffic_stats');

  static Future<String> getTrafficStats(String timeNow, String lastTimPostData) async {
    try {
      Map argument = {
        'timeNow': timeNow,
        'lastTimPostData': lastTimPostData,
      };
      var e = await _channel.invokeMethod('getTrafficStats', argument);
      return e;
    } on PlatformException catch (e) {
      return '';
    }
  }
}
