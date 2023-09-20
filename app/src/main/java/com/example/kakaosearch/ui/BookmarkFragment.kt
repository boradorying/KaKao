package com.example.kakaosearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakaosearch.R
import com.example.kakaosearch.adapter.BookmarkAdapter
import com.example.kakaosearch.databinding.FragmentBookmarkBinding
import java.util.zip.Inflater


class BookmarkFragment : Fragment() {
    private lateinit var binding : FragmentBookmarkBinding
    private val adapter get() = binding.bookRv.adapter as BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookmarkBinding.inflate(inflater,container,false)


        binding.apply {
            bookRv.adapter = BookmarkAdapter()
            bookRv.layoutManager = LinearLayoutManager(context)

            return binding.root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}