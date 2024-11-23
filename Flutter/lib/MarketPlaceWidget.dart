import 'dart:async';
import 'package:flutter/material.dart';
import 'Product.dart';
import 'ProductBloc.dart';
import 'dart:developer' as developer;
import 'ProductWidget.dart';

class MarketPlaceWidget extends StatelessWidget {
  MarketPlaceWidget({
    Key? key,
    /*required this.title,
      required Stream<String> this.messageStream*/
  }) : super(key: key) {
    /*messageStream.listen((message) {
      if (_scaffoldKey.currentState != null) {
        _scaffoldKey.currentState!.showSnackBar(SnackBar(
          content: Text(message),
          duration: const Duration(seconds: 1),
        ));
      }
    })*/
    ;
  }
  //final GlobalKey<ScaffoldMessengerState> _scaffoldKey =
  //GlobalKey<ScaffoldMessengerState>();
  //final String title;
  //final Stream<String> messageStream;

  @override
  Widget build(BuildContext context) {
    developer.log("got here 300");
    final productBloc = ProductBlocProvider.of(context);
    developer.log("got here 301");

    return Scaffold(
        //key: _scaffoldKey,
        appBar: AppBar(
          title: Text("Market Place"), //Text(title),
        ),
        body: StreamBuilder<List<Product>>(
            stream: productBloc.productListStream,
            initialData: productBloc.initProductList(),
            builder: (context, snapshot) {
              List<Widget> productWidgets =
                  snapshot.data!.map((Product product) {
                return ProductWidget(product, productBloc);
              }).toList();
              return ListView(
                  padding: const EdgeInsets.all(10.0),
                  children: productWidgets);
            }));
  }
}
