package com.stefanini.chucknorrisio

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.stefanini.chucknorrisio.datasource.JokeRemoteDataSource
import com.stefanini.chucknorrisio.model.Joke
import com.stefanini.chucknorrisio.presentation.JokePresenter

class JokeActivity : AppCompatActivity() {

    val CATEGORY_KEY: String  = "category_key"

    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (intent.extras != null){
            val category: String = intent.extras.getString(CATEGORY_KEY)


            if (supportActionBar != null){
                supportActionBar!!.title = category
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)

                val dataSource: JokeRemoteDataSource = JokeRemoteDataSource()
                val presenter: JokePresenter = JokePresenter(this, dataSource)
                presenter.findJokeBy(category)

                findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
                    presenter.findJokeBy(category)
                }
            }
        }
    }

        fun showJoke(joke: Joke){
            val txtJoke = findViewById<TextView>(R.id.txt_joke)
            txtJoke.text = joke.value

           val iv = findViewById<ImageView>(R.id.img_icon)
            Picasso.get().load(joke.iconUrl).into(iv)
        }

        fun showFailure(message: String){
            Toast.makeText(this, message, Toast.LENGTH_LONG)
        }

        fun showProgessBar() {
            progress = findViewById(R.id.progress_bar)
            progress.visibility = ProgressBar.VISIBLE
        }

        fun stopProgressBar(){
            progress.visibility = ProgressBar.GONE
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean{
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    return true
                }
                else -> return true
            }
        }
}
