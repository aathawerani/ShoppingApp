package com.example.daraz4

import android.content.ContentValues
import android.location.Location
import android.util.JsonReader
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class ParseStream {
    @Throws(IOException::class)
    suspend fun parseJsonAsync(`in`: InputStream): List<Product?>? {
        var products: List<Product?>? = null
        val reader = JsonReader(withContext(Dispatchers.IO) {
            InputStreamReader(`in`, "UTF-8")
        })
        withContext(Dispatchers.IO) {
            try {
                reader.beginObject()
                while (reader.hasNext()) {
                    val name = reader.nextName()
                    if (name == "products") {
                        products = readProductArray(reader)
                    } else {
                        reader.skipValue()
                    }
                }
                reader.endObject()
            } finally {
                reader.close()
            }
        }
        return products
    }

    @Throws(IOException::class)
    fun readProductArray(reader: JsonReader): List<Product?> {
        val products: MutableList<Product?> = ArrayList()
        reader.beginArray()
        while (reader.hasNext()) {
            products.add(readProduct(reader))
        }
        reader.endArray()
        return products
    }

    @Throws(IOException::class)
    fun readProduct(reader: JsonReader): Product {
        var productName: String = ""
        var productDescription: String = ""
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "productName") {
                productName = reader.nextString()
            } else if (name == "productDescription") {
                productDescription = reader.nextString()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
        return Product(0, productName, productDescription)
    }
}