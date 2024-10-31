package com.example.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var binding: FragmentFirstBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)


        binding.nextA1.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val surname = binding.editTextSurname.text.toString()

            val arguments = Bundle().apply {
                putString(UserData.NAME, name)
                putString(UserData.SURNAME, surname)
            }

            if (name.isNotEmpty() && surname.isNotEmpty()) {
                findNavController()
                    .navigate(R.id.action_firstFragment_to_secondFragment, arguments)
            }

        }

        binding.nextB.setOnClickListener {
            findNavController()
                .navigate(R.id.action_firstFragment_to_fourthFragment)
        }
    }
}