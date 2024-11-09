package com.example.projetocalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetocalculadora.ui.theme.ProjetoCalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjetoCalculadoraTheme {
                Calculadora()
            }
        }
    }
}

@Composable
fun Calculadora() {

    val valorAtual = remember { mutableStateOf("") }
    val operador = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf("") }
    val valorAnterior = remember { mutableStateOf("") }

    Column {

        Text(text = "Display: ${if (resultado.value.isNotEmpty()) resultado.value else valorAtual.value}")


        Teclado(
            onNumberClick = { numero ->
                valorAtual.value += numero
            },
            onOperatorClick = { op ->
                operador.value = op
                valorAnterior.value = valorAtual.value
                valorAtual.value = ""
            },
            onEqualsClick = {
                resultado.value = calcular(valorAnterior.value, valorAtual.value, operador.value)
                valorAtual.value = resultado.value
                operador.value = ""
                valorAnterior.value = ""
            },
            onClearClick = {
                valorAtual.value = ""
                operador.value = ""
                valorAnterior.value = ""
                resultado.value = ""
            }
        )
    }
}

@Composable
fun Teclado(
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column {
        Row {
            Button(onClick = { onClearClick() }) {
                Text(text = "ON/C")
            }
        }


        BotaoLinha(listOf("7", "8", "9", "÷"), onNumberClick, onOperatorClick)
        BotaoLinha(listOf("4", "5", "6", "×"), onNumberClick, onOperatorClick)
        BotaoLinha(listOf("1", "2", "3", "-"), onNumberClick, onOperatorClick)
        BotaoLinha(listOf("0", ".", "=", "+"), onNumberClick, onOperatorClick, onEqualsClick)
    }
}

@Composable
fun BotaoLinha(
    labels: List<String>,
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onEqualsClick: (() -> Unit)? = null
) {
    Row {
        labels.forEach { label ->
            Button(onClick = {
                when (label) {
                    in "0".."9", "." -> onNumberClick(label)
                    "÷", "×", "-", "+" -> onOperatorClick(label)
                    "=" -> onEqualsClick?.invoke()
                }
            }) {
                Text(label)
            }
        }
    }
}


fun calcular(valor1: String, valor2: String, operador: String): String {
    val num1 = valor1.toDoubleOrNull() ?: return ""
    val num2 = valor2.toDoubleOrNull() ?: return ""

    return when (operador) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "×" -> (num1 * num2).toString()
        "÷" -> if (num2 != 0.0) (num1 / num2).toString() else "Erro"
        else -> ""
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Calculadora()
}
