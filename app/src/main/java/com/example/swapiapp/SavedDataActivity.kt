package com.example.swapiapp

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
    private val selectedPeople = mutableListOf<Person>()
    private var isCompareMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_data)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "swapi_db").build()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PersonAdapter(
            onDeleteClick = { person -> confirmDeletePerson(person) },
            onItemClick = { person -> handleItemClick(person) },
            onItemLongClick = { person -> toggleSelection(person) }
        )

        recyclerView.adapter = adapter

        db.personDao().getAllPeople().observe(this, Observer { people ->
            adapter.submitList(people)
        })

        findViewById<FloatingActionButton>(R.id.deleteAllButton).setOnClickListener {
            confirmDeleteAll()
        }

        findViewById<Button>(R.id.compareButton).setOnClickListener {
            toggleCompareMode()
        }
    }

    private fun handleItemClick(person: Person) {
        if (isCompareMode) {
            toggleSelection(person)
        } else {
            navigateToDetail(person)
        }
    }

    private fun toggleSelection(person: Person) {
        if (selectedPeople.contains(person)) {
            selectedPeople.remove(person)
        } else {
            if (selectedPeople.size < 2) {
                selectedPeople.add(person)
            } else {
                Toast.makeText(this, "Du kannst nur zwei Charaktere vergleichen", Toast.LENGTH_SHORT).show()
            }
        }

        if (selectedPeople.size == 2) {
            startComparison()
        }
    }

    private fun toggleCompareMode() {
        isCompareMode = !isCompareMode
        selectedPeople.clear()
        Toast.makeText(this, if (isCompareMode) "Vergleichsmodus aktiviert" else "Vergleichsmodus deaktiviert", Toast.LENGTH_SHORT).show()
    }

    private fun startComparison() {
        if (selectedPeople.size == 2) {
            val intent = Intent(this, CompareActivity::class.java).apply {
                putExtra("person1", selectedPeople[0])
                putExtra("person2", selectedPeople[1])
            }
            startActivity(intent)
            isCompareMode = false
            selectedPeople.clear()
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
            putExtra("height", person.height)
            putExtra("mass", person.mass)
            putExtra("hairColor", person.hairColor)
            putExtra("eyeColor", person.eyeColor)
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