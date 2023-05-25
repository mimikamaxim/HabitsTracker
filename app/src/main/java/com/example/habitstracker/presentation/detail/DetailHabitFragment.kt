package com.example.habitstracker.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.IInteraction
import com.example.habitstracker.HabitsApplication
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentDetailHabitBinding
import javax.inject.Inject


const val KEY_ID = "key_id"

class DetailHabitFragment : Fragment() {
    private lateinit var binding: FragmentDetailHabitBinding

    @Inject
    lateinit var interaction: IInteraction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (requireActivity().application as HabitsApplication).appComponent
        appComponent.inject(this)
    }

    private val viewModel: DetailHabitFragmentViewModel by viewModels {
        DetailViewModelFactory(
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
            viewModel.setPriority(binding.habitPrioritySpinner.selectedItemPosition)
            viewModel.setIsGood(binding.radioGroup.checkedRadioButtonId == R.id.is_good)
            viewModel.saveHabit()
            findNavController().popBackStack(R.id.habitsViewPagerFragment, false)
        }

        viewModel.habit.observe(viewLifecycleOwner) {
            it?.let {
                with(binding) {
                    habitTitleEditText.setText(it.name)
                    habitDescriptionEditText.setText(it.description)
                    habitPrioritySpinner.setSelection(it.priority)
                    if (it.isGood) radioGroup.check(R.id.is_good) else radioGroup.check(R.id.is_bad)
                    habitAllowedTimesEditText.setText(it.frequencyOfAllowedExecutions.toString())
                    periodDaysEditText.setText(it.periodInDays.toString())
                    habitTotalCompleteTimesEditText.setText(it.totalCompleteTimes.toString())
                }
            }
        }

        binding.habitTitleEditText.doOnTextChanged { text, start, before, count ->
            if (text == "") makeToast(getString(R.string.no_habit_title_err_message))
            else viewModel.setName(text.toString())
        }

        binding.habitDescriptionEditText.doOnTextChanged { text, start, before, count ->
            if (text == "") makeToast(getString(R.string.no_habit_description_err_message))
            else viewModel.setDescription(text.toString())
        }

        binding.habitTotalCompleteTimesEditText.doOnTextChanged { text, start, before, count ->
            if (text == "") makeToast(getString(R.string.no_habit_total_complete_times_err_message))
            else viewModel.setTotalCompleteTimes(text.toString())
        }

        binding.habitAllowedTimesEditText.doOnTextChanged { text, start, before, count ->
            if (text == "") makeToast(getString(R.string.no_habit_allowed_executions_err_message))
            else viewModel.setFreqExec(text.toString())
        }

        binding.periodDaysEditText.doOnTextChanged { text, start, before, count ->
            if (text == "") makeToast(getString(R.string.no_habit_days_period_err_message))
            else viewModel.setPeriod(text.toString())
        }

        return binding.root
    }

    private fun makeToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

}
