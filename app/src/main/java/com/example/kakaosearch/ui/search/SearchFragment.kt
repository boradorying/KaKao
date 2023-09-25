package com.example.kakaosearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kakaosearch.adapter.SearchAdapter
import com.example.kakaosearch.data.KakaoVideo
import com.example.kakaosearch.data.KakaoVideoResponse
import com.example.kakaosearch.databinding.FragmentSearchBinding
import com.example.kakaosearch.viewModel.bookmark.BookmarkViewModel
import com.example.kakaosearch.viewModel.search.SearchViewModel
import com.example.kakaosearch.retrofit.RepositoryImpl
import com.example.kakaosearch.retrofit.RetrofitInstance
import com.example.kakaosearch.viewModel.search.SearchViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private lateinit var searchViewModel: SearchViewModel
    private val adapter: SearchAdapter by lazy { binding.searchRV.adapter as SearchAdapter }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        searchButton()
        bookmarkViewModel.bookmarkedItems.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
        searchViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
        }
    }


    private fun setupViewModel() {
        val repository = RepositoryImpl(RetrofitInstance.apiService)
        val viewModelFactory = SearchViewModelFactory(repository)

        searchViewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
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
            searchRV.adapter = SearchAdapter(bookmarkViewModel)
            searchRV.layoutManager = GridLayoutManager(context, 2)
        }
    }
}