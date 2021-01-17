package com.ref.hiltfeaturemodule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ref.hiltfeaturemodule.R

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsHiltActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_hilt)
    }
}