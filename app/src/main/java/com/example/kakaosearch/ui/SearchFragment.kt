package com.example.kakaosearch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kakaosearch.adapter.SearchAdapter
import com.example.kakaosearch.databinding.FragmentSearchBinding
import com.example.kakaosearch.viewModel.BookmarkViewModel
import com.example.kakaosearch.viewModel.SearchViewModel
import kotlinx.coroutines.Job


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels() //이거
    private val searchViewModel : SearchViewModel by viewModels()
    private val job : Job? = null
    private val adapter: SearchAdapter get() = binding.searchRV.adapter as SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifecycle","searchFrag : onCreate")
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
            adapter.notifyDataSetChanged()
        }
        searchViewModel.searchResults.observe(viewLifecycleOwner){
           adapter.submitList(it)

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
            searchRV.adapter = SearchAdapter( bookmarkViewModel)
            searchRV.layoutManager = GridLayoutManager(context, 2)
        }
    }
}