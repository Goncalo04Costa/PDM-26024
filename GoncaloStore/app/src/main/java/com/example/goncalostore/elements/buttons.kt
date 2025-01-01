package com.example.goncalostore.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CreateBottomButtons(
    clickMenu: () -> Unit = {},
    clickCarrinho: () -> Unit = {},
    clickProdutos: () -> Unit = {},
    clickLogout: () -> Unit = {},
    buttonColor: Color = Color(0xFF388E3C),  // Default green color for buttons
    textColor: Color = Color.White          // Default white text color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = { clickMenu() },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Menu", color = textColor)
            }

            TextButton(
                onClick = { clickCarrinho() },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Carrinhos", color = textColor)
            }

            TextButton(
                onClick = { clickProdutos() },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Produtos", color = textColor)
            }

            TextButton(
                onClick = { clickLogout() },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Logout", color = textColor)
            }
        }
    }
}
