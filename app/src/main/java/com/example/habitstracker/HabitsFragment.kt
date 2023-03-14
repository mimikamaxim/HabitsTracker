package com.example.habitstracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.databinding.FragmentHabitsListBinding
import com.example.habitstracker.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class HabitsFragment : Fragment() {

    private lateinit var binding: FragmentHabitsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitsListBinding.inflate(inflater,container,false)
        val dataBase = HabitItemsDB()
        dataBase.fillDBsample()
        with(binding.list){
            layoutManager = LinearLayoutManager(context)
            adapter = MyItemRecyclerViewAdapter(dataBase.getHabitItemsList())
        }
        binding.newItem.setOnClickListener {
//            val args: Bundle = Bundle()
//            args.putString("1","YA YA YA")
//            findNavController().navigate(R.id.detailHabitFragment,args)
//            DetailHabitFragment.newInstance("asdf","fdas")
            findNavController().navigate(HabitsFragmentDirections.actionHabitsFragmentToDetailHabitFragment())
        }
        return binding.root
    }
}