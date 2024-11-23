import 'package:flutter/material.dart';
import 'Product.dart';
import 'ProductBloc.dart';
import 'dart:developer' as developer;

class ProductWidget extends StatelessWidget {
  final Product _product;
  late ProductBloc productBloc;
  ProductWidget(this._product, ProductBloc _productBloc) {
    productBloc = _productBloc;
  }
  @override
  Widget build(BuildContext context) {
    developer.log("got here 700");
    developer.log("got here 701");
    return ListTile(
        title: Text(_product.productName),
        onTap: () => productBloc.navigateToProductDetail(context, _product));
    //productBloc.selectAction.add(_product));
  }
}
