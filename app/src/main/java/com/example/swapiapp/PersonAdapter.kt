package com.example.swapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil

class PersonAdapter : ListAdapter<Person, PersonAdapter.PersonViewHolder>(PersonDiffCallback()) {
    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val birthYearView: TextView = itemView.findViewById(R.id.birthYearView)
        val timestampView: TextView = itemView.findViewById(R.id.timestampView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        holder.nameView.text = "Name: ${person.name}"
        holder.birthYearView.text = "Geburtsjahr: ${person.birthYear}"
        holder.timestampView.text = "Gespeichert: ${person.timestamp}"
    }
}

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
}