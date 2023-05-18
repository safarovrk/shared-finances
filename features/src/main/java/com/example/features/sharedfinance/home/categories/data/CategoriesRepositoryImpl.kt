package com.example.features.sharedfinance.home.categories.data

import com.example.features.sharedfinance.home.categories.domain.CategoriesRepository
import com.example.features.sharedfinance.home.categories.domain.Category
import com.example.features.sharedfinance.home.categories.domain.CreateDeleteCategoryRequest
import com.example.features.sharedfinance.home.categories.domain.ListCategoryRequest
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi
) : CategoriesRepository {
    override suspend fun getCategories(listCategoryRequest: ListCategoryRequest): Response<List<Category>> {
        return api.getCategories(listCategoryRequest)
    }

    override suspend fun addCategory(createDeleteCategoryRequest: CreateDeleteCategoryRequest): Response<ResponseBody> {
        return api.addCategory(createDeleteCategoryRequest)
    }

    override suspend fun deleteCategory(createDeleteCategoryRequest: CreateDeleteCategoryRequest): Response<ResponseBody> {
        return api.deleteCategory(createDeleteCategoryRequest)
    }
}