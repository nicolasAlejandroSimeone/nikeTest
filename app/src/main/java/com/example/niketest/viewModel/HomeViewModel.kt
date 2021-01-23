package com.example.niketest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.niketest.models.List
import com.example.niketest.models.Results
import com.example.niketest.repositories.SearchRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _results = MutableLiveData<List>()
    val results : LiveData<List>
        get() = _results

    private val _resultsDb = MutableLiveData<MutableList<Results>>()
    val resultsDb : LiveData<MutableList<Results>>
        get() = _resultsDb



    fun getAllResults(param:String){
        viewModelScope.launch {
            _results.value.let {
                _results.value = searchRepository.getResults(param)
            }
        }
    }


    fun getAllResultsDb(param: String){
        viewModelScope.launch {
            _resultsDb.value = searchRepository.getWordResultsDb(param)
        }
    }

    fun insertResultsDb(data:MutableList<Results>){
        viewModelScope.launch {
            searchRepository.insertWordOnDb(data)
        }
    }

}