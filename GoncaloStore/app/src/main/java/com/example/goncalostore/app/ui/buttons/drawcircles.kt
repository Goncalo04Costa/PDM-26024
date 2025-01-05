package com.example.goncalostore.app.ui.buttons

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.goncalostore.app.ui.colors.DarkBlue
import com.example.goncalostore.app.ui.colors.GrayTone1
import com.example.goncalostore.app.ui.colors.GrayTone2
import com.example.goncalostore.app.ui.colors.LightBlue


val schemes : Map <String,Map<String,Color>> = mapOf(
    "bb" to mapOf(
        "secondary" to LightBlue,
        "primary" to DarkBlue
    ),
    "bgg" to mapOf(
        "tertiary" to GrayTone2,
        "secondary" to GrayTone1,
        "primary" to DarkBlue
    ),
)

@Composable
fun drawCircles(scheme: String, heightPosMultiplier: Float, widthPosMultiplier: Float, offset: Float, rad: Float){
    val schemeColors = schemes[scheme] ?: emptyMap()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var ofsCount = schemeColors.size - 1
            for ((name, color) in schemeColors) {
                drawCircle(
                    color = color,
                    radius = if(rad == 0f){size.width}
                    else{rad},
                    center = Offset(size.width * widthPosMultiplier, size.height * heightPosMultiplier - offset * ofsCount)
                )
                ofsCount -= 1
            }
        }
    }
}

@Composable
fun drawCircles(color: Color, text: String, fontSize: TextUnit) {
    Box(
        modifier = Modifier
            .size(430.dp)
            .offset(x = -100.dp, y = -280.dp)
            .background(color, shape = CircleShape)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = fontSize
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        )
    }
}

@Composable
fun drawProfileCircles(
    darkBlueColor: Color,
    lightBlueColor: Color,
    darkCircleHeightFraction: Float,
    lightCircleHeightFraction: Float,
    darkCircleRadiusFraction: Float,
    lightCircleRadiusFraction: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp) // Define a altura do cabeçalho
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            // Desenhar o círculo azul claro (maior)
            drawCircle(
                color = lightBlueColor,
                radius = width * lightCircleRadiusFraction,
                center = Offset(width / 2, height * lightCircleHeightFraction)
            )

            // Desenhar o círculo azul escuro (menor)
            drawCircle(
                color = darkBlueColor,
                radius = width * darkCircleRadiusFraction,
                center = Offset(width / 2, height * darkCircleHeightFraction)
            )
        }
    }
}