package com.example.notesapp.domain.cases_of_use


import com.example.notesapp.data.local.model.Note
import com.example.notesapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilteredBookMarkNotes @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getBookMarkedNotes()
    }
}