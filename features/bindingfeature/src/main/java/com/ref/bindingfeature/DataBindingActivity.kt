package com.ref.bindingfeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ref.baselibrary.navigator.replaceFragmentWithNoHistory
import com.ref.baselibrary.views.BaseActivity
import com.ref.bindingfeature.views.BindingListFragment

class DataBindingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binding)

    }

    override fun onResume() {
        super.onResume()
        setSelectedModule("bindingfeature")
        launchFragment()
    }
    override fun getAppTitle(): String {
        return getString(com.ref.baseuilibrary.R.string.title_binding)
    }
    private fun launchFragment(){
        replaceFragmentWithNoHistory(BindingListFragment(),com.ref.baseuilibrary.R.id.container_fragment)
    }
}