package com.example.niketest.repositories.remote

import com.example.niketest.models.List
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiClient {
    @Headers(
        "x-rapidapi-key:8a1cf9cf6amshbc7d747bdb2a0f6p1b7299jsnecc21bae889b",
        "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com"
    )
    @GET("/define")
    fun getProductsListAsync(@Query("term") param:String): Deferred<Response<List>>
}