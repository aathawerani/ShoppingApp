package com.example.daraz4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.daraz4.databinding.FragmentProductOptionsBinding

class ProductOptionsFragment : Fragment() {
    private var _binding : FragmentProductOptionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedProductViewModel: ProductViewModel
    private lateinit var sharedLoginViewModel: LoginViewModel
    //private val TAG_LIST_FRAGMENT = "ProductOptionsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        //Log.d("trace", "got here 100")
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val productDao = ProductDatabase.getInstance(application).productDao
        val productViewModelFactory = ProductViewModelFactory(productDao)
        sharedProductViewModel = ViewModelProvider(requireActivity(), productViewModelFactory).get(ProductViewModel::class.java)
        val loginViewModelFactory = LoginViewModelFactory()
        sharedLoginViewModel = ViewModelProvider(requireActivity(), loginViewModelFactory).get(LoginViewModel::class.java)
        //Log.d("trace", "got here 101")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("trace", "got here 102")
        _binding = FragmentProductOptionsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.buy.setOnClickListener {

            Log.d("trace", "got here 103")
            //val fm: FragmentManager = getParentFragmentManager()

            if (sharedLoginViewModel.IsLogin()?.loginStatus == true) {
                //fm.popBackStack()
                sharedProductViewModel.NavigateToMarketPlace(view)
                sharedProductViewModel.onProductNavigated()
            } else {
                sharedLoginViewModel.navigateToLogin(view)
                /*val ft: FragmentTransaction = fm.beginTransaction()
                ft.replace(
                    R.id.main_activity_frame,
                    sharedLoginViewModel.navigateToLogin(), TAG_LIST_FRAGMENT
                )
                ft.addToBackStack(null)
                ft.commit()*/
            }
            //Log.d("trace", "got here 104")
        }
        //Log.d("trace", "got here 105")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("trace", "got here 106")
        super.onViewCreated(view, savedInstanceState)
        sharedProductViewModel.navigateToProduct.observe(viewLifecycleOwner) { product ->
            product?.let {
                Log.d("trace", "got here 107")
                binding.product = product
                //sharedProductViewModel.onProductNavigated()
            }
        }
        //Log.d("trace", "got here 108")
    }

}