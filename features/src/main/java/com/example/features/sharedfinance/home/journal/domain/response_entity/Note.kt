package com.example.features.sharedfinance.home.journal.domain.response_entity

data class Note(
    val id: Long,
    val date: String,
    val sum: Long,
    val comment: String,
    val category: Category
) {
    data class Category(
        val name: String
    )
}
