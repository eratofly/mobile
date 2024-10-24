package com.example.workshopcookie

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CookieViewModel: ViewModel() {
    var cookiesCount = 0
}

class FragmentCookie : Fragment(R.layout.fragment_cookie) {
    init {
        println("Cookies: init")
    }

    private val viewModel by lazy {
        val provider = ViewModelProvider(owner = this)
        provider.get(CookieViewModel::class.java)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.button)
        button.text = "Cookies: ${viewModel.cookiesCount}"
        button.setOnClickListener {
            viewModel.cookiesCount++
            button.text = "Cookies: ${viewModel.cookiesCount}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Cookies: onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("Cookies: onStart")
    }

    override fun onResume() {
        super.onResume()
        println("Cookies: onResume")
    }

    override fun onPause() {
        super.onPause()
        println("Cookies: onPause")
    }

    override fun onStop() {
        super.onStop()
        println("Cookies: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("Cookies: onDestroyView")
    }
}