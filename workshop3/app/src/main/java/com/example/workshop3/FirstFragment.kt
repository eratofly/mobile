package com.example.workshop3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workshop3.databinding.FragmentListBinding

class FirstFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        binding.openSecondFragmentButton.setOnClickListener {
            findNavController()
                .navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }
}