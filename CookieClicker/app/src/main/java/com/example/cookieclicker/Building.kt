package com.example.cookieclicker

val buildingList = listOf(
    Building(0,"Клик", 15, 15.0, 0.1, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/a/a0/Cursor_64px.png/revision/latest"),
    Building(1,"Бабуля", 100, 100.0,1.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/3/31/Grandma_new.png/revision/latest"),
    Building(2,"Ферма", 1100, 1100.0, 8.0, BuildingState.UNAVAILABLE, 0, "https://static.wikia.nocookie.net/cookieclicker/images/a/a7/Farm.png/revision/latest?cb=20130916094505&path-prefix=ru"),
    Building(3, "Шахта", 12000, 12000.0, 47.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/9/99/Mine_new.png/revision/latest?cb=20130916094544&path-prefix=ru"),
    Building(4,"Фабрика", 130000, 130000.0, 47.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/a/ae/Bank.png/revision/latest?cb=20150516151527&path-prefix=ru"),
    Building(5,"Банк", 1400000,1400000.0, 1400.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/a/a7/Farm.png/revision/latest?cb=20130916094505&path-prefix=ru"),
    Building(6,"Храм", 20000000,20000000.0, 7800.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/a/ae/Bank.png/revision/latest?cb=20150516151527&path-prefix=ru"),
    Building(7,"Башня Волшебника", 330000000, 330000000.0,44000.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/2/22/Wizardtower.png/revision/latest?cb=20150516151657&path-prefix=ru"),
    Building(8,"Космический корабль", 5100000000, 5100000000.0,260000.0, BuildingState.UNAVAILABLE,0, "https://static.wikia.nocookie.net/cookieclicker/images/2/22/Wizardtower.png/revision/latest?cb=20150516151657&path-prefix=ru"),
)

data class Building(
    val id: Int,
    val name: String,
    val basePrice: Long,
    val cost: Double,
    val profit: Double,
    val state: BuildingState,
    val quantity: Int,
    val imageURL: String,
)

enum class BuildingState {
    AVAILABLE,
    UNAVAILABLE
}
//добавить сюда флаг, и в зависимости от него делать карточку прозрачной
//игровой цикл ходит по списку строний и меняет каждый раз флаг