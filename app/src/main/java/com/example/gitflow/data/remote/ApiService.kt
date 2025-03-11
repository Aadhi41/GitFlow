package com.example.gitflow.data.remote

import com.example.gitflow.domain.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/b8136d2b-9d7d-470a-8cb5-980d7c365d2d")
    suspend fun getFurniture(): Model

    @GET("v3/c994cdbc-7230-4357-b95b-dfece5a8f316")
    suspend fun searchFurniture(@Query("query") query: String): Model
}