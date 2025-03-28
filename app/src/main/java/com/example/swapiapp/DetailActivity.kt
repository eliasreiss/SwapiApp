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
        val heightView: TextView = findViewById(R.id.detailHeightView)
        val massView: TextView = findViewById(R.id.detailMassView)
        val hairColorView: TextView = findViewById(R.id.detailHairColorView)
        val eyeColorView: TextView = findViewById(R.id.detailEyeColorView)
        val timestampView: TextView = findViewById(R.id.detailTimestampView)

        // Daten aus Intent abrufen
        val name = intent.getStringExtra("name")
        val birthYear = intent.getStringExtra("birthYear")
        val height = intent.getStringExtra("height")
        val mass = intent.getStringExtra("mass")
        val hairColor = intent.getStringExtra("hairColor")
        val eyeColor = intent.getStringExtra("eyeColor")
        val timestamp = intent.getStringExtra("timestamp")

        // Daten in die Views setzen
        nameView.text = "Name: $name"
        birthYearView.text = "Geburtsjahr: $birthYear"
        heightView.text = "Größe: $height cm"
        massView.text = "Gewicht: $mass kg"
        hairColorView.text = "Haarfarbe: $hairColor"
        eyeColorView.text = "Augenfarbe: $eyeColor"
        timestampView.text = "Gespeichert am: $timestamp"
    }
}