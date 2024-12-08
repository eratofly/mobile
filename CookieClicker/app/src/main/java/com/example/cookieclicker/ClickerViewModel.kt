import androidx.lifecycle.ViewModel
import com.example.cookieclicker.Building
import com.example.cookieclicker.BuildingState
import com.example.cookieclicker.buildingList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.math.pow

data class State(
    var cookiesCount: Double = 0.0,
    val cookiesCountPerMinute: Double = 0.0,
    val time: Int = 0,
    val buildings: List<Building> = buildingList,
    val notificationSent: Boolean = false
)

class ClickerViewModel : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    private val _notificationFlow = MutableSharedFlow<String>()
    val notificationFlow: SharedFlow<String> get() = _notificationFlow.asSharedFlow()

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

            if (_state.value.cookiesCount == 2000.0 && !_state.value.notificationSent) {
                _notificationFlow.tryEmit("У вас ${_state.value.cookiesCount.toInt()} печенья, пора прикупить что-нибудь!")
                _state.value = _state.value.copy(notificationSent = true)
            }
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
        stopTimer()
    }
}