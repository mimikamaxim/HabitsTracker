package com.example.habitstracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.data.HabitsType
import com.example.habitstracker.databinding.FragmentHabitsListBinding

interface ClickItemHandler {
    fun onClickItemHandler(view: View, id: Int)
}

class HabitsFragment(private val habitsType: HabitsType = HabitsType.ALL) : Fragment() {
    private lateinit var binding: FragmentHabitsListBinding
    private val clickItemHandler = object : ClickItemHandler {
        override fun onClickItemHandler(view: View, id: Int) {
            devDoSomeStuff { Log.i(TAG, "item $id is ${HabitItemsDB.getHabit(id)}") }
            val args = Bundle()
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
                MyItemRecyclerViewAdapter(getRelevantList(habitsType), clickItemHandler, context)
            layoutManager = LinearLayoutManager(context)
        }

        binding.newItem.setOnClickListener {
            findNavController().navigate(R.id.detailHabitFragment)
        }

        devDoSomeStuff {
            myLogger("list $habitsType RUN")
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