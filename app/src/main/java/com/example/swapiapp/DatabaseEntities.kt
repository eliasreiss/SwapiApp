package com.example.swapiapp

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthYear: String,
    val height: String,       // Neu: Größe
    val mass: String,         // Neu: Gewicht
    val hairColor: String,    // Neu: Haarfarbe
    val eyeColor: String,     // Neu: Augenfarbe
    val timestamp: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(birthYear)
        parcel.writeString(height)
        parcel.writeString(mass)
        parcel.writeString(hairColor)
        parcel.writeString(eyeColor)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface PersonDao {
    @Insert
    fun insert(person: Person)

    @Query("SELECT * FROM people")
    fun getAllPeople(): LiveData<List<Person>>

    @Query("DELETE FROM people")
    fun deleteAll()

    @Delete
    fun delete(person: Person)
}

@Database(entities = [Person::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
