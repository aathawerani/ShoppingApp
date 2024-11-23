import 'dart:developer' as developer;
import 'dart:convert';

class Product {
  int _productId = 0;
  String _productName = "";
  String get productName => _productName;
  String _productDescription = "";
  String get productDescription => _productDescription;
  Product(this._productId, this._productName, this._productDescription);
  Product.empty() {
    _productId = 0;
    _productName = "";
    _productDescription = "";
  }
  Map<String, dynamic> toMap() {
    return {
      'productId': _productId,
      'productName': _productName,
      'productDescription': _productDescription
    };
  }

  factory Product.fromJson(Map<String, dynamic> json, int index) {
    if (json == null) {
      throw FormatException("Null JSON.");
    }
    return Product(index, json['productName'], json['productDescription']);
  }
  @override
  operator ==(other) =>
      (other is Product) && (_productName == other._productName);
  @override
  int get hashCode => _productName.hashCode ^ _productDescription.hashCode;
}

class ProductParser {
  List<Product> parseProduct(String response) {
    final jsondecoded = json.decode(response);
    List<dynamic> products1 = jsondecoded['products'];
    List<Product> products2 = [];
    int i = 0;
    products1.forEach((decodedProduct) {
      developer.log("got here 210");
      Product product = Product.fromJson(decodedProduct, i);
      products2.add(product);
      i++;
    });
    return products2;
  }
}
