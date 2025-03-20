package com.example.swapiapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthYear: String,
    val timestamp: String
)

@Dao
interface PersonDao {
    @Insert
    fun insert(person: Person)

    @Query("SELECT * FROM people")
    fun getAllPeople(): LiveData<List<Person>>

    @Query("DELETE FROM people")
    fun deleteAll()
}

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}