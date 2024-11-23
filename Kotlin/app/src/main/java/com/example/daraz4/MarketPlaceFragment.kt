package com.example.daraz4

import android.R.attr
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.daraz4.databinding.FragmentMarketPlaceBinding
import kotlinx.coroutines.launch


class MarketPlaceFragment : Fragment() {
    private var _binding: FragmentMarketPlaceBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedProductViewModel: ProductViewModel
    private lateinit var application : Application

    override fun onCreate(savedInstanceState: Bundle?) {
        //Log.d("trace", "got here 1")
        super.onCreate(savedInstanceState)
        application = requireNotNull(this.activity).application
        val productDao = ProductDatabase.getInstance(application).productDao
        val productViewModelFactory = ProductViewModelFactory(productDao)
        sharedProductViewModel = ViewModelProvider(requireActivity(), productViewModelFactory).get(ProductViewModel::class.java)
        //Log.d("trace", "got here 2")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Log.d("trace", "got here 3")
        _binding = FragmentMarketPlaceBinding.inflate(inflater, container, false)
        val view = binding.root
        //Log.d("trace", "got here 4")
        sharedProductViewModel.onProductNavigated()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("trace", "got here 5")
        super.onViewCreated(view, savedInstanceState)
        //val TAG_FRAGMENT = "MarketPlaceFragment"
        binding.viewModel = sharedProductViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = ProductItemAdapter{product->
            Log.d("trace", "got here 6")
            sharedProductViewModel.onProductClicked(product)
        }
        binding.productsList.adapter = adapter
        //Log.d("trace", "got here 7")
        sharedProductViewModel.products.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("trace", "got here 8")
                adapter.submitList(it)
            }
        }
        //Log.d("trace", "got here 9")
        sharedProductViewModel.navigateToProduct.observe(viewLifecycleOwner) { product ->
            product?.let {
                Log.d("trace", "got here 10")
                //view.findNavController().navigate(R.id.action_marketPlace_to_productOptions)
                /*val fm: FragmentManager = getParentFragmentManager()
                val ft: FragmentTransaction = fm.beginTransaction()
                val nextFragment = sharedProductViewModel.NavigateOnProductClick()
                ft.replace(
                    com.example.daraz4.R.id.main_activity_frame,
                    nextFragment, TAG_FRAGMENT
                )
                ft.addToBackStack(null)
                ft.commit()*/
                sharedProductViewModel.NavigateOnProductClick(view);
            }
            Log.d("trace", "got here 11")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("trace", "got here 12")
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("trace", "got here 13")
                sharedProductViewModel.loadProductsAsync(application)
                Log.d("trace", "got here 14")
            }
            //Log.d("trace", "got here 15")
        }
        //Log.d("trace", "got here 16")
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}