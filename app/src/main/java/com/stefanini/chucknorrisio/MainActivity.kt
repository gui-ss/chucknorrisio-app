package com.stefanini.chucknorrisio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import com.stefanini.chucknorrisio.datasource.CategoryRemoteDataSource
import com.stefanini.chucknorrisio.model.CategoryItem
import com.stefanini.chucknorrisio.presentation.CategoryPresenter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.ViewHolder


class  MainActivity : AppCompatActivity() {

    lateinit var adapter: GroupAdapter<ViewHolder>

    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { true })

        val rv: RecyclerView = findViewById(R.id.rv_main)

        adapter = GroupAdapter<ViewHolder>()
        adapter.setOnItemClickListener(OnItemClickListener { item, view ->
            val intent: Intent = Intent(this, JokeActivity::class.java)
            val jokeActivity: JokeActivity = JokeActivity()

            val categoryItem = item as CategoryItem
            intent.putExtra(jokeActivity.CATEGORY_KEY, item.categoryName)

            startActivity(intent)

        })

        val layoutManager = LinearLayoutManager(this)


        rv.layoutManager = layoutManager
        rv.adapter = adapter

        val dataSource: CategoryRemoteDataSource = CategoryRemoteDataSource()
        CategoryPresenter(this, dataSource).requestAll()
    }

    fun showProgessBar() {
        progress = findViewById(R.id.progress_bar)
        progress.visibility = ProgressBar.VISIBLE
    }

    fun stopProgressBar(){
            progress.visibility = ProgressBar.GONE
    }

    fun showCategories(categoryItems: MutableList<CategoryItem>){
        adapter.addAll(categoryItems)
        adapter.notifyDataSetChanged()
    }

    fun showFailure(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG)
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

     fun onNavigationItemSelected(item: MenuItem): Boolean {
         val id: Int = item.itemId
         if (id == R.id.nav_home){

         }

         val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
         drawer.closeDrawer(GravityCompat.START)

         return true
    }




}