package com.example.daraz4

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "product_table")
data class Product (@PrimaryKey (autoGenerate = true) var productId: Long = 0L,
                 @ColumnInfo (name = "product_name") var productName: String = "",
                @ColumnInfo (name = "product_description") var productDescription: String = "",
                 @ColumnInfo (name = "product_bought") var productBought: Boolean = false){
}

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product:Product)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(earthquakes: List<Product?>?)
    @Update
    suspend fun update(product:Product)
    @Query("SELECT COUNT(productId) FROM product_table")
    fun getDataCount(): Int
    @Delete
    suspend fun delete (product: Product)
    @Query("Select * from product_table ORDER BY productId desc")
    fun getAll() : LiveData<List<Product>>
    @Query("Select * from product_table where productId = :key")
    fun get(key:Long) : LiveData<Product>
}

@Database(entities = [Product::class], version=1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase(){
    abstract val productDao : ProductDao
    companion object{
        @Volatile
        private var INSTANCE: ProductDatabase? = null
        fun getInstance(context: Context): ProductDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}