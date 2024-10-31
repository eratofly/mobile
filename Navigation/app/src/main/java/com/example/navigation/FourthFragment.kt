package com.example.navigation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentFirstBinding
import com.example.navigation.databinding.FragmentFourthBinding
import java.util.Calendar

class FourthFragment : Fragment(R.layout.fragment_fourth) {
    private lateinit var binding: FragmentFourthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFourthBinding.bind(view)

        binding.nextB1.setOnClickListener {
            findNavController()
                .navigate(R.id.action_fourthFragment_to_fifthFragment)
        }

        binding.back.setOnClickListener {
            findNavController()
                .popBackStack()
        }
    }

//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(
//            ,
//            year, month, day
//        )
//
//        datePickerDialog.show()
//    }
}
