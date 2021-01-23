package com.example.niketest.repositories.remote

import com.example.niketest.models.List
import com.example.niketest.utils.Result

class SearchService(private val api: ApiClient) {
    suspend fun getProducts(param:String): Result<List> {

        val response = api.getProductsListAsync(param).await()
        val body = response.body()
        body?.let {
            return Result.Success(body)
        } ?: run {
            val messageError = when(response.code()){
                401 -> "Unauthorize"
                502 -> "bad gateway"
                else -> "unexpected_error"
            }
            return Result.Error(
                Exception(messageError)
            )
        }
    }
}