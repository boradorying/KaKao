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
import com.example.kakaosearch.data.KaKaoImage
import com.example.kakaosearch.databinding.FragmentSearchBinding
import com.example.kakaosearch.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val items: MutableList<KaKaoImage> = mutableListOf()
    private val adapter: SearchAdapter get() = binding.searchRV.adapter as SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                searchInfoImage(query)
            }
        }
    }

    private fun searchInfoImage(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.getApiService()
                .searchImage(Constants.AUTH_KEY, query, "accuracy", 1, 80)

            if (response.isSuccessful) {
                Log.d("jun", "성공")
                val imageResponse = response.body()
                val documents = imageResponse?.documents

                if (documents != null) {
                    withContext(Dispatchers.Main) {
                        adapter.searchData(documents)

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