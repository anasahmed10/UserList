package com.example.userlist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.presenter.MainPresenter
import com.example.userlist.presenter.RecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var mainPresenter: MainPresenter
    private lateinit var buttonFragment: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         //DEPRECATED: setting toolbar
        /*
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        */
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainPresenter = MainPresenter(application)

        recyclerView = findViewById(R.id.recyclerViewActivity)
        fab = findViewById(R.id.create_fab_activity)

        recyclerView.adapter = RecyclerAdapter(mainPresenter.totalUsers.value)
        recyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

        fab.setOnClickListener {
            startActivity(Intent(this, AddUserActivity::class.java))
        }
    }

    override fun onStart() {
        mainPresenter.totalUsers.observe(this, Observer { users ->
            recyclerView.adapter = RecyclerAdapter(users)
        })

        super.onStart()
    }

    override fun onResume() {
        mainPresenter.totalUsers.observe(this, Observer { users ->
            recyclerView.adapter = RecyclerAdapter(users)
        })

        super.onResume()
    }

    // For avoiding runtime errors the LiveData Observers need to be removed onStop and onDestroy
    override fun onStop() {
        mainPresenter.totalUsers.removeObservers(this)
        super.onStop()
    }

    override fun onDestroy() {
        mainPresenter.totalUsers.removeObservers(this)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Adds options to the top menu toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_go_to_fragment -> {
            startActivity(Intent(this, FragActivity::class.java))
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}