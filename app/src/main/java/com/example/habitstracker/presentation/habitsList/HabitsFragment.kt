package com.example.habitstracker.presentation.habitsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstracker.R
import com.example.habitstracker.TAG
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.data.HabitsListFilter
import com.example.habitstracker.data.HabitsListType
import com.example.habitstracker.databinding.FragmentHabitsListBinding
import com.example.habitstracker.devDoSomeStuff
import com.example.habitstracker.myLogger
import com.example.habitstracker.presentation.KEY_ID

interface ClickItemHandler {
    fun onClickItemHandler(view: View, id: Int)
}

class HabitsFragment(private val habitsListType: HabitsListType = HabitsListType.ALL) : Fragment() {
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
                HabitItemRecyclerViewAdapter(getRelevantList(habitsListType), clickItemHandler, context)
            layoutManager = LinearLayoutManager(context)
        }

        binding.newItem.setOnClickListener {
            findNavController().navigate(R.id.detailHabitFragment)
        }

        devDoSomeStuff {
            myLogger("list $habitsListType RUN")
        }

        return binding.root
    }

    private fun getRelevantList(habitsListType: HabitsListType): List<HabitItem> {
        return when (habitsListType) {
            HabitsListType.ALL -> HabitItemsDB.getHabitItemsList()
            HabitsListType.BAD -> HabitsListFilter.getBadHabitItemsList()
            HabitsListType.GOOD -> HabitsListFilter.getGoodHabitItemsList()
        }
    }
}