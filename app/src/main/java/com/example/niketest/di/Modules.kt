package com.example.niketest.di

import com.example.niketest.repositories.SearchRepository
import com.example.niketest.repositories.local.AppDatabase
import com.example.niketest.repositories.remote.ApiInstance
import com.example.niketest.repositories.remote.SearchService
import com.example.niketest.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiInstance.create() }
}

val dbModule = module {
    single{ AppDatabase.getDatabase(get()) }
    single{ (get<AppDatabase>().searchedWordDAO())}
}

val searchgModule = module {
    viewModel { HomeViewModel(get()) }
    single { SearchRepository(get(), get()) }
    single { SearchService((get())) }
}