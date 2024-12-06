package com.example.cookieclicker

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.math.pow

data class State(
    var cookiesCount: Double = 0.0,
    var cookiesCountPerMinute: Double = 0.0,
    var time: Int = 0,
    val buildings: List<Building> = buildingList
)

class ClickerViewModel : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    private var timer: Timer? = null
    private var isRunning = false

    fun startTimer() {
        if (isRunning) return
        isRunning = true

        timer = fixedRateTimer("stopwatch", initialDelay = 1000, period = 1000) {
            _state.value = _state.value.copy(time = _state.value.time + 1)

            if (_state.value.time != 0) {
                _state.value = _state.value.copy(
                    cookiesCountPerMinute = _state.value.cookiesCount / _state.value.time * 60
                )
            }

            var profit: Double = 0.0
            _state.value.buildings.forEach {
                profit += it.profit * it.quantity
            }
            _state.value = _state.value.copy(
                cookiesCount = _state.value.cookiesCount + profit,
                buildings = updateBuildingsAvailable(_state.value.buildings)
            )
        }
    }

    fun stopTimer() {
        isRunning = false
        timer?.cancel()
    }

    fun incrementCookies() {
        _state.value = _state.value.copy(cookiesCount = _state.value.cookiesCount + 1)
    }

    fun updateBuildingsAvailable(buildings: List<Building>): List<Building> {
        var newBuildingList: List<Building> = buildings
        newBuildingList = newBuildingList.map { item ->
            if (item.cost <= _state.value.cookiesCount) {
                item.copy(state = BuildingState.AVAILABLE)
            }
            else if (item.cost > _state.value.cookiesCount) {
                item.copy(state = BuildingState.UNAVAILABLE)
            }
            else {
                item
            }
        }
        return newBuildingList
    }

    fun buyBuilding(building: Building) {
        var newBuildingList: List<Building> = _state.value.buildings
        newBuildingList = newBuildingList.map { item ->
            if (item.id == building.id) {
                item.copy(
                    quantity = building.quantity + 1,
                    cost = building.basePrice * 1.15.pow(building.quantity - 1) / 0.15
                )
            } else {
                item
            }
        }

        _state.value = _state.value.copy(
            buildings =  updateBuildingsAvailable(newBuildingList),
            cookiesCount = _state.value.cookiesCount - building.cost.toInt()
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}

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
            viewModel.state.collect { state ->
                cookiesCountText.text = "Печенья: ${String.format("%.1f", state.cookiesCount)}"

                val minutes = (state.time % 3600) / 60
                val sec = state.time % 60
                binding.cookieFrame.time.text = "Время: ${String.format("%02d:%02d", minutes, sec)}"
                binding.cookieFrame.cookiesPerSecond.text = "В секунду: ${String.format("%.1f", state.cookiesCountPerMinute / 60)}"
                binding.cookieFrame.cookiesPerMinute.text =
                    "Средняя скорость: ${String.format("%.3f", state.cookiesCountPerMinute)} п/мин"


                if (state.cookiesCount % 2000 == 0.0 && state.cookiesCount != 0.0) {
                    Toast.makeText(requireContext(), "У вас ${state.cookiesCount} печенья, пора прикупить что-нибудь!", Toast.LENGTH_SHORT).show()
                }
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
}