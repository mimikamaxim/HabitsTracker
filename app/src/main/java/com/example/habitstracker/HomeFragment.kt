package com.example.habitstracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.databinding.FragmentHomeBinding
import kotlin.math.log

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.homeStartBtn.setOnClickListener {
            devDoSomeStuff{ Log.i(TAG,"PRESSED") }
            findNavController().navigate(R.id.habitsViewPagerFragment)
        }
        return binding.root
    }
}