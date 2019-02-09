package com.example.henryjacobs.weatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.henryjacobs.weatherapp.adapter.CityAdapter
import com.example.henryjacobs.weatherapp.data.AppDatabase
import com.example.henryjacobs.weatherapp.data.City
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CityDialog.CityHandler {

    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            showAddCityDialog()
        }

        initRecyclerView()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initRecyclerView() {
        Thread {
            // get all cities from DB, returned as a list
            val cities = AppDatabase.getInstance(this@MainActivity).cityDao().findAllCities()
            // create the adapter
            runOnUiThread {
                cityAdapter = CityAdapter(this@MainActivity, cities)
                recyclerCity.adapter = cityAdapter
            }
        }.start()
    }

    private fun showAddCityDialog() {
        CityDialog().show(supportFragmentManager, getString(R.string.tag_create))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_add_city -> {
                showAddCityDialog()
            }
            R.id.nav_about_dev -> {
                Toast.makeText(this, getString(R.string.toast_about), Toast.LENGTH_LONG).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun cityCreated(item: City) {
        Thread {
            val id = AppDatabase.getInstance(this).cityDao().insertCity(item)
            item.cityId = id
            runOnUiThread {
                cityAdapter.addCity(item)
            }
        }.start()

    }
}
