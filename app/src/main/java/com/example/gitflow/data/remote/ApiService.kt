package com.example.gitflow.data.remote

import com.example.gitflow.domain.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/e8650b6e-db1e-4172-98cf-ca6ca354c154")
    suspend fun getFurniture(): Model

    @GET("v3/c994cdbc-7230-4357-b95b-dfece5a8f316")
    suspend fun searchFurniture(@Query("query") query: String): Model
}