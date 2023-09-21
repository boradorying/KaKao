package com.example.kakaosearch.adapter

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kakaosearch.R
import com.example.kakaosearch.data.ItemType
import com.example.kakaosearch.data.KakaoItem
import com.example.kakaosearch.databinding.BookmarkItemBinding
import com.example.kakaosearch.extension.loadHeartImage

import com.example.kakaosearch.viewModel.BookmarkViewModel
import java.util.Locale

class BookmarkAdapter(private val bookmarkViewModel : BookmarkViewModel):RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
   var bookmarkList = mutableListOf<KakaoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {

        val binding = BookmarkItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        val item = bookmarkList[position]
        Log.d("jun","어댑터${bookmarkList}")
        holder.bindItems(item)
    }

    override fun getItemCount(): Int {

        return bookmarkList.size
    }
    inner class ViewHolder(private val binding: BookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: KakaoItem) {
            binding.apply {

                when (item.itemType) {
                    ItemType.IMAGE -> {
                        nameArea.text = "[Image] ${item.title}"
                    }

                    ItemType.VIDEO -> {
                        nameArea.text = "[Video] ${item.title}"
                    }
                }
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())//원래형식 ISO8601 파싱
                val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) //파싱된날짜 원하는 걸로

                val date = inputFormat.parse(item.datetime)
                dateArea.text = outputFormat.format(date)

                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(imageArea)
                binding.apply {
                    bookmarkBtn.loadHeartImage(item.isHeart)
                    bookmarkBtn.setOnClickListener {
                        item.isHeart = !item.isHeart
                        if (item.isHeart){
                            bookmarkBtn.setImageResource(R.drawable.baseline_favorite_24)
                            bookmarkViewModel.addBookmark(item)

                        }else{
                            bookmarkBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                            bookmarkViewModel.removeBookmark(item)
                            Log.d("jun","안조아여${bookmarkList}")
                        }
                    }
                }
            }
        }
    }
}