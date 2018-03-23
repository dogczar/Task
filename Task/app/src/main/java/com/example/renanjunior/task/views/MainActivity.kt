package com.example.renanjunior.task.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.renanjunior.task.R
import com.example.renanjunior.task.business.UserBusiness
import com.example.renanjunior.task.views.fragment.TaskListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var mUserBusiness : UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mUserBusiness = UserBusiness(this)

        startDefaultFragment()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun startDefaultFragment() {
        val fragment : Fragment = TaskListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        var fragment : Fragment? = null

        when (item.itemId) {
            R.id.nav_done -> {
                fragment = TaskListFragment.newInstance()
            }
            R.id.nav_todo -> {
                fragment = TaskListFragment.newInstance()
            }
            R.id.nav_logout -> {
                handleLogout()
            }
        }

        if(fragment!=null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
        }


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }



    private fun handleLogout(){
        if (mUserBusiness.logout()){
            val intent : Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, getString(R.string.erro_logout), Toast.LENGTH_LONG).show()
        }
    }




}
