package com.example.daraz4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    private val TAG_LIST_FRAGMENT = "MainActivity"
    var mMarketPlaceFragment: MarketPlaceFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val fm: FragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            val ft: FragmentTransaction = fm.beginTransaction()
            mMarketPlaceFragment = MarketPlaceFragment()
            ft.add(
                R.id.main_activity_frame,
                mMarketPlaceFragment!!, TAG_LIST_FRAGMENT
            )
            ft.commit()
        } else {
            mMarketPlaceFragment = fm.findFragmentByTag(TAG_LIST_FRAGMENT) as MarketPlaceFragment?
        }*/
    }
}