package com.example.features.sharedfinance.home.categories.domain

import com.example.core.utils.Resource
import com.example.features.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CreateDeleteCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    operator fun invoke(createDeleteCategoryRequest: CreateDeleteCategoryRequest, creating: Boolean): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            emit(Resource.Loading())
            val response: Response<ResponseBody> =
                if (creating) repository.addCategory(createDeleteCategoryRequest)
                else repository.deleteCategory(createDeleteCategoryRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}