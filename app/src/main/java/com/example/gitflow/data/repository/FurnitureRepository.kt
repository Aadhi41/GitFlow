package com.example.gitflow.data.repository

import com.example.gitflow.data.remote.ApiService
import com.example.gitflow.domain.Model

class FurnitureRepository(private val apiService: ApiService) {
    suspend fun FurnitureResponse(): Model {  // Change List<Category> to Model
        return try {
            val response = apiService.getFurniture()
            println("API Response: $response")
            response
        } catch (e: Exception) {
            println("API Error: ${e.message}")
            Model(emptyList())  // Return empty Model instead of List<Category>
        }
    }
}