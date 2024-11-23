import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'dart:developer' as developer;
import 'Product.dart';

class DBHelper {
  bool _loadedDatabasePath = false;
  bool get loadedDatabasePath => _loadedDatabasePath;
  bool _openedDatabase = false;
  bool get openedDatabase => _openedDatabase;
  static late Database _database;
  String _databasesPath = "";

  Future<bool> _loadDatabasesPath() async {
    _databasesPath = await getDatabasesPath();
    return true;
  }

  Future<Database> openAndInitDatabase() async {
    developer.log("got here 307");
    _database = await openDatabase(
      join(_databasesPath, 'product.db'),
      onCreate: (db, version) {
        developer.log("creating database...");
        db.execute(
            "CREATE TABLE product(productId INTEGER PRIMARY KEY AUTOINCREMENT, productName TEXT, "
            "productDescription TEXT)");
      },
      version: 1,
    );
    developer.log("got here 308");
    return _database;
  }

  loadDatabasesPath() {
    try {
      _loadDatabasesPath().then((b) {
        _loadedDatabasePath = true;
      }).catchError((error) {
        developer.log("got here 20");
      });
    } catch (e) {
      developer.log("got here 21");
    }
  }

  _openAndInitDatabase() {
    try {
      _openAndInitDatabase().then((b) {
        developer.log("got here 222");
        _openedDatabase = true;
        return true;
      }).catchError((error) {
        developer.log("got here 22");
      });
    } catch (e) {
      developer.log("got here 23");
    }
  }

  Future<int> addProductToDB(Product product) async {
    int result = await _database.insert(
      'product',
      product.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    developer.log("got here 220 " + result.toString());
    return result;
  }

  insertProduct(List<Product> products) {
    products.forEach((product) {
      addProductToDB(product);
    });
  }
}
