package com.example.habitstracker

import android.os.Bundle
import android.util.Log
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

interface ClickItemHandler {
    fun onClickItemHandler(view:View, id: Int)
}
/**
 * A fragment representing a list of Items.
 */
class HabitsFragment : Fragment() {

    private lateinit var binding: FragmentHabitsListBinding
    private val clickItemHandler = object : ClickItemHandler {
        override fun onClickItemHandler (view: View, id: Int){
            Log.i("TAGG", "item $id is ${HabitItemsDB.getHabit(id)}")
            val args: Bundle = Bundle()
            args.putInt(KEY_ID,id)
            findNavController().navigate(R.id.detailHabitFragment, args)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitsListBinding.inflate(inflater,container,false)
        devDoSomeStuff {HabitItemsDB.fillDBsample()}
        with(binding.list){
            layoutManager = LinearLayoutManager(context)
            adapter = MyItemRecyclerViewAdapter(HabitItemsDB.getHabitItemsList(),clickItemHandler)
        }
        binding.newItem.setOnClickListener {
//            val args: Bundle = Bundle()
//            args.putString("2","DADADA")
//            args.putString("1","YA YA YA")
//            findNavController().navigate(R.id.detailHabitFragment,args) //TODO remove in final
//            DetailHabitFragment.newInstance("asdf","fdas")
            findNavController().navigate(HabitsFragmentDirections.actionHabitsFragmentToDetailHabitFragment())
//            findNavController().navigate(R.id.detailHabitFragment)
        }
        devDoSomeStuff {Log.i("TAG","RUN")}
        return binding.root
    }
}