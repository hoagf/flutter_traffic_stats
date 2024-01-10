library flutter_traffic_stats;

import 'package:flutter/services.dart';

class TrafficStats {
  static const MethodChannel _channel =
  MethodChannel('flutter_traffic_stats/trafficStats');

  static Future<Map<String, dynamic>> getTrafficStats() async {
    try {
      final Map<String, dynamic> result =
      await _channel.invokeMethod('getTrafficStats');
      return result;
    } on PlatformException catch (e) {
      print("Error: $e");
      return {};
    }
  }
}