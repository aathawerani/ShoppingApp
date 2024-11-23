package com.example.daraz4

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.daraz4.databinding.FragmentLoginBinding
import com.example.daraz4.databinding.FragmentProductOptionsBinding

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedLoginViewModel: LoginViewModel
    //private val TAG_LIST_FRAGMENT = "LoginFragment"
    private lateinit var sharedProductViewModel: ProductViewModel
    private lateinit var application : Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModelFactory = LoginViewModelFactory()
        sharedLoginViewModel = ViewModelProvider(requireActivity(), loginViewModelFactory).get(LoginViewModel::class.java)
        application = requireNotNull(this.activity).application
        val productDao = ProductDatabase.getInstance(application).productDao
        val productViewModelFactory = ProductViewModelFactory(productDao)
        sharedProductViewModel = ViewModelProvider(requireActivity(), productViewModelFactory).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = sharedLoginViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        sharedLoginViewModel.Login.observe(viewLifecycleOwner) {
            sharedLoginViewModel.navigateToProductOptions(view)
            //val fm: FragmentManager = getParentFragmentManager()
            //fm.popBackStack()
        }
    }
}