library flutter_traffic_stats;

import 'package:flutter/services.dart';
import 'package:intl/intl.dart';

class TrafficStats {
  static const MethodChannel _channel = const MethodChannel('traffic_stats');

  static Future<Map<String, dynamic>> getTrafficStats() async {
    try {
      String u = DateFormat('yyyy/MM/dd HH:mm:ss').format(DateTime.now());
      Map argument = {
        'timeNow': '$u',
        'lastTimPostData': '2024/01/08 10:02:23',
      };
      var e = await _channel.invokeMethod('getTrafficStats', argument);
      print('hhhee:$e');
      return Map();
    } on PlatformException catch (e) {
      return {};
    }
  }
}
