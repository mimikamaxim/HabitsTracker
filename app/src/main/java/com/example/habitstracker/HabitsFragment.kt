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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.data.HabitsType
import com.example.habitstracker.databinding.FragmentHabitsListBinding
import com.example.habitstracker.placeholder.PlaceholderContent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface ClickItemHandler {
    fun onClickItemHandler(view: View, id: Int)
}

class HabitsFragment(private val habitsType: HabitsType = HabitsType.ALL) : Fragment() {
    private lateinit var binding: FragmentHabitsListBinding
    private val clickItemHandler = object : ClickItemHandler {
        override fun onClickItemHandler(view: View, id: Int) {
            devDoSomeStuff { Log.i(TAG, "item $id is ${HabitItemsDB.getHabit(id)}") }
            val args: Bundle = Bundle()
            args.putInt(KEY_ID, id)
            findNavController().navigate(R.id.detailHabitFragment, args)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        with(binding.list) {
            adapter =
                MyItemRecyclerViewAdapter(getRelevantList(habitsType), clickItemHandler)
            layoutManager = LinearLayoutManager(context)
        }
        binding.newItem.setOnClickListener {
            findNavController().navigate(HabitsFragmentDirections.actionHabitsFragmentToDetailHabitFragment())
        }
        devDoSomeStuff {
            Log.i(TAG, "list $habitsType RUN")
        }
        return binding.root
    }

    private fun getRelevantList(habitsType: HabitsType): List<HabitItem> {
        return when (habitsType) {
            HabitsType.ALL -> HabitItemsDB.getHabitItemsList()
            HabitsType.BAD -> HabitItemsDB.getBadHabitItemsList()
            HabitsType.GOOD -> HabitItemsDB.getGoodHabitItemsList()
        }
    }
}