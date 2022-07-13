package com.publicwasant.miniplant.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.publicwasant.common_ui.fragments.ProductFragment
import com.publicwasant.miniplant.R
import com.publicwasant.miniplant.receivers.NetworkChangeReceiver

class MainActivity : AppCompatActivity() {
    private var productFragment: ProductFragment? = null

    private var bnMainMenu: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productFragment = ProductFragment()

        onReplaceFragment(productFragment!!)

        registerReceiver(NetworkChangeReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume() {
        super.onResume()

        bnMainMenu = findViewById<BottomNavigationView>(R.id.bn_main_menu)
        bnMainMenu?.setOnClickListener { view ->
            when (view.id) {
                R.id.it_product -> onReplaceFragment(productFragment!!)
            }
        }
    }

    private fun onReplaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}