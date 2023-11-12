package com.example.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesapp.data.local.converters.DataConverter
import com.example.notesapp.data.local.model.Note

@TypeConverters(value = [DataConverter::class])
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
    )
abstract class NoteDataBase:RoomDatabase() {
    abstract val noteDao: NoteDao
}