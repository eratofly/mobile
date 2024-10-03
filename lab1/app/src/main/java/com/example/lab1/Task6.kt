package com.example.lab1

data class Student(
    val id: Int,
    var name: String,
    var age: Int,
    var points: Int
)

class StudentManager {
    private val students = mutableListOf<Student>()
    private var studentId = 1

    fun addStudent(info: String) {
        val parts = info.split(" ")
        if (parts.size != 3) {
            println("Неправильно введены данные")
            return
        }
        val name = parts[0]
        val age = parts[1].toIntOrNull() ?: return
        val points = parts[2].toIntOrNull() ?: return

        val student = Student(studentId++, name, age, points)
        students.add(student)
    }

    fun removeStudent(id: Int) {
        students.removeIf { it.id == id }
    }

    fun updatePoints(id: Int, newPoints: Int) {
        students.find { it.id == id }?.points = newPoints
    }

    fun renameStudent(id: Int, newName: String) {
        students.find { it.id == id }?.name = newName
    }

    fun printSortedByPoints() {
        students.sortedByDescending { it.points }
            .forEach {
                println("${it.id} ${it.name} (${it.age} лет) - ${it.points} балла")
        }
    }

    fun printSortedByNames() {
        students.sortedBy { it.name }.forEach {
            println("${it.id} ${it.name} (${it.age} лет) - ${it.points} балла")
        }
    }
}

fun main() {
    val studentManager = StudentManager()
    println("Введите студентов в формате: <имя> <возраст> <балл>, <имя> <возраст> <балл>, ...")
    val input = readlnOrNull()
    input?.split(", ")?.forEach { studentManager.addStudent(it) }
    println("Имена введены")

    while (true) {
        println("Введите команду:")
        val command = readlnOrNull() ?: break
        val parts = command.split(" ")

        when (parts[0]) {
            "add" -> {
                studentManager.addStudent(parts.drop(1).joinToString(" "))
            }
            "remove" -> {
                if (parts.size > 1) {
                    val id = parts[1].toIntOrNull()
                    if (id != null) studentManager.removeStudent(id)
                }
            }
            "update_points" -> {
                if (parts.size > 2) {
                    val id = parts[1].toIntOrNull()
                    val newPoints = parts[2].toIntOrNull()
                    if (id != null && newPoints != null) studentManager.updatePoints(id, newPoints)
                }
            }
            "rename" -> {
                if (parts.size > 2) {
                    val id = parts[1].toIntOrNull()
                    val newName = parts.drop(2).joinToString(" ")
                    if (id != null) studentManager.renameStudent(id, newName)
                }
            }
            "print_sort_by_points" -> {
                studentManager.printSortedByPoints()
            }
            "print_sort_by_names" -> {
                studentManager.printSortedByNames()
            }
            "exit" -> break
            else -> println("Неизвестная команда")
        }
    }
}
