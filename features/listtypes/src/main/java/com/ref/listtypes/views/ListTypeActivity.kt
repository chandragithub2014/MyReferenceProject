package com.ref.listtypes.views

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.ref.baselibrary.views.BaseActivity
import com.ref.listtypes.R


class ListTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_type)
    }

    override fun onResume() {
        super.onResume()
     //   setSelectedModule("listtypes")
    //    launchFragment()

    }

    /*override fun getAppTitle(): String {
        return getString(com.ref.baseuilibrary.R.string.title_list_types)
    }
*/
    private fun launchFragment(){
      /*  var navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHostFragment?.let {
            val fragment = navHostFragment.childFragmentManager.fragments[0]
            replaceFragmentWithNoHistory(fragment,com.ref.baseuilibrary.R.id.container_fragment)
        }*/


       var childView :View = findViewById<FrameLayout>(com.ref.baseuilibrary.R.id.container_fragment)
        val parent = childView.parent as ViewGroup
        var index:Int = parent.indexOfChild(childView)
        parent.removeView(childView)

        Navigation.findNavController(this, R.id.nav_host_fragment)
     //   childView = layoutInflater.inflate(R.layout.activity_list_type, parent, false)
       // parent.addView(childView, index);

      //  frameLayout.visibility = View.GONE
    }
}
//https://stackoverflow.com/questions/50689206/how-i-can-retrieve-current-fragment-in-navhostfragment/51962582