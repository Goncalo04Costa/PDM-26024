package com.example.goncalostore.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.goncalostore.app.domain.model.Product
import com.example.goncalostore.app.domain.repository.NewProduct
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProdutosViewModel: ViewModel() {
    val database = Firebase.firestore

    suspend fun AddProduct(product: Product):Boolean{
        return try{
            val resultProduct = NewProduct(product,database)
            Log.d("ProdutosViewModel","resultado:${resultProduct}")
            true
        }catch(e:Exception){
            Log.d("ProdutosViewModel", "Ocorreu um erro:${e}")
            false
        }
    }

    suspend fun FetchProducts():List<Product>{
        var resultProducts = emptyList<Product>()
        return try{
            resultProducts = com.example.goncalostore.app.domain.repository.FetchProducts(database)
            Log.d("ProdutosViewModel","Produtos fetched.")
            resultProducts
        }catch(e:Exception){
            Log.d("ProdutosViewModel","Ocorreu um erro: ${e}")
            emptyList()
        }
    }
}