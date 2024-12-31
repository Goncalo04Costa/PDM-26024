import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.goncalostore.app.presentation.view.AddCarrinho
import com.example.goncalostore.app.presentation.view.AddProduct
import com.example.goncalostore.app.presentation.view.CarrinhosScreen
import com.example.goncalostore.app.presentation.view.LoginScreen
import com.example.goncalostore.app.presentation.view.MenuProdutos
import com.example.goncalostore.app.presentation.view.MenuUtilizador
import com.example.goncalostore.app.presentation.view.NewUser

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login"){
        composable("Login") {
            LoginScreen(viewModel(),navController)
        }
        composable("MenuUtilizador/{utlzObject}",
            arguments = listOf(navArgument("utlzObject"){
                type = NavType.StringType
                nullable = false
            })
        ){ navBackStackEntry->
            val utlzEncoded =navBackStackEntry.arguments?.getString("utlzObject")
            val utlzDecoded =utlzEncoded?.let { Uri.decode(it) }
            MenuUtilizador(viewModel(),navController,utlzDecoded)
        }
        composable("RegistoUtilizador/{loginUtilizador}"){navBackStackEntry->
            val usernameEncoded = navBackStackEntry.arguments?.getString("loginUtilizador")
            if(usernameEncoded !=null){
                val usernameDecoded = usernameEncoded?.let { Uri.decode(it) }
                NewUser(viewModel(),navController,usernameDecoded)
            }else{
                NewUser(viewModel(),navController,"")
            }
        }
        composable("ProdutosScreen"){
            MenuProdutos(viewModel(),viewModel(),navController,)
        }
        composable("AddProduct"){
            AddProduct(viewModel(),navController)
        }
        composable("AddCarrinho"){
            AddCarrinho(viewModel(), viewModel(),navController)
        }
        composable("CarrinhosScreen"){
            CarrinhosScreen(viewModel(),viewModel(),navController)
        }
    }
}