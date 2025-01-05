package com.example.goncalostore.app.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Composable function that creates a text field. Receives placeholder, value, and onValueChange as arguments
@Composable
fun CustomTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        modifier = Modifier
            .background(
                color = Color.White.copy(alpha = 0.05f),
                shape = MaterialTheme.shapes.small
            )
            .padding(4.dp),
        singleLine = true
    )
}
