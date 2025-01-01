package com.example.goncalostore.elements

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit


@Composable
fun CreateText(
    string: String,
    modifier: Modifier = Modifier,
    color: Color,
    textAlign: TextAlign? = null,
    fontSize: TextUnit = TextUnit.Unspecified // Define fontSize here
) {
    Text(
        text = string,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        fontSize = fontSize // Apply the fontSize parameter
    )
}

@Composable
fun CreateTextTitle(string: String, modifier: Modifier, color: Color, size: TextUnit) {
    Text(
        text = string,
        modifier = modifier,
        color = color,
        fontSize = size,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CreateTextField(
    value: String,
    modifier: Modifier,
    labelText: String,
    valueChange: (String) -> Unit,
    typeKeyboard: KeyboardType,
    hideText: Boolean,
    isError: Boolean = false, // Added for error styling
    labelColor: Color = Color.Gray // Default color for the label
) {
    TextField(
        value = value,
        modifier = modifier,
        label = { Text(labelText, color = labelColor) },
        onValueChange = valueChange,
        keyboardOptions = KeyboardOptions(keyboardType = typeKeyboard),
        visualTransformation = if (hideText) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError // Added error handling
    )
}
