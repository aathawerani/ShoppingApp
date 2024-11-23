package com.example.daraz4

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import java.lang.IllegalArgumentException

class LoginViewModel  : ViewModel()  {
    var Email = ""
    var Password = ""
    private var _Login = MutableLiveData<Login?>()
    val Login: LiveData<Login?> get() = _Login
    val mLoginFragment: LoginFragment = LoginFragment()

    fun Login(){
        _Login.value = Login(true);
    }

    fun IsLogin(): Login? {
        return Login.value
    }

    fun navigateToProductOptions(view: View)
    {
        //view.findNavController().popBackStack()
        view.findNavController().navigate(R.id.action_loginFragment_to_productOptions)
        //view.findNavController().navigate(R.id.action_loginFragment_to_marketPlace)
    }

    fun navigateToLogin(view: View){
        view.findNavController().navigate(R.id.action_productOptions_to_loginFragment)
        //view.findNavController().navigate(R.id.action_productOptions_to_marketPlace)
        //return mLoginFragment
    }
}

class LoginViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
