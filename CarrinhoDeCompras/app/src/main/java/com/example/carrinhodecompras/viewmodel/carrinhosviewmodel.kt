package com.example.carrinhodecompras.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.carrinhodecompras.Classes.Carrinho
import com.example.carrinhodecompras.Classes.Produto
import com.example.carrinhodecompras.Classes.ProdutoCarrinho
import com.example.carrinhodecompras.Database.AddCarrinhoDatabase
import com.example.carrinhodecompras.Database.FetchCarrinhosDatabase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CarrinhosViewModel: ViewModel() {
    val database = Firebase.firestore


    suspend fun FetchCarrinhos():List<Carrinho>{
        var resultCarrinhos = emptyList<Carrinho>()
        return try{
            resultCarrinhos = FetchCarrinhosDatabase(database)
            Log.d("CarrinhosViewModel","Carrinhos fetched:${resultCarrinhos}")
            resultCarrinhos
        }catch (e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            emptyList()
        }
    }
    suspend fun AddCarrinhoDatabase(carrinho:Carrinho):Boolean{
        return try{
            val resultCarrinho = AddCarrinhoDatabase(carrinho,database)
            resultCarrinho
        }catch(e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            false
        }
    }
}