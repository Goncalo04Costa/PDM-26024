package com.example.carrinhocompras.repository

import android.util.Log
import com.example.carrinhocompras.models.product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun AddProductDatabase(productToAdd: product, databaseReference: FirebaseFirestore):Boolean{
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

suspend fun fetchProductsFromDatabase(databaseReference: FirebaseFirestore): List<product> {
    return try {
        val productsDatabase = databaseReference.collection("Produtos")
            .get()
            .await()
        productsDatabase.map { it.toObject(product::class.java) }
    } catch (e: Exception) {
        Log.d("FunctionsDatabaseFetch", "Erro ao buscar produtos: ${e.message}")
        emptyList()
    }
}