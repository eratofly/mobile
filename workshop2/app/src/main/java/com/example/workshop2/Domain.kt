data class FoodItem(
    val name: String,
    val description: String,
    val price: Int,
    val type: FoodType
)

enum class FoodType {
    BURGER,
    PIZZA
}

enum class Filter {
    ALL,
    BURGERS,
    PIZZA
}