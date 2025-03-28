package com.example.swapiapp

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SavedDataActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_data)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "swapi_db").build()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PersonAdapter(
            onDeleteClick = { person -> confirmDeletePerson(person) },
            onItemClick = { person -> navigateToDetail(person) } // NEU: Detailansicht öffnen
        )

        recyclerView.adapter = adapter

        db.personDao().getAllPeople().observe(this, Observer { people ->
            adapter.submitList(people)
        })

        findViewById<FloatingActionButton>(R.id.deleteAllButton).setOnClickListener {
            confirmDeleteAll()
        }
    }

    private fun confirmDeletePerson(person: Person) {
        AlertDialog.Builder(this)
            .setTitle("Eintrag löschen")
            .setMessage("Möchtest du den Eintrag von ${person.name} wirklich löschen?")
            .setPositiveButton("Ja") { _, _ -> DeletePersonTask(db).execute(person) }
            .setNegativeButton("Nein", null)
            .show()
    }

    private fun confirmDeleteAll() {
        AlertDialog.Builder(this)
            .setTitle("Alle Einträge löschen")
            .setMessage("Möchtest du wirklich alle gespeicherten Einträge löschen?")
            .setPositiveButton("Ja") { _, _ -> DeleteAllTask(db).execute() }
            .setNegativeButton("Nein", null)
            .show()
    }

    private fun navigateToDetail(person: Person) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("name", person.name)
            putExtra("birthYear", person.birthYear)
            putExtra("timestamp", person.timestamp)
        }
        startActivity(intent)
    }

    private class DeletePersonTask(val db: AppDatabase) : AsyncTask<Person, Void, Void>() {
        override fun doInBackground(vararg persons: Person): Void? {
            db.personDao().delete(persons[0])
            return null
        }
    }

    private class DeleteAllTask(val db: AppDatabase) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            db.personDao().deleteAll()
            return null
        }
    }
}