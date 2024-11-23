import 'package:flutter/material.dart';
import 'Product.dart';
import 'ProductBloc.dart';
import 'dart:developer' as developer;
import 'LoginBloc.dart';

class ProductDetailWidget extends StatelessWidget {
  final Product _product;
  late ProductBloc productBloc;
  late LoginBloc loginBloc;
  ProductDetailWidget(this._product, ProductBloc _productBloc) {
    productBloc = _productBloc;
  }
  @override
  Widget build(BuildContext context) {
    loginBloc = LoginBlocProvider.of(context);
    return Scaffold(
      appBar: AppBar(
        title: Text("Product Details"),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Text(
              _product.productName,
              style: TextStyle(fontSize: 30.0, fontWeight: FontWeight.bold),
            ),
            Text(
              _product.productDescription,
              style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold),
            ),
            TextButton(
                onPressed: () => onPressed(context, productBloc),
                child: Text(
                  "Add To Cart",
                  style: TextStyle(fontSize: 20.0),
                ))
          ],
        ),
      ),
    );
  }

  onPressed(BuildContext context, ProductBloc productBloc) {
    if (loginBloc.login == false) {
      productBloc.navigateToLogin(context);
    } else {}
  }
}
