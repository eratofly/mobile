package com.example.navigation

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)


        val name = arguments?.getString(UserData.NAME)
        val surname = arguments?.getString(UserData.SURNAME)
        binding.name.text = name
        binding.surname.text = surname

        val buttonBirthday = binding.buttonBirthday


        buttonBirthday.setOnClickListener {
            showDatePickerDialog()
        }

        binding.nextA2.setOnClickListener {
            val arguments = Bundle().apply {
                putString(UserData.NAME, binding.name.text.toString())
                putString(UserData.SURNAME, binding.surname.text.toString())
                putString(UserData.BIRTH_DATE, binding.birthDate.text.toString())
            }
            findNavController()
                .navigate(R.id.action_secondFragment_to_thirdFragment, arguments)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = android.icu.util.Calendar.getInstance()
        val year = calendar.get(android.icu.util.Calendar.YEAR)
        val month = calendar.get(android.icu.util.Calendar.MONTH)
        val day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
            requireContext(),
            object : OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
                    val selectedDate: String = selectedDay.toString() + "/" + (selectedMonth + 1).toString() + "/" + selectedYear.toString()
                    binding.birthDate.text = selectedDate
                }
            },
            year,
            month,
            day

        )

        datePickerDialog.show()
    }
}
