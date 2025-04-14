package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodapp.Fragment.FaqsFragment
import com.example.foodapp.Fragment.FavoriteFragment
import com.example.foodapp.Fragment.HistoryFragment
import com.example.foodapp.Fragment.ProfileFragment
import com.example.foodapp.R
import com.example.foodapp.Fragment.RestaurantFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var mheaderview:View
    lateinit var hperson:TextView
   lateinit var hmobile:TextView
    lateinit var  sss:SharedPreferences
lateinit var toolbar:androidx.appcompat.widget.Toolbar
    var previousitem:MenuItem?=null
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar=findViewById(R.id.hhtoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="All Restaurants"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout=findViewById(R.id.hhdrawer)
        coordinatorLayout=findViewById(R.id.hhcoordinator)
        frameLayout=findViewById(R.id.frame)
        navigationView=findViewById(R.id.hhnavigationview)
      val actionBarDrawerToggle=ActionBarDrawerToggle(this@HomeActivity,drawerLayout,
          R.string.open_drawer,
          R.string.close_drawer
      )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportFragmentManager.beginTransaction().replace(
            R.id.frame,
            RestaurantFragment()
        ).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title="All Restaurants"
        navigationView.setCheckedItem(R.id.restaurants)
        mheaderview=navigationView.getHeaderView(0)
          hperson=mheaderview.findViewById(R.id.hpersonname)
         hmobile=mheaderview.findViewById(R.id.hmobile)
           sss=getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)

          hperson.setText(sss.getString("profname","---")).toString()
         hmobile.setText("+91-"+sss.getString("profmobile","---")).toString()

        navigationView.setNavigationItemSelectedListener {
            if(previousitem!=null)
            {
                previousitem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousitem=it
            when(it.itemId)
            {
                R.id.restaurants ->
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        RestaurantFragment()
                    ).commit()
               drawerLayout.closeDrawers()
                    supportActionBar?.title="All Restaurants"
                }
                R.id.profile ->
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        ProfileFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="My Profile"
                }
                R.id.favorite ->
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FavoriteFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favorite Restaurants"

                }
                R.id.history ->
                {

                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        HistoryFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="My Previous Orders"
                }
                R.id.faqs ->
                {

                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FaqsFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Frequently Asked Questions"
                }
                R.id.logout ->
                {
                    val dialog= AlertDialog.Builder(this@HomeActivity)
                    dialog.setTitle("Confirm")
                    dialog.setMessage("Are you sure??")
                    dialog.setPositiveButton("Ok"){
                            text,listener ->
                        val ss=getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)
                        ss.edit().clear().apply()
                        val intent = Intent(this@HomeActivity, LoginActivity::class.java);
                        startActivity(intent)
                        finish()
                    }
                    dialog.setNegativeButton("Cancel"){
                            text,listener ->

                    }

                    dialog.create()
                    dialog.show()

                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)
        when(frag)
        {
            !is RestaurantFragment ->
            {
                supportFragmentManager.beginTransaction().replace(
                    R.id.frame,
                    RestaurantFragment()
                ).commit()
                drawerLayout.closeDrawers()
                supportActionBar?.title="All Restaurants"
            }

        else ->
        super.onBackPressed()}
    }









}
