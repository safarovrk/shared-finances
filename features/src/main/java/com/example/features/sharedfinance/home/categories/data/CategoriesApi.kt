package com.example.features.sharedfinance.home.categories.data

import com.example.features.sharedfinance.home.categories.domain.Category
import com.example.features.sharedfinance.home.categories.domain.CreateDeleteCategoryRequest
import com.example.features.sharedfinance.home.categories.domain.ListCategoryRequest
import com.example.features.sharedfinance.invitations.domain.ChoiceRequest
import com.example.features.sharedfinance.invitations.domain.Invitation
import com.example.features.sharedfinance.invitations.domain.InvitationsRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoriesApi {
    @POST("/private/listCategory")
    suspend fun getCategories(@Body listCategoryRequest: ListCategoryRequest): Response<List<Category>>

    @POST("/private/addCategory")
    suspend fun addCategory(@Body createDeleteCategoryRequest: CreateDeleteCategoryRequest): Response<ResponseBody>

    @POST("/private/deleteCategory")
    suspend fun deleteCategory(@Body createDeleteCategoryRequest: CreateDeleteCategoryRequest): Response<ResponseBody>
}