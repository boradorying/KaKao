package com.example.kakaosearch.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager

import com.example.kakaosearch.adapter.SearchAdapter
import com.example.kakaosearch.data.Constants
import com.example.kakaosearch.data.ItemType
import com.example.kakaosearch.data.KaKaoImage
import com.example.kakaosearch.data.KakaoItem
import com.example.kakaosearch.databinding.FragmentSearchBinding
import com.example.kakaosearch.retrofit.RetrofitInstance
import com.example.kakaosearch.viewModel.BookmarkViewModel
import com.example.kakaosearch.viewModel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val items: MutableList<KakaoItem> by lazy { mutableListOf() }
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private val searchViewModel : SearchViewModel by activityViewModels()
    private val job : Job? = null
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
        bookmarkViewModel.bookmarkedItems.observe(viewLifecycleOwner) {
            // bookmarkedItems를 사용하여 북마크 상태를 업데이트
            for (item in items) {
                item.isHeart = it.contains(item) //토글이 아니라 화면에 표시되는 아이템의 북마크상태가 bookmarkeditems와 일치하도록 만드는 것
            }
            adapter.notifyDataSetChanged()
        }
        searchViewModel.searchResults.observe(viewLifecycleOwner){
            items.clear()
            items.addAll(it)
            adapter.notifyDataSetChanged()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()

    }

    private fun searchButton() {
        binding.searchBtn.setOnClickListener {
            val query = binding.searchEdit.text.toString()
            if (query.isNotEmpty()) {
                searchViewModel.searchInfo(query)
            }
        }
    }


    private fun setupRecyclerView() {
        binding.apply {
            searchRV.adapter = SearchAdapter(items, bookmarkViewModel)
            searchRV.layoutManager = GridLayoutManager(context, 2)
        }

    }
}