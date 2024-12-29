package com.example.carrinhocompras.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.carrinhocompras.models.cart
import com.example.carrinhocompras.repository.FetchCarrinhosDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class cartviewmodel: ViewModel() {
    val database = Firebase.firestore


    suspend fun FetchCarrinhos():List<cart>{
        var resultCarrinhos = emptyList<cart>()
        return try{
            resultCarrinhos = FetchCarrinhosDatabase(database)
            Log.d("CarrinhosViewModel","Carrinhos fetched:${resultCarrinhos}")
            resultCarrinhos
        }catch (e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            emptyList()
        }
    }
    suspend fun AddCarrinhoDatabase(carrinho: cart):Boolean{
        return try{
            val resultCarrinho = AddCarrinhoDatabase(carrinho)
            resultCarrinho
        }catch(e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            false
        }
    }
}