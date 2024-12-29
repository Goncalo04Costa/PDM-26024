package com.example.carrinhocompras.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.carrinhocompras.models.product
import com.example.carrinhocompras.repository.AddProductDatabase
import com.example.carrinhocompras.repository.fetchProductsFromDatabase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductViewModel: ViewModel() {
    val database = Firebase.firestore

    suspend fun AddProduct(product: product):Boolean{
        return try{
            val resultProduct = AddProductDatabase(product,database)
            Log.d("ProdutosViewModel","resultado:${resultProduct}")
            true
        }catch(e:Exception){
            Log.d("ProdutosViewModel", "Ocorreu um erro:${e}")
            false
        }
    }

    suspend fun FetchProducts():List<product>{
        var resultProducts = emptyList<product>()
        return try{
            resultProducts = fetchProductsFromDatabase(database)
            Log.d("ProdutosViewModel","Produtos fetched.")
            resultProducts
        }catch(e:Exception){
            Log.d("ProdutosViewModel","Ocorreu um erro: ${e}")
            emptyList()
        }
    }
}