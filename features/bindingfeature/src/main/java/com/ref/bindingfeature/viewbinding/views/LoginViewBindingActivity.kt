package com.ref.bindingfeature.viewbinding.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ref.baselibrary.navigator.replaceFragmentWithNoHistory
import com.ref.bindingfeature.R
import com.ref.bindingfeature.databinding.ActivityLoginViewBindingBinding
import com.ref.bindingfeature.views.BindingListFragment
import kotlinx.android.synthetic.main.activity_login_view_binding.*
import kotlinx.android.synthetic.main.fragment_login_view_binding.*

class LoginViewBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginViewBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginViewBindingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        launchFragment()
    }


    private fun launchFragment(){
        replaceFragmentWithNoHistory(LoginViewBindingFragment(),binding.containerFragment.id)
    }
}