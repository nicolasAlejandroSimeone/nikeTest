package com.example.niketest.repositories.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.niketest.models.Results

@Dao
interface SearchedWordDAO {
    @Insert
    fun insertLastWordSearched(results: MutableList<Results>)

    @Query("SELECT * FROM lastWordSearched WHERE word LIKE :param")
    fun getLastWordSearched(param:String):MutableList<Results>
}