package com.example.swapiapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class SavedDataActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_data)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "swapi_db").build()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PersonAdapter()
        recyclerView.adapter = adapter

        db.personDao().getAllPeople().observe(this, Observer { people ->
            adapter.submitList(people)
        })
    }
}