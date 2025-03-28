package com.example.swapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil

class PersonAdapter(
    private val onDeleteClick: (Person) -> Unit,
    private val onItemClick: (Person) -> Unit // Neu: Item-Klick-Funktion für Detailansicht
) : ListAdapter<Person, PersonAdapter.PersonViewHolder>(PersonDiffCallback()) {

    class PersonViewHolder(itemView: View, val onDeleteClick: (Person) -> Unit, val onItemClick: (Person) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val birthYearView: TextView = itemView.findViewById(R.id.birthYearView)
        val timestampView: TextView = itemView.findViewById(R.id.timestampView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(person: Person) {
            nameView.text = "Name: ${person.name}"
            birthYearView.text = "Geburtsjahr: ${person.birthYear}"
            timestampView.text = "Gespeichert: ${person.timestamp}"

            // Löschen-Button-Click-Listener
            deleteButton.setOnClickListener { onDeleteClick(person) }

            // Gesamt-Item-Klick für Detailansicht
            itemView.setOnClickListener { onItemClick(person) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view, onDeleteClick, onItemClick) // Beides übergeben
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
}