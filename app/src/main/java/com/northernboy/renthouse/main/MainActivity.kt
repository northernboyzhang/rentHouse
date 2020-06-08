package com.northernboy.renthouse.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityMainBinding
    lateinit var usrViewModel: UsrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater, container, false)
        usrViewModel = ViewModelProvider(this).get(UsrViewModel::class.java)

        setContentView(dataBinding.root)
        val navView: BottomNavigationView = dataBinding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_bbs,
            R.id.navigation_personal
        )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)




        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            getUsrView()?.let {
                it.identityNo?.let {  id ->
                    it.password?.let { it1 -> usrViewModel.login(id, it1) }
                }
            }
            when(destination.id){
                R.id.navigation_reply -> navView.visibility = View.GONE
                R.id.navigation_house_update -> navView.visibility  =View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController(R.id.nav_host_fragment).navigateUp()
        return super.onOptionsItemSelected(item)
    }

}
