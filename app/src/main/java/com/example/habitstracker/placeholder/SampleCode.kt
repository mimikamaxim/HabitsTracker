package com.example.habitstracker

//Fragment code with sample code
/*
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.data.HabitItem
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.databinding.FragmentDetailHabitBinding

// TOODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailHabitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailHabitFragment : Fragment() {
    // TOODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentDetailHabitBinding

//    override fun onCreate(savedInstanceState: Bundle?) { //TOODO remove at final
//        super.onCreate(savedInstanceState)
//        Log.i("TAG1", arguments.toString())
//        arguments?.let {
//            param1 = it.getString("1")
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailHabitBinding.inflate(inflater, container, false)
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
        }
        binding.saveBtn.setOnClickListener {
            getHabitItem()?.let {
                if (idItem == null)
                    HabitItemsDB.addHabit(it)
                else
                    HabitItemsDB.updateHabit(idItem, it)
                findNavController().navigate(R.id.habitsFragment)
            }
        }
        return binding.root
    }

    private fun getHabitItem(): HabitItem? {
        val name: String = binding.habitTitleEditText.text.toString()
        if (name.isEmpty()) {
            makeToast("Введите название привычки")
            return null
        }
        val description: String = binding.habitDescriptionEditText.text.toString()
        if (description.isEmpty()) {
            makeToast("Введите описание привычки")
            return null
        }
        val priority: Int = binding.habitPrioritySpinner.selectedItemPosition
        val isGood: Boolean = binding.radioGroup.checkedRadioButtonId == R.id.is_good
        Log.i("TAG4", binding.radioGroup.checkedRadioButtonId.toString())
        var amountDone: Int = 0
        if (binding.habitDoneAmountEditText.text.toString().isEmpty()) {
            makeToast("Введите сколько раз сделано")
            return null
        } else amountDone = binding.habitDoneAmountEditText.text.toString().toInt()
        val period: String = binding.habitPeriodicEditText.text.toString()
        if (period.isEmpty()) {
            makeToast("Введите периодичность")
            return null
        }
        val color: Int = Color.GRAY

        return HabitItem(name, description, priority, isGood, amountDone, period, color)
    }

    private fun makeToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailHabitFragment.
         */
        // TOODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailHabitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun android.os.Bundle.getIntOrNull(key: String): Int? {
        return if (this.containsKey(key))
            this.getInt(key)
        else
            null
    }
}
 */
//_________________________________________________________________________________________________
//Drawer item listener
//        navView.setNavigationItemSelectedListener{
//            val id = it.itemId
//            Log.i("TAG",it.toString())
//            if (id == R.id.homeFragment)
//                navController.navigate(R.id.homeFragment)
//            if (id == R.id.aboutFragment)
//                navController.navigate(R.id.aboutFragment)
//            drawerLayout.close()
//            true
//        }

//_________________________________________________________________________________________________
//ViewModels
//private val viewModel: DetailHabitFragmentViewModel by viewModels { ViewModelFactory(arguments) }
////    private lateinit var viewModel: DetailHabitFragmentViewModel
//
//private class ViewModelFactory(private val arguments: Bundle?) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return DetailHabitFragmentViewModel(arguments?.getIntOrNull(KEY_ID)) as T
//    }
//}
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        viewModel = ViewModelProvider(this,object : ViewModelProvider.Factory {
////            override fun <T : ViewModel> create(modelClass: Class<T>): T {
////                return DetailHabitFragmentViewModel(arguments?.getIntOrNull(KEY_ID)) as T
////            }
////        }).get(DetailHabitFragmentViewModel::class.java)
////    }

//_________________________________________________________________________________________________
//private val _name: MutableLiveData<String> = MutableLiveData()
//    private val _description: MutableLiveData<String> = MutableLiveData()
//    private val _priority: MutableLiveData<Int> = MutableLiveData()
//    private val _isGood: MutableLiveData<Boolean> = MutableLiveData()
//    private val _amountDone: MutableLiveData<Int> = MutableLiveData()
//    private val _period: MutableLiveData<String> = MutableLiveData()
//    private val _color: MutableLiveData<Int> = MutableLiveData()