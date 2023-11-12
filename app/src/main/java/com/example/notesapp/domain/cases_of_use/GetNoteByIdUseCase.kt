package com.example.notesapp.domain.cases_of_use

import com.example.notesapp.domain.repository.Repository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: Long) = repository.getNoteById(id)
}