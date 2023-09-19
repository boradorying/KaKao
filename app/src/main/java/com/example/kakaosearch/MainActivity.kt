package com.example.kakaosearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kakaosearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val searchFrag by lazy { SearchFragment() }
    private val bookFrag by lazy { BookmarkFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fragments = listOf(searchFrag, bookFrag)
        val adapter = PageAdapter(this, fragments)


        binding.viewPager2.adapter =adapter

        binding.dots.setViewPager2(binding.viewPager2)
     }
}