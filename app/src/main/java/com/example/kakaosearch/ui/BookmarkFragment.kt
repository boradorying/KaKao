package com.example.kakaosearch.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakaosearch.adapter.BookmarkAdapter
import com.example.kakaosearch.databinding.FragmentBookmarkBinding
import com.example.kakaosearch.viewModel.BookmarkViewModel


class BookmarkFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding
    private val adapter get() = binding.bookRv.adapter as BookmarkAdapter
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        context?.let { bookmarkViewModel.load(it) } //저장한거 불러오기
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        binding.apply {
            bookRv.adapter = BookmarkAdapter(bookmarkViewModel)
            bookRv.layoutManager = LinearLayoutManager(context)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkViewModel.bookmarkedItems.observe(viewLifecycleOwner) {
            adapter.bookmarkList = it
            bookmarkViewModel.save(requireContext())
            Log.d("jun", "${adapter.bookmarkList}")
            adapter.notifyDataSetChanged()
        }
    }
}