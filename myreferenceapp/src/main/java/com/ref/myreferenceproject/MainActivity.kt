package com.ref.myreferenceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent =
            Intent("com.ref.mvvmfeature.open").setPackage(this.packageName)
        startActivity(intent)
    }
}