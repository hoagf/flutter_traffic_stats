library flutter_traffic_stats;
import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:intl/intl.dart';

class TrafficStats {
  static const MethodChannel _channel = const MethodChannel('traffic_stats');

  static Future<List<dynamic>> getTrafficStats(DateTime startDate, DateTime endDate) async {
    try {
      Map argument = {
        'lastTimPostData': DateFormat('yyyy/MM/dd HH:mm:ss').format(startDate),
        'timeNow': DateFormat('yyyy/MM/dd HH:mm:ss').format(endDate),
      };
      String events = await _channel.invokeMethod('getTrafficStats', argument);
      return jsonDecode(events);
    } on PlatformException catch (e) {
      return [];
    }
  }

  static Future<List<dynamic>> queryNetworkBuckets(
      DateTime startDate,
      DateTime endDate, {
        NetworkType networkType = NetworkType.all,
      }) async {
    int end = endDate.millisecondsSinceEpoch;
    int start = startDate.millisecondsSinceEpoch;
    Map<String, int> interval = {
      'start': start,
      'end': end,
      'type': networkType.value,
    };
    String events =
    await _channel.invokeMethod('queryNetworkBuckets', interval);
    print(events);
    return jsonDecode(events);
  }
}

enum NetworkType {
  all,
  wifi,
  mobile,
}

extension NetworkTypeExt on NetworkType {
  int get value {
    switch (this) {
      case NetworkType.all:
        return 1;
      case NetworkType.wifi:
        return 2;
      case NetworkType.mobile:
        return 3;
    }
  }
}
