package com.example.gitflow.data.remote

import com.example.gitflow.domain.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/1d2ff89c-938d-443e-91d7-157278d47ef8")
    suspend fun getFurniture(): Model

}