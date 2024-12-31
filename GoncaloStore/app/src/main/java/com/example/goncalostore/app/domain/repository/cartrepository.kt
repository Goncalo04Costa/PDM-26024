package com.example.goncalostore.app.domain.repository

import android.util.Log
import com.example.goncalostore.app.domain.model.Cart
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun NewCart(carrinhoToAdd:Cart,databaseReference: FirebaseFirestore):Boolean{
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

suspend fun FetchCart(databaseReference: FirebaseFirestore):List<Cart>{
    var listCarrinhos = emptyList<Cart>()
    return try{
        val carrinhosDatabase = databaseReference.collection("Carts")
            .get()
            .await()
        listCarrinhos=carrinhosDatabase.map{carrinho->
            carrinho.toObject(Cart::class.java)
        }
        listCarrinhos
    }catch(e:Exception){
        Log.d("FcuntionsDatabaseFetch","Não foi dado fetch: ${e}")
        listCarrinhos
    }
}
