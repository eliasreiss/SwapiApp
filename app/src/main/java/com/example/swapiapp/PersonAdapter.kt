package com.example.swapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(
    private val onDeleteClick: (Person) -> Unit,
    private val onItemClick: (Person) -> Unit,
    private val onSelectionToggle: (Person) -> Unit, // Vergleichsauswahl toggeln
    private val isCompareMode: () -> Boolean, // Prüfen, ob Vergleichsmodus aktiv ist
    private val selectedPeople: () -> List<Person> // Gibt aktuell ausgewählte Personen zurück
) : ListAdapter<Person, PersonAdapter.PersonViewHolder>(PersonDiffCallback()) {

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val birthYearView: TextView = itemView.findViewById(R.id.birthYearView)
        val timestampView: TextView = itemView.findViewById(R.id.timestampView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(
            person: Person,
            isSelected: Boolean,
            isCompareMode: Boolean,
            onDeleteClick: (Person) -> Unit,
            onItemClick: (Person) -> Unit,
            onSelectionToggle: (Person) -> Unit
        ) {
            nameView.text = "Name: ${person.name}"
            birthYearView.text = "Geburtsjahr: ${person.birthYear}"
            timestampView.text = "Gespeichert: ${person.timestamp}"

            deleteButton.setOnClickListener { onDeleteClick(person) }

            itemView.setOnClickListener {
                if (isCompareMode) {
                    onSelectionToggle(person) // Auswahlmodus aktiv: Person markieren/demarkieren
                } else {
                    onItemClick(person) // Normalmodus: Detailansicht öffnen
                }
            }

            // Hintergrundfarbe ändern, wenn die Person für den Vergleich ausgewählt ist
            if (isCompareMode && isSelected) {
                itemView.setBackgroundResource(R.drawable.selected_item_background) // Dunklere Hintergrundfarbe mit abgerundeten Ecken
            } else {
                itemView.setBackgroundResource(R.drawable.default_item_background) // Standard-Hintergrund
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        holder.bind(
            person,
            selectedPeople().contains(person),
            isCompareMode(),
            onDeleteClick,
            onItemClick,
            onSelectionToggle
        )
    }
}

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
}