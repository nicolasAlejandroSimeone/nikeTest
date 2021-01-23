package com.example.niketest

import android.app.Application
import com.example.niketest.di.apiModule
import com.example.niketest.di.dbModule
import com.example.niketest.di.searchgModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(apiModule, dbModule, searchgModule))
        }
    }
}