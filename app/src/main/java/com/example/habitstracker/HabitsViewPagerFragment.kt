package com.example.habitstracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.habitstracker.data.HabitsType
import com.example.habitstracker.databinding.FragmentHabitsViewPagerBinding

private lateinit var binding: FragmentHabitsViewPagerBinding

class HabitsViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsViewPagerBinding.inflate(inflater,container,false)
        val viewPager: ViewPager2 = binding.habitsViewPager
        viewPager.adapter = ViewPagerHabitsAdapter(this)
        return binding.root
    }

    class ViewPagerHabitsAdapter(fr: HabitsViewPagerFragment): FragmentStateAdapter(fr) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            devDoSomeStuff{ Log.i(TAG,position.toString())}
            return when (position){
                0 -> HabitsFragment(HabitsType.ALL)
                1 -> HabitsFragment(HabitsType.BAD)
                2 -> HabitsFragment(HabitsType.GOOD)
                else -> HomeFragment()
            }
        }
    }
}