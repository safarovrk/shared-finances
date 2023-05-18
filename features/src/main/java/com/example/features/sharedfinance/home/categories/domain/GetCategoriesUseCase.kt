package com.example.features.sharedfinance.home.categories.domain

import com.example.core.utils.Resource
import com.example.features.R
import com.example.features.sharedfinance.list_journals.domain.Journal
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    operator fun invoke(listCategoryRequest: ListCategoryRequest): Flow<Resource<Response<List<Category>>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getCategories(listCategoryRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}