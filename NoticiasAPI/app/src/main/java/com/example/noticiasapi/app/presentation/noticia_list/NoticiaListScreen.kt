package com.example.noticiasapi.app.presentation.noticia_list

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noticiasapi.app.domain.model.Noticia
import com.example.noticiasapi.app.domain.model.NoticiaDetail
import com.example.noticiasapi.app.presentation.noticia_list.NoticiaDetailViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject

@Composable
fun CreateText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: androidx.compose.ui.unit.TextUnit = 14.sp
) {
    Text(
        text = text,
        modifier = modifier.padding(4.dp),
        color = color,
        textAlign = textAlign,
        fontSize = fontSize
    )
}


@Composable
fun NoticiaListScreen(viewModel: NoticiaListViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        viewModel.fetchNoticias()
    }
    val news by viewModel.Noticias.collectAsState()
    Box(modifier =Modifier
        .fillMaxSize()){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(news) { item ->
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(bottom = 10.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CreateText(item.name,Modifier.fillMaxWidth(),Color.Black,
                            textAlign = TextAlign.Center, 16.sp)
                        TextButton(
                            onClick = {
                                val argNew = GenerateObject(item)
                                val encondedNew = Uri.encode(argNew)
                                navController.navigate("NewsListDetailed/${encondedNew}")
                            }){
                            Text("Details")
                        }
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter),
            onClick = {navController.popBackStack()},

            ){
            Text("Go back")
        }
    }
}
fun GenerateObject(new: Noticia):String {
    return Gson().toJson(new)
}
