package com.example.kakaosearch.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kakaosearch.R
import com.example.kakaosearch.data.ItemType
import com.example.kakaosearch.data.KakaoItem
import com.example.kakaosearch.databinding.SearchItemBinding
import com.example.kakaosearch.extension.loadHeartImage
import com.example.kakaosearch.viewModel.BookmarkViewModel
import java.util.Locale

class SearchAdapter( private val bookmarkViewModel : BookmarkViewModel) : ListAdapter<KakaoItem, SearchAdapter.ViewHolder>(KakaoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {

        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
      val item = getItem(position) //그포지션에 있는 아이템을 가지고 온당
       holder.bindItems(item)
    }

    class KakaoItemDiffCallback : DiffUtil.ItemCallback<KakaoItem>() {
        override fun areItemsTheSame(oldItem: KakaoItem, newItem: KakaoItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: KakaoItem, newItem: KakaoItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
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
                        }
                    }
                }
            }
        }
    }
}