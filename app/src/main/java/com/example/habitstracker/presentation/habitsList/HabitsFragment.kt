package com.example.habitstracker.presentation.habitsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.IInteraction
import com.example.habitstracker.HabitsApplication
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsListBinding
import com.example.habitstracker.myLogger
import com.example.habitstracker.presentation.detail.KEY_ID
import javax.inject.Inject

class HabitsFragment(private val habitsListType: HabitsListType = HabitsListType.ALL) : Fragment() {
    private lateinit var binding: FragmentHabitsListBinding
    private val clickItemHandler = object : ClickItemHandler {
        override fun onNavigateToDetails(id: Int) {
            val args = Bundle()
            args.putInt(KEY_ID, id)
            findNavController().navigate(R.id.detailHabitFragment, args)
        }

        override fun onDeleteItem(id: Int) {
            viewModel.deleteHabit(id)
        }

        override fun onAddDone(id: Int) {
            viewModel.addDone(id)
        }
    }

    @Inject
    lateinit var interaction: IInteraction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (requireActivity().application as HabitsApplication).appComponent
        appComponent.inject(this)
    }

    private val viewModel: HabitsListViewModel by viewModels {
        HabitsListViewModelFactory(
            habitsListType,
            interaction,
            requireContext()
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

        viewModel.toastSource.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}