package com.ref.baselibrary.views

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.ref.baselibrary.R
import com.ref.baselibrary.navigator.launchActivityWithAction



abstract class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var installedModules: Set<String>
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var bottomNavigationView:BottomNavigationView
    private var actionId:Int = R.id.nav_mvvm;
    lateinit var drawerLayout: DrawerLayout
    //lateinit var actionBar:MaterialToolbar
   // private var drawerLayout : DrawerLayout?= null


    override fun onResume() {
        super.onResume()
        initAppBarAndSetTitle()
        initNavigationMenu()
        initBottomNavigationView()
        initSplitManagerAndEnableFeatures()
    }


   override fun onNavigationItemSelected(item: MenuItem): Boolean {
       Log.d("BaseActivity", "$item clicked ")
       return when (item.itemId) {
           R.id.nav_mvvm -> {
               println("In R.id.nav_mvvm........")

               val intent =
                   Intent("com.ref.mvvmfeature.open").setPackage(this.packageName)
               startActivity(intent)
               true
           }

           R.id.data_binding ->{
               println("Clicked on DataBinding.....")
               launchActivityWithAction<AppCompatActivity>("com.ref.bindingfeature.open")
               true
           }

           R.id.room_db -> {
               println("Clicked on RoomDB.....")
               launchActivityWithAction<AppCompatActivity>("com.ref.roomdbops.open")
               true
           }

           R.id.list_types ->{
               println("Clicked on List Types.....")
               launchActivityWithAction<AppCompatActivity>("com.ref.listtypes.open")
               true
           }
           R.id.file_upload -> {
               println("Clicked on File Upload Types.....")
               launchActivityWithAction<AppCompatActivity>("com.ref.fileuploader.open")
               true
           }
           R.id.hilt_list -> {
               println("Clicked on Hilt List......")
               launchActivityWithAction<AppCompatActivity>("com.ref.hiltfeaturemodule.open")

             /*  try {
                  val intent = Intent(
                       this,
                       Class.forName("com.ref.hiltfeaturemodule.view.PostsHiltActivity")
                   )
                   startActivity(intent)
               } catch (e: ClassNotFoundException) {
                   e.printStackTrace()
               }*/
               true
           }
         /*  R.id.nav_price -> {

               val intent =
                   Intent("colruyt.android.dsa.price.open").setPackage(this.packageName)
               startActivity(intent)
               true
           }
           R.id.nav_rayon -> {
               val intent =
                   Intent("colruyt.android.dsa.rayon.open").setPackage(this.packageName)
               startActivity(intent)
               true
           }
           R.id.nav_home -> {
               println("In R.id.nav_home........")
               val intent =
                   Intent("colruyt.android.dsa.dashboard.open").setPackage(this.packageName)
               startActivity(intent)
               true
           }

           R.id.nav_transport -> {
               val intent =
                   Intent("colruyt.android.dsa.transport.open").setPackage(this.packageName)
               startActivity(intent)
               true
           }*/
           else -> super.onOptionsItemSelected(item)
       }
    }
    abstract fun getAppTitle(): String
    private fun initAppBarAndSetTitle(){
        title = getAppTitle()
        val actionBar = findViewById<MaterialToolbar>(R.id.top_app_bar)
        actionBar?.title = getAppTitle()
    }

    private fun initBottomNavigationView(){
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    private fun initNavigationMenu(){
      drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
      val actionBar = findViewById<MaterialToolbar>(R.id.top_app_bar)
        val toggle =
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                actionBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView =
            findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.data_binding)
    }

    private fun initSplitManagerAndEnableFeatures(){
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        installedModules = splitInstallManager.installedModules

        for (module in installedModules) {
            Log.d("BaseActivity", "$module  is installed")
            println("BaseActivity ActionId $actionId")
            when (module) {
                "mvvmfeature" -> {
                    bottomNavigationView.menu.findItem(R.id.nav_mvvm).isVisible = true

                }
              /*  "price" -> {
                    bottomNavigationView.menu.findItem(R.id.nav_price).isVisible = true

                }
                "rayon" -> {
                    bottomNavigationView.menu.findItem(R.id.nav_rayon).isVisible = true

                }*/

            }
        }
    }

    fun setSelectedModule(selectedModule : String){
        when (selectedModule) {
            "mvvmfeature" -> {
                bottomNavigationView.menu.findItem(R.id.nav_mvvm).isChecked = true

            }
           /* "price" -> {
                bottomNavigationView.menu.findItem(R.id.nav_price).isChecked = true

            }
            "rayon" -> {
                bottomNavigationView.menu.findItem(R.id.nav_rayon).isChecked = true

            }*/
            else -> {
                bottomNavigationView.menu.findItem(R.id.nav_mvvm).isChecked = true
            }

        }

    }


    override fun onBackPressed() {
        val drawer =
            findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}