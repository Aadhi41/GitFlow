package com.example.gitflow.data.remote

import com.example.gitflow.domain.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/fbff387b-7a33-4c66-88ad-5a4e44445d47")
    suspend fun getFurniture(): Model

}