package com.example.calculator

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.calculator.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class State(
    var expression: String = "0",
    var result: String = "0",
    var error: String = "0"
)

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

//    private val _error = MutableSharedFlow<String>(replay = 1)
//    val error = _error.asSharedFlow()

    fun appendToExpression(value: String) {
        val newExpression = if (_state.value.expression == "0") {
            value
        } else {
            _state.value.expression + value
        }
        _state.value = _state.value.copy(expression = newExpression, error = "")
        calculateResult()
    }

    fun removeLastCharacter() {
        val currentExpression = _state.value.expression
        val newExpression = if (currentExpression.length > 1) {
            currentExpression.dropLast(1)
        } else {
            "0"
        }
        _state.value = _state.value.copy(expression = newExpression, error = "")
        calculateResult()
    }

    private fun calculateResult() {
        val expression = _state.value.expression
        try {
            val result = calculateExpression(expression)
            _state.value = _state.value.copy(result = result.toString(), error = "")
        } catch (e: ArithmeticException) {
            _state.value = _state.value.copy(error = "На ноль делить нельзя", result = "")
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = "Ошибка", result = "")
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
                '*' -> left * right
                '÷' -> {
                    if (right == 0.0) throw ArithmeticException("Деление на ноль")
                    left / right
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

    private fun precedence(operator: Char): Int = when (operator) {
        '+', '-' -> 1
        '*', '÷' -> 2
        else -> 0
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonsId: List<Button>
    private val viewModel by lazy {
        ViewModelProvider(this)[CalculatorViewModel::class.java]
    }

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
            button.setOnClickListener { handleButtonClick(button.id) }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.expressionNumbers.text = state.expression
                if (state.error.isNotEmpty()) {
                    binding.resultNumbers.text = state.error
                    binding.resultNumbers.setTextColor(Color.parseColor("#FF0000"))
                } else {
                    binding.resultNumbers.text = state.result
                    binding.resultNumbers.setTextColor(Color.parseColor("#000000"))
                }
            }
        }
    }

    private fun handleButtonClick(buttonId: Int) {
        when (buttonId) {
            binding.b0.id -> viewModel.appendToExpression("0")
            binding.b1.id -> viewModel.appendToExpression("1")
            binding.b2.id -> viewModel.appendToExpression("2")
            binding.b3.id -> viewModel.appendToExpression("3")
            binding.b4.id -> viewModel.appendToExpression("4")
            binding.b5.id -> viewModel.appendToExpression("5")
            binding.b6.id -> viewModel.appendToExpression("6")
            binding.b7.id -> viewModel.appendToExpression("7")
            binding.b8.id -> viewModel.appendToExpression("8")
            binding.b9.id -> viewModel.appendToExpression("9")
            binding.bComma.id -> viewModel.appendToExpression(".")
            binding.bBackspace.id -> viewModel.removeLastCharacter()
            binding.bMinus.id -> viewModel.appendToExpression("-")
            binding.bPlus.id -> viewModel.appendToExpression("+")
            binding.bDivision.id -> viewModel.appendToExpression("÷")
            binding.bMultiplication.id -> viewModel.appendToExpression("*")
        }
    }
}