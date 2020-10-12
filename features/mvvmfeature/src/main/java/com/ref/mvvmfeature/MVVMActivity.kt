package com.ref.mvvmfeature


import android.os.Bundle
import com.ref.baselibrary.navigator.replaceFragmentWithNoHistory
import com.ref.baselibrary.views.BaseActivity
import com.ref.mvvmfeature.view.PlaceHolderFragment

class MVVMActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
    }

    override fun onResume() {
        super.onResume()
        setSelectedModule("mvvmfeature")
        launchFragment()
    }

    override fun getAppTitle(): String {
        return getString(com.ref.baseuilibrary.R.string.title_mvvm)
    }


    private fun launchFragment(){
        replaceFragmentWithNoHistory(PlaceHolderFragment(),com.ref.baseuilibrary.R.id.container_fragment)
    }


}