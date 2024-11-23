import 'package:dio/dio.dart';
import 'dart:developer' as developer;

class internet {
  //static final String _BASE_URL = "http://192.168.100.220:3000";
  static final String _BASE_URL = "http://172.16.8.71:3000";
  //static final String _BASE_URL = "http://10.0.2.2:3000";
  //static final String _BASE_URL = "localhost:3000";
  static const _TIMEOUT = Duration(seconds: 1);

  Future<String> callProductAPI() async {
    Response response;
    var dio = Dio();
    var url = '${_BASE_URL}/products';
    response = await dio.get(url);
    if (response.statusCode == 200) {
      return response.data.toString();
    } else {
      developer.log("got here 3");
      return "";
    }
  }
}
