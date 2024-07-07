package com.app.calculator_umb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    // Operaciones
    val suma = "+"
    val resta = "-"
    val multiplicacion = "*"
    val division = "/"
    val porcentaje = "%"

    // Seleccion de operaciones
    var operacion = ""

    // Números
    var numeroUno: Double = Double.NaN
    var numeroDos: Double = Double.NaN

    lateinit var tvTemporal: TextView
    lateinit var tvResultado: TextView

    var formatoDecimal = Formato()  // Asumiendo que Formato es una clase que formatea los números

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        tvTemporal = findViewById(R.id.temporal)
        tvResultado = findViewById(R.id.resultado)

        // Inicializar botones
        val buttons = arrayOf(
            R.id.button_c, R.id.button_del, R.id.button_percent, R.id.button_div,
            R.id.button_seven, R.id.button_eight, R.id.button_nine, R.id.button_equis,
            R.id.button_cuatro, R.id.button_cinco, R.id.button_seis, R.id.button_minus,
            R.id.button_one, R.id.button_two, R.id.button_three, R.id.button_plus,
            R.id.button_dot, R.id.button_zero, R.id.button_doublezero, R.id.button_equal
        )

        // Asignar listeners a los botones
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onClick(it as Button) }
        }
    }

    private fun onClick(view: Button) {
        when (view.id) {
            R.id.button_c -> clear()
            R.id.button_del -> delete()
            R.id.button_equal -> calculate()
            R.id.button_plus, R.id.button_minus, R.id.button_equis, R.id.button_div, R.id.button_percent -> selectOperation(view)
            else -> appendNumber(view.text.toString())
        }
    }

    private fun clear() {
        numeroUno = Double.NaN
        numeroDos = Double.NaN
        operacion = ""
        tvTemporal.text = ""
        tvResultado.text = "0"
    }

    private fun delete() {
        val currentText = tvTemporal.text.toString()
        if (currentText.isNotEmpty()) {
            tvTemporal.text = currentText.dropLast(1)
        }
    }

    private fun calculate() {
        if (!operacion.isEmpty()) {
            numeroDos = tvTemporal.text.toString().toDouble()
            val result = when (operacion) {
                suma -> numeroUno + numeroDos
                resta -> numeroUno - numeroDos
                multiplicacion -> numeroUno * numeroDos
                division -> numeroUno / numeroDos
                porcentaje -> numeroUno * (numeroDos / 100)
                else -> Double.NaN
            }
            tvResultado.text = formatoDecimal.format(result)
            operacion = ""
        }
    }

    private fun selectOperation(view: Button) {
        if (!tvTemporal.text.isEmpty()) {
            numeroUno = tvTemporal.text.toString().toDouble()
            operacion = view.text.toString()
            tvTemporal.text = ""
        }
    }

    private fun appendNumber(number: String) {
        tvTemporal.text = "${tvTemporal.text}$number"
    }
}

class Formato {
    fun format(number: Double): String {
        return if (number == number.toLong().toDouble()) {
            number.toLong().toString()
        } else {
            number.toString()
        }
    }
}
