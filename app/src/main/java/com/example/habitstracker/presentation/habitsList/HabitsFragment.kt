package com.example.habitstracker.presentation.habitsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstracker.R
import com.example.habitstracker.TAG
import com.example.habitstracker.data.HabitItemsDB
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
    private val viewModel: ListViewModel by viewModels { ViewModelFactory(habitsListType) }

    private class ViewModelFactory(private val habitsListType: HabitsListType) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListViewModel(habitsListType) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater, container, false)

        viewModel.list.observe(viewLifecycleOwner){
            with(binding.list) {
                adapter =
                    HabitItemRecyclerViewAdapter(
                        it,
                        clickItemHandler,
                        context
                    )
                layoutManager = LinearLayoutManager(context)
            }
        }

        binding.newItem.setOnClickListener {
            findNavController().navigate(R.id.detailHabitFragment)
        }

        binding.bottomSheet.filterNameTextInputEditText.doOnTextChanged { text, start, before, count ->
            if (binding.bottomSheet.filterNameTextInputEditText.isFocused) {
                viewModel.findByHabitName(text.toString())
                binding.bottomSheet.filterDescriptionTextInputEditText.setText("")
            }
            myLogger(text.toString())
        }

        binding.bottomSheet.filterDescriptionTextInputEditText.doOnTextChanged { text, start, before, count ->
            if (binding.bottomSheet.filterDescriptionTextInputEditText.isFocused) {
                viewModel.findInHabitDescription(text.toString())
                binding.bottomSheet.filterNameTextInputEditText.setText("")
            }
        }

        binding.bottomSheet.sortByNameBtn.setOnClickListener { viewModel.sortByHabitName() }

        binding.bottomSheet.sortByDefaultBtn.setOnClickListener { viewModel.sortByHabitId() }

        return binding.root
    }
}