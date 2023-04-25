package com.example.habitstracker.presentation.habitsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsListBinding
import com.example.habitstracker.myLogger
import com.example.habitstracker.presentation.KEY_ID

class HabitsFragment(private val habitsListType: HabitsListType = HabitsListType.ALL) : Fragment() {
    private lateinit var binding: FragmentHabitsListBinding
    private val clickItemHandler = object : ClickItemHandler {
        override fun onClickItemHandler(view: View, id: Int) {
            val args = Bundle()
            args.putInt(KEY_ID, id)
            findNavController().navigate(R.id.detailHabitFragment, args)
        }
    }
    private val viewModel: HabitsListViewModel by viewModels {
        HabitsListViewModelFactory(
            habitsListType
        )
    }

    var adapter: HabitItemRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        adapter =
            HabitItemRecyclerViewAdapter(
                viewModel.list.value ?: emptyList(),
                clickItemHandler,
                requireContext()
            )

        with(binding.list) {
            adapter = this@HabitsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            adapter?.updateList(it)
        }
//        viewModel.list.observe(viewLifecycleOwner) {
//            with(binding.list) {
//                adapter =
//                    HabitItemRecyclerViewAdapter(
//                        viewModel.list.value ?: emptyList(),
//                        clickItemHandler,
//                        context
//                    )
//                layoutManager = LinearLayoutManager(context)
//            }
//        }

        binding.newItem.setOnClickListener {
            findNavController().navigate(R.id.detailHabitFragment)
        }

        binding.bottomSheet.filterNameTextInputEditText.doOnTextChanged { text, _, _, _ ->
            if (binding.bottomSheet.filterNameTextInputEditText.isFocused) {
                viewModel.findByHabitName(text.toString())
                binding.bottomSheet.filterDescriptionTextInputEditText.setText("")
            }
            myLogger(text.toString())
        }

        binding.bottomSheet.filterDescriptionTextInputEditText.doOnTextChanged { text, _, _, _ ->
            if (binding.bottomSheet.filterDescriptionTextInputEditText.isFocused) {
                viewModel.findInHabitDescription(text.toString())
                binding.bottomSheet.filterNameTextInputEditText.setText("")
            }
        }

        binding.bottomSheet.sortByNameBtn.setOnClickListener { viewModel.sortByHabitName() }

        binding.bottomSheet.sortByDefaultBtn.setOnClickListener { viewModel.sortByHabitId() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}