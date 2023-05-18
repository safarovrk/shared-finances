package com.example.features.sharedfinance.list_journals.domain

import com.example.core.utils.Resource
import com.example.features.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetRoleNetworkUseCase @Inject constructor(
    private val repository: ListJournalsRepository
) {
    operator fun invoke(roleRequest: RoleRequest): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getUserRole(roleRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}