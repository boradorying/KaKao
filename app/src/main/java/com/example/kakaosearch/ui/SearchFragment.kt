package com.example.kakaosearch.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager

import com.example.kakaosearch.adapter.SearchAdapter
import com.example.kakaosearch.data.Constants
import com.example.kakaosearch.data.ItemType
import com.example.kakaosearch.data.KaKaoImage
import com.example.kakaosearch.data.KakaoItem
import com.example.kakaosearch.databinding.FragmentSearchBinding
import com.example.kakaosearch.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val items: MutableList<KakaoItem> by lazy { mutableListOf() }
    private val adapter: SearchAdapter get() = binding.searchRV.adapter as SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        searchButton()
    }

    private fun searchButton() {
        binding.searchBtn.setOnClickListener {
            val query = binding.searchEdit.text.toString()
            if (query.isNotEmpty()) {
                searchInfo(query)
            }
        }
    }

    private fun searchInfo(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val imageResponse = RetrofitInstance.getApiService()
                .searchImage(Constants.AUTH_KEY, query, "accuracy", 1, 80)
            val videoResponse = RetrofitInstance.getApiService()
                .searchVideo(Constants.AUTH_KEY, query, "accuracy", 1, 15)

            if (imageResponse.isSuccessful && videoResponse.isSuccessful) {
                val imageDocuments = imageResponse.body()?.documents
                val videoDocuments = videoResponse.body()?.documents

                if (imageDocuments != null && videoDocuments != null) {
                    val combinedList = mutableListOf<KakaoItem>()

                    // 이미지와 동영상을 KaKaoItem으로 통합하여 리스트에 추가
                    imageDocuments.forEach {
                        combinedList.add(KakaoItem(it.thumbnailUrl, it.displaySitename, it.datetime, ItemType.IMAGE,false))
                    }
                    videoDocuments.forEach {
                        combinedList.add(KakaoItem(it.thumbnailUrl, it.title, it.datetime, ItemType.VIDEO,false))
                    }
                    combinedList.sortByDescending { it.datetime }//시간역순정렬

                    withContext(Dispatchers.Main) {
                        adapter.searchData(combinedList)
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding.apply {
            searchRV.adapter = SearchAdapter(items)
            searchRV.layoutManager = GridLayoutManager(context, 2)
        }
    }
}