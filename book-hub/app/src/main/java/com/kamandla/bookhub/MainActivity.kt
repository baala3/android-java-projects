package com.kamandla.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
   lateinit var coordinator:CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navgation:NavigationView
    lateinit var drawble:DrawerLayout
     var previous_menu_item:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coordinator=findViewById(R.id.coordinator)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame)
        navgation=findViewById(R.id.navigation)
        drawble=findViewById(R.id.drawable)
        setactionbar()
        supportFragmentManager.beginTransaction().replace(R.id.frame,Dashboard()).commit()
        supportActionBar?.title="Dashboard"
        navgation.setCheckedItem(R.id.dashboard)
        var actionBarDrawerToggle=ActionBarDrawerToggle(this,drawble,R.string.open_drawer,R.string.close_drawer)
        drawble.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navgation.setNavigationItemSelectedListener {
            if(previous_menu_item!=null)
                previous_menu_item?.isChecked=false
            it.isCheckable=true
            it.isChecked=true
            previous_menu_item=it
            when(it.itemId)
            {

                R.id.dashboard->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,Dashboard()).commit()
                    drawble.closeDrawers()
                    supportActionBar?.title="Dashboard"

                }
                R.id.favorites->{
                supportFragmentManager.beginTransaction().replace(R.id.frame,favorites()).commit()
                    drawble.closeDrawers()
                    supportActionBar?.title="Favorites"
                }
                R.id.appinfo->{
               supportFragmentManager.beginTransaction().replace(R.id.frame,AppInfo()).commit()
                    drawble.closeDrawers()
                    supportActionBar?.title="App Info"
                }
                R.id.profile->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,Profile()).commit()
                    drawble.closeDrawers()
                    supportActionBar?.title="Profile"
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
    fun setactionbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="bala"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
            drawble.openDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        var frag=supportFragmentManager.findFragmentById(R.id.frame)
        when(frag)
        {
            !is Dashboard -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame,Dashboard()).commit()
                        supportActionBar?.title= "Dashboard"
            }

                else ->super.onBackPressed()
        }
    }
}
