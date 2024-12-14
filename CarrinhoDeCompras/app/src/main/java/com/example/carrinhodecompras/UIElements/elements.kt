package com.example.carrinhodecompras.UIElements

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.material3.*

// Reusable Text composable for general use
@Composable
fun CreateText(
    string: String,
    modifier: Modifier,
    color: Color,
    textAlign: TextAlign? = null,
    size: TextUnit
) {
    Text(
        text = string,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        fontSize = size
    )
}

// Center-aligned Title text
@Composable
fun CreateTextTitle(
    string: String,
    modifier: Modifier,
    color: Color,
    size: TextUnit
) {
    Text(
        text = string,
        modifier = modifier,
        color = color,
        fontSize = size,
        textAlign = TextAlign.Center
    )
}

// Reusable TextField composable for input fields (with password handling)


@OptIn(ExperimentalMaterial3Api::class) // Opting-in for experimental material APIs
@Composable
fun CreateTextField(
    value: String,
    modifier: Modifier,
    labelText: String,
    valueChange: (String) -> Unit,
    typeKeyboard: KeyboardType,
    hideText: Boolean
) {
    TextField(
        value = value,
        modifier = modifier,
        label = { Text(labelText) },
        onValueChange = valueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = typeKeyboard,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (hideText) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray
        )
    )
}
