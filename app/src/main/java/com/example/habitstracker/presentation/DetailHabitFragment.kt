package com.example.habitstracker.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.R
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.databinding.FragmentDetailHabitBinding

const val KEY_ID = "key_id"

class DetailHabitFragment : Fragment() {
    private lateinit var binding: FragmentDetailHabitBinding
    private var colorItem = Color.GRAY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailHabitBinding.inflate(inflater, container, false)//TODO q last?

        val idItem = arguments?.getIntOrNull(KEY_ID)
        idItem?.let {
            val habit = HabitItemsDB.getHabit(idItem)
            with(binding) {
                habitTitleEditText.setText(habit.name)
                habitDescriptionEditText.setText(habit.description)
                habitPrioritySpinner.setSelection(habit.priority)
                if (habit.isGood) radioGroup.check(R.id.is_good) else radioGroup.check(R.id.is_bad)
                habitDoneAmountEditText.setText(habit.amountDone.toString())
                habitPeriodicEditText.setText(habit.period)
            }
            colorItem = habit.color
        }

        binding.saveBtn.setOnClickListener {
            getHabitItem()?.let {
                if (idItem == null)
                    HabitItemsDB.addHabit(it)
                else
                    HabitItemsDB.updateHabit(idItem, it)
                findNavController().popBackStack(R.id.habitsViewPagerFragment, false)
            }
        }

        return binding.root
    }

    private fun getHabitItem(): HabitItem? {
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
        var amountDone: Int = 0
        if (binding.habitDoneAmountEditText.text.toString().isEmpty()) {
            makeToast(getString(R.string.no_habit_done_amount_err_message))
            return null
        } else amountDone = binding.habitDoneAmountEditText.text.toString().toInt()

        val period: String = binding.habitPeriodicEditText.text.toString()
        if (period.isEmpty()) {
            makeToast(getString(R.string.no_habit_periodic_err_message))
            return null
        }

        val color: Int = colorItem //TODO make color picker

        return HabitItem(name, description, priority, isGood, amountDone, period, color)
    }

    private fun makeToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    private fun android.os.Bundle.getIntOrNull(key: String): Int? {
        return if (this.containsKey(key))
            this.getInt(key)
        else
            null
    }
}
