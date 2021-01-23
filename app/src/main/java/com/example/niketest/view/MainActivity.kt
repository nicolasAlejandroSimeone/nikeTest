package com.example.niketest.view

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.models.Results
import com.example.niketest.utils.ConnectivityHelper
import com.example.niketest.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapterResults: SearchWordAdapter
    private var resultList : MutableList<Results> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapterResults = SearchWordAdapter(this, resultList)

        llProgressBar.visibility = View.GONE

        findViewById<RecyclerView>(R.id.searchAll).apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            layoutManager = LinearLayoutManager (context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterResults

        }

        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                no_internet_detail.visibility = View.GONE
                notConnection.visibility = View.GONE
                llProgressBar.visibility = View.VISIBLE
                filter(editText.text.toString())
            }
            false
        }

        no_internet_detail.tryAgainAction = {
            no_internet_detail.visibility = View.GONE
            relative_main.visibility = View.VISIBLE
            llProgressBar.visibility = View.GONE
            lookWord.visibility = View.GONE
            editText.text.clear()

        }

        viewModel.resultsDb.observe(this, Observer {
            resultList = it
            if (resultList.isNullOrEmpty()){
                relative_main.visibility = View.GONE
                notConnection.visibility = View.VISIBLE
            }else{
                lookWord.visibility = View.GONE
                searchAll.visibility = View.VISIBLE
                no_internet_detail.visibility = View.GONE
                adapterResults.refreshList(resultList)
                llProgressBar.visibility = View.INVISIBLE
                relative_main.visibility = View.VISIBLE
                notConnection.visibility = View.GONE
            }
        })

        viewModel.results.observe(this, Observer {
                resultList = it.list
                resultList.let { viewModel.insertResultsDb(resultList) }
                lookWord.visibility = View.GONE
                searchAll.visibility = View.VISIBLE
                adapterResults.refreshList(resultList)
                llProgressBar.visibility = View.GONE
                searchAll.visibility = View.VISIBLE
                notConnection.visibility = View.GONE
        })
    }

    private fun filter(text :String){
        if(checkConnectivity()){
            if(text.isNotEmpty()){
                viewModel.getAllResults(text)
            }
        }else{
            if(text.isNotEmpty()){
                viewModel.getAllResultsDb(text)
            }
        }
    }

    private fun checkConnectivity(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (ConnectivityHelper.getConnectionType(this)) {
                NetworkCapabilities.TRANSPORT_WIFI -> {
                    no_internet_detail.visibility = View.GONE
                    relative_main.visibility = View.VISIBLE
                    return true
                }
                NetworkCapabilities.TRANSPORT_CELLULAR -> {
                    no_internet_detail.visibility = View.GONE
                    relative_main.visibility = View.VISIBLE
                    return true
                }

                else -> {
                    no_internet_detail.visibility = View.VISIBLE
                    relative_main.visibility = View.GONE
                    return false
                }
            }
        }else{
            when (ConnectivityHelper.getConnectionTypeSDK21(this)) {
                ConnectivityManager.TYPE_WIFI -> {
                    no_internet_detail.visibility = View.GONE
                    relative_main.visibility = View.VISIBLE
                    return true
                }
                0 -> {
                    no_internet_detail.visibility = View.GONE
                    relative_main.visibility = View.VISIBLE
                    return true
                }

                else -> {
                    no_internet_detail.visibility = View.VISIBLE
                    relative_main.visibility = View.GONE
                    return false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.orderByDown -> {
                resultList = adapterResults.orderDown(resultList)
                adapterResults.refreshUpOrDown(resultList)
                true
            }
            R.id.orderByUp -> {
                resultList = adapterResults.orderUp(resultList)
                adapterResults.refreshUpOrDown(resultList)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}