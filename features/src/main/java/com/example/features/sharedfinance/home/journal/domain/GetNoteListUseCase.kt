package com.example.features.sharedfinance.home.journal.domain

import com.example.core.utils.Resource
import com.example.features.R
import com.example.features.sharedfinance.home.journal.domain.request_body.NoteListRequest
import com.example.features.sharedfinance.home.journal.domain.response_entity.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetNoteListUseCase @Inject constructor(
    private val repository: JournalRepository
) {
    operator fun invoke(noteListRequest: NoteListRequest): Flow<Resource<Response<List<Note>>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getNoteList(noteListRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}