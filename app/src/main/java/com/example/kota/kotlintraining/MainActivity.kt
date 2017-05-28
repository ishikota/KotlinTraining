package com.example.kota.kotlintraining

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initFragment()
        }
    }

    fun initFragment() {
        val tag = MainFragment::class.java.simpleName
        supportFragmentManager.beginTransaction().add(R.id.container, MainFragment(), tag).commit()
    }
}
