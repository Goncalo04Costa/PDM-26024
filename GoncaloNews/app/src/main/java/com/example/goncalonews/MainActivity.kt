package com.example.goncalonews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goncalonews.app.presentation.view.NoticeDetailScreen
import com.example.goncalonews.app.presentation.view.NoticeListScreen
import com.example.goncalonews.app.presentation.viewmodel.NoticeDetailViewModel
import com.example.goncalonews.app.presentation.viewmodel.NoticeListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        var selectedNoticiaTitle by remember { mutableStateOf<String?>(null) }


        if (selectedNoticiaTitle == null) {
            val noticeListViewModel: NoticeListViewModel = viewModel()
            NoticeListScreen(noticeListViewModel) { noticiaTitle ->
                
                selectedNoticiaTitle = noticiaTitle
            }
        } //else {

//         val noticeDetailViewModel: NoticeDetailViewModel = viewModel()
  //       NoticeDetailScreen(
    //          viewModel = noticeDetailViewModel,
      //        weburl = selectedNoticiaTitle!!,
        //       onBackClick = {
           //        selectedNoticiaTitle = null
         //}
        //)
          //  }
    }
}
