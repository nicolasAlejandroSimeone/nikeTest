package com.example.niketest.repositories

import com.example.niketest.models.List
import com.example.niketest.models.Results
import com.example.niketest.repositories.local.AppDatabase
import com.example.niketest.repositories.remote.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.niketest.utils.Result
import java.nio.channels.MulticastChannel

class SearchRepository(private val remoteDataSource: SearchService, private val appDatabase: AppDatabase) {
    suspend fun getResults(param:String): List = withContext(Dispatchers.IO)
    {
        when (val result = remoteDataSource.getProducts(param)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }

    suspend fun getWordResultsDb(param: String): MutableList<Results> = withContext(Dispatchers.IO) {

        return@withContext appDatabase.searchedWordDAO().getLastWordSearched(param)
    }

    suspend fun insertWordOnDb(results: MutableList<Results>) = withContext(Dispatchers.IO) {

        return@withContext appDatabase.searchedWordDAO().insertLastWordSearched(results)
    }

}