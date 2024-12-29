package com.example.carrinhocompras.repository

import android.util.Log
import com.example.carrinhocompras.models.cart
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun FetchCarrinhosDatabase(databaseReference: FirebaseFirestore):List<cart>{
    var listCarrinhos = emptyList<cart>()
    return try{
        val carrinhosDatabase = databaseReference.collection("Carrinhos")
            .get()
            .await()
        listCarrinhos=carrinhosDatabase.map{carrinho->
            carrinho.toObject(cart::class.java)
        }
        listCarrinhos
    }catch(e:Exception){
        Log.d("FcuntionsDatabaseFetch","Não foi dado fetch: ${e}")
        listCarrinhos
    }
}


suspend fun AddCarrinhoDatabase(carrinhoToAdd:cart,databaseReference: FirebaseFirestore):Boolean{
    return try{
        carrinhoToAdd.idCarrinho?.let{
            databaseReference.collection("Carrinhos")
                .document(it)
                .set(carrinhoToAdd)
                .await()
        }
        true
    }catch(e:Exception){
        Log.d("FunctionsDatabaseAdd","Não foi adicionado o carrinho:${e}")
        false
    }
}