package com.example.goncalostore.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goncalostore.app.fronted.View.FeedScreen
import com.example.goncalostore.app.fronted.View.LoginScreen
import com.example.goncalostore.app.fronted.View.MostraCarrinhosScreen
import com.example.goncalostore.app.fronted.View.NavBarScreen
import com.example.goncalostore.app.fronted.View.RegisterProductScreen
import com.example.goncalostore.app.fronted.View.RegisterUserScreen
import com.example.goncalostore.app.fronted.ViewModel.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String) {

    val loginViewModel: LoginViewModel = viewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.FEED) {
            NavBarScreen(content = {
                FeedScreen(navController = navController)
            }, navController = navController,
                LoginViewModel = loginViewModel)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(Routes.REGIST) {
            NavBarScreen(content = {
                RegisterUserScreen(navController = navController)
            }, navController = navController,
                LoginViewModel = loginViewModel)
        }


        composable(Routes.ADDPRODUCT) {
            NavBarScreen(content = {
                RegisterProductScreen(navController = navController)
            }, navController = navController,
                LoginViewModel = loginViewModel)
        }


        composable(Routes.LISTCARTS) {
            NavBarScreen(content = {
                MostraCarrinhosScreen(navController = navController)
            }, navController = navController,
                LoginViewModel = loginViewModel)
        }




    }
}