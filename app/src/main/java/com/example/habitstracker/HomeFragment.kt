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
            findNavController().navigate(R.id.habitsFragment)
        }
        return binding.root
    }

//        companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment HabitsViewPagerFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            HabitsViewPagerFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}