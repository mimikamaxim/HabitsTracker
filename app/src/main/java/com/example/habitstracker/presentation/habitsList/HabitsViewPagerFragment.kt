package com.example.habitstracker.presentation.habitsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.habitstracker.R
import com.example.habitstracker.TAG
import com.example.habitstracker.databinding.FragmentHabitsViewPagerBinding
import com.example.habitstracker.devDoSomeStuff
import com.example.habitstracker.presentation.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: FragmentHabitsViewPagerBinding

class HabitsViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsViewPagerBinding.inflate(inflater,container,false)

        val viewPager: ViewPager2 = binding.habitsViewPager
        viewPager.adapter = ViewPagerHabitsAdapter(this)

        val tabLayout = binding.habitsViewPagerTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.all_text)
                1 -> getString(R.string.bad_text)
                2 -> getString(R.string.good_text)
                else -> "none"
            }
        }.attach()

        return binding.root
    }

    class ViewPagerHabitsAdapter(fr: HabitsViewPagerFragment): FragmentStateAdapter(fr) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            devDoSomeStuff{ Log.i(TAG,position.toString())}

            return when (position){
                0 -> HabitsFragment(HabitsListType.ALL)
                1 -> HabitsFragment(HabitsListType.BAD)
                2 -> HabitsFragment(HabitsListType.GOOD)
                else -> HomeFragment()
            }
        }
    }
}