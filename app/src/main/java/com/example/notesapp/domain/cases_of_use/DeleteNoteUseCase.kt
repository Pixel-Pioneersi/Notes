package com.example.notesapp.domain.cases_of_use

import com.example.notesapp.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id:Long) = repository.delete(id)
}
