import 'package:daraz/LoginBloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:developer' as developer;
import 'DBhelper.dart';
import 'package:sqflite/sqflite.dart';
import 'ProductBloc.dart';
import 'MarketPlaceWidget.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  //developer.log("got here 301");
  DBHelper db = DBHelper();
  if (!db.loadedDatabasePath) {
    //developer.log("got here 302");
    db.loadDatabasesPath();
    //developer.log("got here 303");
  }

  if (!db.openedDatabase) {
    //developer.log("got here 304");
    //_openAndInitDatabase();
    try {
      db.openAndInitDatabase().then((database) {
        developer.log("got here 222 ");
        //_openedDatabase = true;
        //return true;
        runApp(DarazApp(database));
      }).catchError((error) {
        developer.log("got here 22");
      });
    } catch (e) {
      developer.log("got here 23 " + e.toString());
    }
    developer.log("got here 305");
  }
  //runApp(DarazApp());
}

class DarazApp extends StatelessWidget {
  DarazApp(Database database) {
    _database = database;
  }

  late Database _database;

  @override
  Widget build(BuildContext context) {
    final ProductBloc _productBloc = ProductBloc(_database);
    developer.log("got here 400");
    return MaterialApp(
        title: 'Daraz Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: MultiBlocProvider(providers: [
            BlocProvider<ProductBloc>(create: (context) => ProductBloc()), //bloc page to get corrdinate
            BlocProvider<LoginBloc>(create: (context) => LoginBloc()), // bloc page to listen to change user location
          ]/*ProductBlocProvider(
            productBloc: _productBloc,*/
            child: MarketPlaceWidget(
                /*title: 'Daraz', messageStream: _productBloc.messageStream*/)));
  }
}
