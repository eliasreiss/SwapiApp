package com.example.swapiapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var inputId: EditText
    private lateinit var resultView: TextView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputId = findViewById(R.id.inputId)
        resultView = findViewById(R.id.resultView)
        val fetchButton: Button = findViewById(R.id.fetchButton)
        val viewSavedButton: Button = findViewById(R.id.viewSavedButton)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "swapi_db").build()

        fetchButton.setOnClickListener { fetchData() }
        viewSavedButton.setOnClickListener {
            startActivity(Intent(this, SavedDataActivity::class.java))
        }
    }

    private fun fetchData() {
        val id = inputId.text.toString()
        if (id.isEmpty()) {
            Toast.makeText(this, "Bitte ID eingeben", Toast.LENGTH_SHORT).show()
            return
        }
        FetchDataTask().execute("https://swapi.dev/api/people/$id/")
    }

    private inner class FetchDataTask : AsyncTask<String, Void, String?>() {
        override fun doInBackground(vararg urls: String): String? {
            return try {
                val url = URL(urls[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()
                response
            } catch (e: Exception) {
                null
            }
        }

        override fun onPostExecute(result: String?) {
            if (result == null) {
                Toast.makeText(this@MainActivity, "Fehler beim Abrufen", Toast.LENGTH_SHORT).show()
                return
            }
            try {
                val jsonObject = JSONObject(result)
                val name = jsonObject.getString("name")
                val birthYear = jsonObject.getString("birth_year")
                val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                resultView.text = "Name: $name\nGeburtsjahr: $birthYear"

                val person = Person(name = name, birthYear = birthYear, timestamp = timestamp)
                lifecycleScope.launch {
                    db.personDao().insert(person)
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Fehler beim Verarbeiten", Toast.LENGTH_SHORT).show()
            }
        }
    }
}