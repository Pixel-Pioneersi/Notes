package com.example.notesapp.domain.cases_of_use

import com.example.notesapp.data.local.model.Note
import com.example.notesapp.domain.repository.Repository
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.insert(note)
}
