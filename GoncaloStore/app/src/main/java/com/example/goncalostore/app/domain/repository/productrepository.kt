package com.example.goncalostore.app.domain.repository

import android.util.Log
import com.example.goncalostore.app.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun NewProduct(productToAdd: Product, databaseReference: FirebaseFirestore):Boolean{
    return try {
        productToAdd.nome?.let {
            databaseReference.collection("Produtos")
                .document(it)
                .set(productToAdd)
                .await()
        }
        true
    }catch(e:Exception){
        Log.d("FunctionsDatabase","Não foi adicionado: ${e}")
        false
    }
}

suspend fun FetchProducts(databaseReference: FirebaseFirestore):List<Product>{
    var listProducts = emptyList<Product>()
    return try{
        val productsDatabase = databaseReference.collection("Produtos")
            .get()
            .await()
        listProducts = productsDatabase.map { product->
            product.toObject(Product::class.java)
        }
        listProducts
    }catch (e:Exception){
        Log.d("FunctionsDatabaseFetch","Não foi dado fetch: ${e}")
        listProducts
    }
}
