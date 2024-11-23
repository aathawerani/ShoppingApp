package com.example.daraz4

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

class DBhelper {
    suspend fun DBInsertAsync(application: Application, products: ArrayList<Product?>)
    {
        withContext(Dispatchers.IO) {
            ProductDatabase.getInstance(application)
                .productDao.insertProducts(products);
        }
    }
}