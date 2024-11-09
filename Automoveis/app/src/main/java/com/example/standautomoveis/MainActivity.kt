package com.example.stantautomoveis
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Carro(
    val marca: String,
    val modelo: String,
    var ano: Int,
    var cor: String,
    var kms: Double = 0.0,
    var contactoVendedor: Int,
    var descricao: String,
    var preco: Double
)

@Composable
fun EcraInicio(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("listaCarros") }) {
            Text("Ir para Lista de Carros")
        }
    }
}


@Composable
fun AdicionaCarro(onCarroAdicionado: (Carro) -> Unit, navController: NavHostController) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }
    var kms by remember { mutableStateOf("") }
    var contactoVendedor by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }

    // Estado para mensagens de erro
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = marca,
            onValueChange = { marca = it },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = ano,
            onValueChange = { ano = it },
            label = { Text("Ano") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = cor,
            onValueChange = { cor = it },
            label = { Text("Cor") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = kms,
            onValueChange = { kms = it },
            label = { Text("Kms") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = contactoVendedor,
            onValueChange = { contactoVendedor = it },
            label = { Text("Contacto do Vendedor") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text("Preço") },
            modifier = Modifier.fillMaxWidth()
        )


        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {

                errorMessage = ""


                if (marca.isBlank() || modelo.isBlank() || ano.isBlank() || cor.isBlank() || kms.isBlank() || contactoVendedor.isBlank() || descricao.isBlank() || preco.isBlank()) {
                    errorMessage = "Todos os campos devem ser preenchidos."
                    return@Button
                }


                val anoInt = ano.toIntOrNull()
                val kmsDouble = kms.toDoubleOrNull()
                val contactoInt = contactoVendedor.toIntOrNull()
                val precoDouble = preco.toDoubleOrNull()

                if (anoInt == null || anoInt < 1990 || anoInt > 2024) {
                    errorMessage = "Ano inválido. Insira um ano válido."
                    return@Button
                }
                if (kmsDouble == null || kmsDouble < 0) {
                    errorMessage = "Kms deve ser um número positivo."
                    return@Button
                }
                if (contactoInt == null || contactoInt <= 0) {
                    errorMessage = "Contacto do vendedor deve ser um número positivo."
                    return@Button
                }
                if (precoDouble == null || precoDouble < 0) {
                    errorMessage = "Preço deve ser um número positivo."
                    return@Button
                }

                val carro = Carro(
                    marca = marca,
                    modelo = modelo,
                    ano = anoInt,
                    cor = cor,
                    kms = kmsDouble,
                    contactoVendedor = contactoInt,
                    descricao = descricao,
                    preco = precoDouble
                )
                onCarroAdicionado(carro)
                navController.navigate("listaCarros")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Adicionar Carro")
        }

        Button(
            onClick = { navController.navigate("listaCarros") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Cancelar")
        }
    }
}


@Composable
fun ListaCarros(carros: List<Carro>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(carros) { carro ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("informacoesCarro/${carros.indexOf(carro)}") }, // Navegar para info do carro
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Marca: ${carro.marca}")
                Text(text = "Modelo: ${carro.modelo}")
                Text(text = "Ano: ${carro.ano}")
                Text(text = "Preço: ${carro.preco}")
            }
        }

        item {
            Button(onClick = { navController.navigate("adicionaCarro") }) {
                Text("Adicionar Novo Carro")
            }
        }
    }
}

@Composable
fun InformacoesCarro(
    carro: Carro,
    navController: NavHostController,
    onRemoveCarro: () -> Unit
) {
    var mostrarConfirmacaoRemocao by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Marca: ${carro.marca}")
        Text(text = "Modelo: ${carro.modelo}")
        Text(text = "Ano: ${carro.ano}")
        Text(text = "Cor: ${carro.cor}")
        Text(text = "Kms: ${carro.kms}")
        Text(text = "Contacto: ${carro.contactoVendedor}")
        Text(text = "Descrição: ${carro.descricao}")
        Text(text = "Preço: ${carro.preco}")

        Button(
            onClick = { mostrarConfirmacaoRemocao = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Remover Carro")
        }

        Button(
            onClick = { navController.navigate("listaCarros") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Voltar para Lista de Carros")
        }

        if (mostrarConfirmacaoRemocao) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacaoRemocao = false },
                title = { Text("Confirmação de Remoção") },
                text = { Text("Tem certeza que deseja remover este carro?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onRemoveCarro()
                            navController.navigate("listaCarros")
                        }
                    ) {
                        Text("Remover")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { mostrarConfirmacaoRemocao = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun StandApp() {
    val navController = rememberNavController()
    val carros = remember { mutableStateListOf<Carro>() }

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            EcraInicio(navController)
        }
        composable("adicionaCarro") {
            AdicionaCarro(onCarroAdicionado = { carros.add(it) }, navController = navController)
        }
        composable("listaCarros") {
            ListaCarros(carros = carros, navController = navController)
        }
        composable("informacoesCarro/{carroIndex}") { backStackEntry ->
            val carroIndex = backStackEntry.arguments?.getString("carroIndex")?.toInt() ?: -1
            if (carroIndex in carros.indices) {
                InformacoesCarro(
                    carro = carros[carroIndex],
                    navController = navController,
                    onRemoveCarro = {
                        carros.removeAt(carroIndex)
                        navController.navigate("listaCarros")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarroAppPreview() {
    StandApp()
}
