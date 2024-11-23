import 'package:daraz/MarketPlaceWidget.dart';

import 'internet.dart';
import 'package:rxdart/rxdart.dart';
import 'Product.dart';
import 'dart:developer' as developer;
import 'package:sqflite/sqflite.dart';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'dart:async';
import 'DBhelper.dart';
import 'ProductDetailWidget.dart';
import 'LoginWidget.dart';
import 'MarketPlaceWidget.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ProductBloc implements StateStreamableSource {
  List<Product> _productList = [];
  late Stream<List<Product>?> productsStream;

  late Database _database;

  // Streams for State Updates
  final _productListSubject = BehaviorSubject<List<Product>>();
  Stream<List<Product>> get productListStream => _productListSubject.stream;

  // Sinks for Actions
  final _selectActionStreamController = StreamController<Product>();
  Sink<Product> get selectAction => _selectActionStreamController.sink;

  ProductBloc(Database database) {
    _database = database;
    _selectActionStreamController.stream.listen(_selectProduct);
  }

  Future<List<Product>?> loadAndParseProducts() async {
    developer.log("got here 200");
    final List<Map<String, dynamic>> products =
        await _database.query('product');
    developer.log("got here 201");
    List<Product> list = [];
    if (products.isNotEmpty) {
      developer.log("got here 202");
      list = List.generate(products.length, (i) {
        return Product(products[i]['productId'], products[i]['productName'],
            products[i]['productDescription']);
      });
    } else {
      developer.log("got here 203");
      list = await callProductAPI();
    }
    //developer.log("list " + list[0]._productName);
    _productListSubject.add(list);
    developer.log(list.length.toString() + " added");
    return list;
  }

  Future<List<Product>> callProductAPI() async {
    internet internetHelper = internet();
    String response = await internetHelper.callProductAPI();
    ProductParser productParser = ProductParser();
    DBHelper dbHelper = DBHelper();
    List<Product> products = productParser.parseProduct(response);
    dbHelper.insertProduct(products);
    return products;
  }

  List<Product> initProductList() {
    try {
      developer.log("got here 6");
      loadAndParseProducts().then((products) {
        developer.log("got here 7");
        _productList = products!;
        //developer.log("_productList " + _productList[0]._productName);
      }).catchError((error) {
        developer.log("got here 9 " + error.toString());
      });
    } catch (e) {
      developer.log("got here 10");
    }
    return _productList;
  }

  void _selectProduct(Product product) {
    _productListSubject.add(_productList);
  }

  void navigateToProductDetail(BuildContext context, Product product) {
    Navigator.push(
      context,
      MaterialPageRoute(
          builder: (context) => ProductDetailWidget(product, this)),
    );
  }

  void navigateToLogin(BuildContext context) {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => LoginWidget()));
  }

  void navigateToMarketPlace(BuildContext context) {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => MarketPlaceWidget()));
  }

  bool _isClosed = false;

  @override
  bool get isClosed => _isClosed;

  @override
  close() {
    _selectActionStreamController.close();
    _isClosed = true;
  }

  late State _state;
  @override
  State get state => _state;
  late Stream _stream;
  @override
  Stream get stream => _stream;
}

class ProductBlocProvider extends InheritedWidget {
  final ProductBloc productBloc;
  ProductBlocProvider({
    Key? key,
    required this.productBloc,
    Widget? child,
  }) : super(key: key, child: child!);
  @override
  bool updateShouldNotify(InheritedWidget oldWidget) => true;
  static ProductBloc of(BuildContext context) =>
      (context.dependOnInheritedWidgetOfExactType<ProductBlocProvider>()
              as ProductBlocProvider)
          .productBloc;
}
