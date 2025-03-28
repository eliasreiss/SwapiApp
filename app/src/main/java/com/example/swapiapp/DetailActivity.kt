package com.example.swapiapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val nameView: TextView = findViewById(R.id.detailNameView)
        val birthYearView: TextView = findViewById(R.id.detailBirthYearView)
        val timestampView: TextView = findViewById(R.id.detailTimestampView)

        // Daten aus Intent abrufen
        val name = intent.getStringExtra("name")
        val birthYear = intent.getStringExtra("birthYear")
        val timestamp = intent.getStringExtra("timestamp")

        // Daten in die Views setzen
        nameView.text = "Name: $name"
        birthYearView.text = "Geburtsjahr: $birthYear"
        timestampView.text = "Gespeichert am: $timestamp"
    }
}