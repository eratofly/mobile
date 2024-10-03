package com.example.lab1

sealed interface Vehicle

//он data object, потому что у него будет только один экземпляр
data object Scooter : Vehicle

data class Bicycle(
    val brand: String,
    val frontWheel: Wheel,
    val backWheel: Wheel,
    val frame: Frame
) : Vehicle

sealed interface Car : Vehicle

data class GasCar(
    val engine: Engine,
    val wheels: List<Wheel>,
    val steering: Steering
) : Car

data class ElectricCar(
    val electricMotor: ElectricMotor,
    val wheels: List<Wheel>,
    val autopilot: Autopilot
) : Car

enum class FrameMaterial {
    Steel, Aluminum, Carbon
}

data class Frame(
    val material: FrameMaterial
)

data class Wheel(
    val diameter: Double,
    val brand: String,
    val disc: DiscType
)

enum class DiscType {
    Cast, Forged, Stamped
}

data class Engine(
    val volume: Double,
    val fuelType: FuelType
)

enum class FuelType {
    Diesel, Gas92, Gas95, Gas98, Gas100
}

data class Steering(
    val type: String
)

data class ElectricMotor(
    val power: Double
)

sealed interface Autopilot {
    data object Yandex : Autopilot
    data object Tesla : Autopilot
}

fun main() {

}

