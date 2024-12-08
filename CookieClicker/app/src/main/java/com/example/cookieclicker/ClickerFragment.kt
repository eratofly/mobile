package com.example.cookieclicker

import ClickerViewModel
import State
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookieclicker.databinding.ClickerFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.math.pow

class ClickerFragment : Fragment(R.layout.clicker_fragment) {

    private val viewModel: ClickerViewModel by activityViewModels()

    private lateinit var buildingAdapter: BuildingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ClickerFragmentBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)

        buildingAdapter = BuildingAdapter(viewModel)

        val button = binding.cookieFrame.cookieButton
        val cookiesCountText = binding.cookieFrame.cookiesCount

        viewModel.startTimer()

        // RENDER

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notificationFlow.collect { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                cookiesCountText.text = "Печенья: ${String.format("%.1f", state.cookiesCount)}"
                showTimeData(state, binding)
                buildingAdapter.submitList(state.buildings)
            }
        }

        button.setOnClickListener {
            viewModel.incrementCookies()
            button.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(100)
                .withEndAction {
                    button.animate().scaleX(1f).scaleY(1f).duration = 100
                }
        }


        binding.shopFrame.listBuilding.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = buildingAdapter
        }

//        buildingAdapter.submitList(buildingList)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_clicker -> {
                    if (binding.cookieFrame.cookie.visibility == View.INVISIBLE) {
                        binding.cookieFrame.cookie.visibility = View.VISIBLE
                        binding.shopFrame.shop.visibility = View.INVISIBLE
                    }
                    true
                }

                R.id.nav_shop -> {
                    if (binding.shopFrame.shop.visibility == View.INVISIBLE) {
                        binding.shopFrame.shop.visibility = View.VISIBLE
                        binding.cookieFrame.cookie.visibility = View.INVISIBLE
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun showTimeData(state: State, binding: ClickerFragmentBinding) {
        val minutes = (state.time % 3600) / 60
        val sec = state.time % 60
        binding.cookieFrame.time.text = "Время: ${String.format("%02d:%02d", minutes, sec)}"
        binding.cookieFrame.cookiesPerSecond.text = "В секунду: ${String.format("%.1f", state.cookiesCountPerMinute / 60)}"
        binding.cookieFrame.cookiesPerMinute.text =
            "Средняя скорость: ${String.format("%.3f", state.cookiesCountPerMinute)} п/мин"
    }
}