package com.example.swapiapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        val name1View: TextView = findViewById(R.id.compareName1View)
        val birthYear1View: TextView = findViewById(R.id.compareBirthYear1View)
        val height1View: TextView = findViewById(R.id.compareHeight1View)
        val mass1View: TextView = findViewById(R.id.compareMass1View)
        val hairColor1View: TextView = findViewById(R.id.compareHairColor1View)
        val eyeColor1View: TextView = findViewById(R.id.compareEyeColor1View)

        val name2View: TextView = findViewById(R.id.compareName2View)
        val birthYear2View: TextView = findViewById(R.id.compareBirthYear2View)
        val height2View: TextView = findViewById(R.id.compareHeight2View)
        val mass2View: TextView = findViewById(R.id.compareMass2View)
        val hairColor2View: TextView = findViewById(R.id.compareHairColor2View)
        val eyeColor2View: TextView = findViewById(R.id.compareEyeColor2View)

        val person1 = intent.getParcelableExtra<Person>("person1")
        val person2 = intent.getParcelableExtra<Person>("person2")

        if (person1 != null && person2 != null) {
            name1View.text = person1.name
            birthYear1View.text = person1.birthYear
            height1View.text = "${person1.height} cm"
            mass1View.text = "${person1.mass} kg"
            hairColor1View.text = person1.hairColor
            eyeColor1View.text = person1.eyeColor

            name2View.text = person2.name
            birthYear2View.text = person2.birthYear
            height2View.text = "${person2.height} cm"
            mass2View.text = "${person2.mass} kg"
            hairColor2View.text = person2.hairColor
            eyeColor2View.text = person2.eyeColor
        } else {
            name1View.text = "Keine Daten"
            birthYear1View.text = "Keine Daten"
            height1View.text = "Keine Daten"
            mass1View.text = "Keine Daten"
            hairColor1View.text = "Keine Daten"
            eyeColor1View.text = "Keine Daten"

            name2View.text = "Keine Daten"
            birthYear2View.text = "Keine Daten"
            height2View.text = "Keine Daten"
            mass2View.text = "Keine Daten"
            hairColor2View.text = "Keine Daten"
            eyeColor2View.text = "Keine Daten"
        }
    }
}