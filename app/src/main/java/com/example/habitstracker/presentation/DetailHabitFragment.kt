package com.example.habitstracker.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.HabitsApplication
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentDetailHabitBinding
import com.example.habitstracker.domain.Interaction
import com.example.habitstracker.presentation.HabitItemPresentationModel.Companion.NoId
import javax.inject.Inject


const val KEY_ID = "key_id"

class DetailHabitFragment : Fragment() {
    private lateinit var binding: FragmentDetailHabitBinding
    private var colorItem = Color.GRAY

    @Inject
    lateinit var interaction: Interaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (requireActivity().application as HabitsApplication).appComponent
        appComponent.inject(this)
//        HabitsApplication.appComponent.inject(this)//todo mark
    }

    private val viewModel: DetailHabitFragmentViewModel by viewModels {
        DetailHabitFragmentViewModel.ViewModelFactory(
            arguments,
            interaction
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailHabitBinding.inflate(inflater, container, false)

        binding.saveBtn.setOnClickListener {
            getHabitItem()?.let {
                viewModel.saveHabit(it)
                findNavController().popBackStack(R.id.habitsViewPagerFragment, false)
            }
        }

        viewModel.habit.observe(viewLifecycleOwner) {
            it?.let {
                with(binding) {
                    habitTitleEditText.setText(it.name)
                    habitDescriptionEditText.setText(it.description)
                    habitPrioritySpinner.setSelection(it.priority)
                    if (it.isGood) radioGroup.check(R.id.is_good) else radioGroup.check(R.id.is_bad)
                    habitDoneAmountEditText.setText(it.periodInDays.toString())
                    habitPeriodicEditText.setText(it.frequencyOfAllowedExecutions)
                }
                colorItem = it.color
            }
        }

        return binding.root
    }

    private fun getHabitItem(): HabitItemPresentationModel? {
        val name: String = binding.habitTitleEditText.text.toString()
        if (name.isEmpty()) {
            makeToast(getString(R.string.no_habit_title_err_message))
            return null
        }

        val description: String = binding.habitDescriptionEditText.text.toString()
        if (description.isEmpty()) {
            makeToast(getString(R.string.no_habit_description_err_message))
            return null
        }

        val priority: Int = binding.habitPrioritySpinner.selectedItemPosition
        val isGood: Boolean = binding.radioGroup.checkedRadioButtonId == R.id.is_good
        val amountDone: Int = if (binding.habitDoneAmountEditText.text.toString().isEmpty()) {
            makeToast(getString(R.string.no_habit_done_amount_err_message))
            return null
        } else binding.habitDoneAmountEditText.text.toString().toInt()

        val period: String = binding.habitPeriodicEditText.text.toString()
        if (period.isEmpty()) {
            makeToast(getString(R.string.no_habit_periodic_err_message))
            return null
        }

        val color: Int = colorItem //TODO make color picker

        return HabitItemPresentationModel(
            name = name,
            description = description,
            priority = priority,
            isGood = isGood,
            color = color,
            frequencyOfAllowedExecutions = period.toInt(),
            periodInDays = amountDone,
            doneDates = listOf(),
            initialDate = System.currentTimeMillis(),
            id = viewModel.id ?: NoId
        )
    }

    private fun makeToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

}
