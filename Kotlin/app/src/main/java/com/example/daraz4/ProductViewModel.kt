package com.example.daraz4

import android.R.attr.tag
import android.app.Application
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.launch


class ProductViewModel(val dao: ProductDao) : ViewModel() {
    private val _navigateToProduct = MutableLiveData<Product?>()
    val navigateToProduct: LiveData<Product?> get() = _navigateToProduct
    var newProductName = ""
    var products = dao.getAll()
    //val mProductOptionsFragment: ProductOptionsFragment = ProductOptionsFragment()

    fun addProduct() {
        viewModelScope.launch {
            val product = Product()
            product.productName = newProductName
            dao.insert(product)
        }
    }

    fun onProductClicked(product: Product){
        _navigateToProduct.value = product
    }
    fun onProductNavigated(){
        _navigateToProduct.value = null
    }

    fun NavigateOnProductClick(view: View) {
        view.findNavController().navigate(R.id.action_marketPlace_to_productOptions)
        //return mProductOptionsFragment
    }

    fun NavigateToMarketPlace(view: View){
        Log.d("addtocart", "got here 4")
        view.findNavController().navigate(R.id.action_productOptions_to_marketPlace)
        //view.findNavController().popBackStack()
    }

    suspend fun loadProductsAsync(application: Application) {
        val internet = InternetConnection()
        val parseStream = ParseStream()
        val productFeed = "http://192.168.100.149:3000/products"
        //val productFeed = "http://localhost:3000/products"
        var products: java.util.ArrayList<Product?> = java.util.ArrayList(0)

        val `in` = internet.getStreamAsync(productFeed)
        if (`in` != null) {
            products = parseStream.parseJsonAsync(`in`) as java.util.ArrayList<Product?>
            val dbhelper = DBhelper()
            dbhelper.DBInsertAsync(application, products)
        }
        internet.disconnect()
    }
}

class ProductViewModelFactory(private val dao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
