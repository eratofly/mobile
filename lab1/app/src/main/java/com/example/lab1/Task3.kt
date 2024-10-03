package com.example.lab1

import kotlin.math.pow

fun sum(a: Int, b: Int) {
    println("Сумма чисел $a и $b равна ${a + b}")
}

fun subtract(a: Int, b: Int) {
    //val sub = if (b < 0) (a + b) else (a - b)
    println("Разность чисел $a и $b равна ${a - b}")
}

fun divide(a: Int, b: Int) {
    if (b == 0) {
        println("Делить на 0 нельзя!")
    }
    println("Частное от деления числа $a на $b равно ${(a.toDouble() / b.toDouble())}")
}

fun multiple(a: Int, b: Int) {
    println("Произведение чисел $a и $b равно ${a * b}")
}

fun pow(a: Int, b: Int) {
    println("$a в степени $b равно ${(a.toDouble().pow(b.toDouble())).toInt()}")
}

fun max(list: List<Int>) {
    println("Максимальное число из $list равно ${list.max()}")
}

fun min(list: List<Int>) {
    println("Минимальное число из $list равно ${list.min()}")
}

fun printList(list: List<Int>) {
    println(list.sorted().joinToString (separator = " < " ))
}

fun printAbout(name: String, age: Int) {
    println("Привет, меня зовут ${name}, мне ${age} лет, через 5 лет мне будет ${age + 5} лет.")
}

 fun main() {
    while (true) {
        val input = readlnOrNull()?.split(" ")
        when (input?.get(0)) {
            "sum" -> sum(input[1].toInt(), input[2].toInt())
            "subtract" -> subtract(input[1].toInt(), input[2].toInt())
            "divide" -> divide(input[1].toInt(), input[2].toInt())
            "multiple" -> multiple(input[1].toInt(), input[2].toInt())
            "pow" -> pow(input[1].toInt(), input[2].toInt())
            "max" -> max(input.drop(1).map { it.toInt() })
            "min" -> min(input.drop(1).map { it.toInt() })
            "print_list" -> printList(input.drop(1).map { it.toInt() })
            "print_about" -> printAbout(input[1], input[2].toInt())
            "exit" -> return
            else -> println("Введена неверная команда, повторите еще раз")
        }
    }
}