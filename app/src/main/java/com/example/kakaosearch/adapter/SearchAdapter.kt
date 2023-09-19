package com.example.kakaosearch.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.kakaosearch.data.KaKaoImage
import com.example.kakaosearch.databinding.SearchItemBinding

class SearchAdapter (private val itemList: MutableList<KaKaoImage>):RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {

       val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bindItems(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun searchData(newData: MutableList<KaKaoImage>) { //검색할때 다시 새로운 리스트
        if (newData.isNotEmpty()) {
            itemList.clear()
            itemList.addAll(newData)
            notifyDataSetChanged()
        }
    }
   inner class ViewHolder(private val binding: SearchItemBinding):RecyclerView.ViewHolder(binding.root){

       fun bindItems(item:KaKaoImage){
           binding.apply {
               nameArea.text = item.displaySitename
                dateArea.text =item.datetime
               Glide.with(itemView.context)
                   .load(item.thumbnailUrl)
                   .into(imageArea)
           }
       }
   }
}