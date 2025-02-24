package com.example.gitflow.domain

data class Furniture(
    val description: String,
    val images: List<String>,
    val price: Double,
    val rating: String,
    val reviews: Int,
    val title: String
)