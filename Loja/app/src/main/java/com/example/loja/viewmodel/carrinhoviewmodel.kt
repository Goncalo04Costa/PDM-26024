package com.example.loja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.loja.classes.Utilizador
import com.example.loja.classes.Produto
import com.example.loja.classes.ProdutoCarrinho
import com.example.loja.classes.Carrinho
import com.example.loja.metodos.NovoCarrinho
import com.example.loja.metodos.ProcuraCarrinho
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CarrinhosViewModel: ViewModel() {
    val database = Firebase.firestore


    suspend fun ProcuraCarrinhos():List<Carrinho>{
        var resultCarrinhos = emptyList<Carrinho>()
        return try{
            resultCarrinhos = ProcuraCarrinho(database)
            Log.d("CarrinhosViewModel","Carrinhos fetched:${resultCarrinhos}")
            resultCarrinhos
        }catch (e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            emptyList()
        }
    }
    suspend fun NovoCarrinhos(carrinho:Carrinho):Boolean{
        return try{
            val resultCarrinho = NovoCarrinho(carrinho, database)
            resultCarrinho
        }catch(e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            false
        }
    }
}