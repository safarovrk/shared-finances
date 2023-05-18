package com.example.features.sharedfinance.home.categories.domain

import okhttp3.ResponseBody
import retrofit2.Response

interface CategoriesRepository {

    suspend fun getCategories(listCategoryRequest: ListCategoryRequest): Response<List<Category>>

    suspend fun addCategory(createDeleteCategoryRequest: CreateDeleteCategoryRequest): Response<ResponseBody>

    suspend fun deleteCategory(createDeleteCategoryRequest: CreateDeleteCategoryRequest): Response<ResponseBody>
}