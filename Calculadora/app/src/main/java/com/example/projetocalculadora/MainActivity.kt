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

    // Estados para armazenar o valor atual, operador, resultado e valor anterior
    val valorAtual = remember { mutableStateOf("") }
    val operador = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf("") }
    val valorAnterior = remember { mutableStateOf("") }

    Column {
        // Display que mostra o resultado ou o valor atual
        Text(text = "Display: ${if (resultado.value.isNotEmpty()) resultado.value else valorAtual.value}")

        // Componente que representa o teclado da calculadora
        Teclado(
            onNumberClick = { numero ->
                valorAtual.value += numero // Adiciona o número ao valor atual
            },
            onOperatorClick = { op ->
                operador.value = op //
                valorAnterior.value = valorAtual.value // Armazena o valor atual como valor anterior
                valorAtual.value = "" // Limpa o valor atual
            },
            onEqualsClick = {
                resultado.value = calcular(valorAnterior.value, valorAtual.value, operador.value) // Calcula o resultado
                valorAtual.value = resultado.value // Atualiza o valor atual com o resultado
                operador.value = "" // Limpa o operador
                valorAnterior.value = "" // Limpa o valor anterior
            },
            onClearClick = {
                // Limpa todos os valores e operadores
                valorAtual.value = ""
                operador.value = ""
                valorAnterior.value = ""
                resultado.value = ""
            }
        )
    }
}

// Componente que define o teclado da calculadora
@Composable
fun Teclado(
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column {
        // Botão para limpar a calculadora
        Row {
            Button(onClick = { onClearClick() }) {
                Text(text = "ON/C")
            }
        }

        // Botões numéricos e operadores organizados em linhas
        BotaoLinha(listOf("7", "8", "9", "÷"), onNumberClick, onOperatorClick)
        BotaoLinha(listOf("4", "5", "6", "×"), onNumberClick, onOperatorClick)
        BotaoLinha(listOf("1", "2", "3", "-"), onNumberClick, onOperatorClick)
        BotaoLinha(listOf("0", ".", "=", "+"), onNumberClick, onOperatorClick, onEqualsClick)
    }
}

// Função que cria uma linha de botões com base numa lista de etiquetas
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
                // Define o comportamento de cada botão com base na etiqueta
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

// Calculos
fun calcular(valor1: String, valor2: String, operador: String): String {
    val num1 = valor1.toDoubleOrNull() ?: return "" // Converte o primeiro valor para Double
    val num2 = valor2.toDoubleOrNull() ?: return "" // Converte o segundo valor para Double

    // Realiza o cálculo com base no operador
    return when (operador) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "×" -> (num1 * num2).toString()
        "÷" -> if (num2 != 0.0) (num1 / num2).toString() else "Erro"
        else -> ""
    }
}
