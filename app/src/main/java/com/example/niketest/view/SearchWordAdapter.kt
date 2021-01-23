package com.example.niketest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.niketest.R
import com.example.niketest.models.Results
import com.example.niketest.utils.formatAuthor
import com.example.niketest.utils.formatDefinition
import com.example.niketest.utils.formatWord
import kotlinx.android.synthetic.main.list_item.view.*

class SearchWordAdapter(private val context: Context, private var searchFindList:MutableList<Results>): RecyclerView.Adapter<SearchWordAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchWordAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    fun refreshList(noticeList: MutableList<Results>) {
        this.searchFindList.clear()
        this.searchFindList = noticeList.toMutableList()
        notifyDataSetChanged()
    }

    fun refreshUpOrDown(list: MutableList<Results>) {
        this.searchFindList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : Results {
        return searchFindList[position]
    }

    fun orderUp(list: MutableList<Results>):MutableList<Results>{
        this.searchFindList = list.sortedByDescending { x -> x.thumbs_up } as MutableList<Results>
        return searchFindList
    }

    fun orderDown(list: MutableList<Results>):MutableList<Results>{
        this.searchFindList = list.sortedByDescending { x -> x.thumbs_down } as MutableList<Results>
        return searchFindList
    }


    override fun onBindViewHolder(holder: SearchWordAdapter.ViewHolder, position: Int) {
        holder.txtWord.text = formatWord(searchFindList[position].word)
        holder.txtDefinition.text = formatDefinition(searchFindList[position].definition)
        holder.txtAuthor.text = formatAuthor(searchFindList[position].author)
        holder.txtThumbsUp.text = searchFindList[position].thumbs_up.toString()
        holder.txtThumbsDown.text = searchFindList[position].thumbs_down.toString()
    }

    inner class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        var txtWord: TextView = itemView.word
        var txtDefinition: TextView = itemView.definition
        var txtAuthor : TextView = itemView.author
        var txtThumbsUp : TextView = itemView.thumbsUp
        var txtThumbsDown : TextView = itemView.thumbsDown

    }

    override fun getItemCount(): Int {
        return searchFindList.size
    }
}