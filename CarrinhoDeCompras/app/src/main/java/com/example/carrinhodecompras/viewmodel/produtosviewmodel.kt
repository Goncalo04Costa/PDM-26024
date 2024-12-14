package com.example.carrinhodecompras.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.carrinhodecompras.Classes.Produto
import com.example.carrinhodecompras.Database.AddProductDatabase
import com.example.carrinhodecompras.Database.FetchProductsDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProdutosViewModel: ViewModel() {
    val database = Firebase.firestore

    suspend fun AddProduct(product: Produto):Boolean{
        return try{
            val resultProduct = AddProductDatabase(product,database)
            Log.d("ProdutosViewModel","resultado:${resultProduct}")
            true
        }catch(e:Exception){
            Log.d("ProdutosViewModel", "Ocorreu um erro:${e}")
            false
        }
    }

    suspend fun FetchProducts():List<Produto>{
        var resultProducts = emptyList<Produto>()
        return try{
            resultProducts = FetchProductsDatabase(database)
            Log.d("ProdutosViewModel","Produtos fetched.")
            resultProducts
        }catch(e:Exception){
            Log.d("ProdutosViewModel","Ocorreu um erro: ${e}")
            emptyList()
        }
    }
}