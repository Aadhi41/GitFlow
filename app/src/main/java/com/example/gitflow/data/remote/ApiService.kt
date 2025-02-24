package com.example.gitflow.data.remote

import com.example.gitflow.domain.Model
import retrofit2.http.GET

interface ApiService {
    @GET("v3/ca38288b-6458-460f-be4c-51625c14722c")
    suspend fun getFurniture(): Model
}