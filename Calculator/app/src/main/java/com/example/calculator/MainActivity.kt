package com.example.calculator

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonsId: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonsId = listOf(
            binding.b0,
            binding.b1,
            binding.b2,
            binding.b3,
            binding.b4,
            binding.b5,
            binding.b6,
            binding.b7,
            binding.b8,
            binding.b9,
            binding.bComma,
            binding.bBackspace,
            binding.bMinus,
            binding.bPlus,
            binding.bDivision,
            binding.bMultiplication
        )

        buttonsId.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button.id)

                val currentText = binding.expressionNumbers.text.toString()
                if (currentText.isEmpty()) {
                    binding.resultNumbers.setTextColor(Color.parseColor("#000000"))
                    binding.expressionNumbers.text = "0"
                    binding.resultNumbers.text = "0"
                }
            }
        }
    }

    private fun handleButtonClick(buttonId: Int) {

        when (buttonId) {
            binding.b0.id -> appendToDisplay("0")
            binding.b1.id -> appendToDisplay("1")
            binding.b2.id -> appendToDisplay("2")
            binding.b3.id -> appendToDisplay("3")
            binding.b4.id -> appendToDisplay("4")
            binding.b5.id -> appendToDisplay("5")
            binding.b6.id -> appendToDisplay("6")
            binding.b7.id -> appendToDisplay("7")
            binding.b8.id -> appendToDisplay("8")
            binding.b9.id -> appendToDisplay("9")
            binding.bComma.id -> appendToDisplay(".")
            binding.bBackspace.id -> handleBackspace()
            binding.bMinus.id -> appendToDisplay("-")
            binding.bPlus.id -> appendToDisplay("+")
            binding.bDivision.id -> appendToDisplay("÷")
            binding.bMultiplication.id -> appendToDisplay("*")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun appendToDisplay(value: String) {
        val currentText = binding.expressionNumbers.text.toString()
        if (currentText == "0") {
            binding.expressionNumbers.text = value
        } else {
            binding.expressionNumbers.text = currentText + value
        }
        performCalculation()
    }

    private fun handleBackspace() {
        val currentText = binding.expressionNumbers.text.toString()
        if (currentText.isNotEmpty()) {
            binding.expressionNumbers.text =
                currentText.substring(0, currentText.length - 1)
            performCalculation()
        }
    }


    private fun calculateExpression(expr: String): Double {
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()

        fun applyOperator() {
            val right = numbers.removeAt(numbers.size - 1)
            val left = numbers.removeAt(numbers.size - 1)
            val operator = operators.removeAt(operators.size - 1)

            val result = when (operator) {
                '+' -> left + right
                '-' -> left - right
                '×' -> left * right
                '÷' -> {
                    if (right == 0.0) {
                        throw ArithmeticException("Деление на ноль")
                    } else {
                        left / right
                    }
                }

                else -> throw IllegalArgumentException("Неизвестный оператор: $operator")
            }

            numbers.add(result)
        }

        var i = 0
        while (i < expr.length) {
            val c = expr[i]

            if (c.isDigit() || c == ',') {
                val numberBuilder = StringBuilder()
                while (i < expr.length && (expr[i].isDigit() || expr[i] == ',')) {
                    numberBuilder.append(expr[i])
                    i++
                }
                numbers.add(numberBuilder.toString().replace(',', '.').toDouble())
            } else if (c in listOf('+', '-', '*', '÷')) {
                while (operators.isNotEmpty() && precedence(operators.last()) >= precedence(c)) {
                    applyOperator()
                }
                operators.add(c)
                i++
            } else {
                throw IllegalArgumentException("Неизвестный символ: $c")
            }
        }

        while (operators.isNotEmpty()) {
            applyOperator()
        }

        return numbers[0]
    }

    private fun precedence(operator: Char): Int {
        return when (operator) {
            '+', '-' -> 1
            '×', '÷' -> 2
            else -> 0
        }
    }

    private fun performCalculation() {
        val displayValue = binding.expressionNumbers.text.toString()
        try {
            val result = calculateExpression(displayValue)
            binding.resultNumbers.setTextColor(Color.parseColor("#000000"))
            binding.resultNumbers.text = result.toString()
        } catch (e: ArithmeticException) {
            binding.resultNumbers.setTextColor(Color.parseColor("#FF0000"))
            binding.resultNumbers.setTextColor(Color.parseColor("#FF0000"))
            binding.resultNumbers.text = "На ноль делить нельзя"

        } catch (e: Exception) {
            binding.resultNumbers.setTextColor(Color.parseColor("#FF0000"))
            binding.resultNumbers.setTextColor(Color.parseColor("#FF0000"))
            binding.resultNumbers.text = "Ошибка"

        }
    }
}