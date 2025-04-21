package com.example.gitflow.data.remote

import com.example.gitflow.domain.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/66fca20c-3bf1-4f68-af41-ec48d027d102")
    suspend fun getFurniture(): Model

}