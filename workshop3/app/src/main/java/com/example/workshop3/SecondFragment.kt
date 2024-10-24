package com.example.workshop3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workshop3.databinding.ActivityMainBinding
import com.example.workshop3.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

//Medium Phone API 35 is already running. If that is not the case, delete C:\Users\Daria Okhotnikova\.android\avd\Medium_Phone_API_35.avd\*.lock and try again.