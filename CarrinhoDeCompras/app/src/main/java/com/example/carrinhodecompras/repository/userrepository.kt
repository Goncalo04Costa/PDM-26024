package com.example.carrinhodecompras.Database

import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.util.Log
import androidx.compose.runtime.remember
import com.example.carrinhodecompras.Classes.Carrinho
import com.example.carrinhodecompras.Classes.Produto
import com.example.carrinhodecompras.Classes.Utilizador
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await


suspend fun AddUserDatabase(userToAdd: Utilizador, databaseReference: FirebaseFirestore): Boolean {
    return try {
        databaseReference.collection("Utilizador")
            .document(userToAdd.email)
            .set(userToAdd)
            .await()
        Log.d("FunctionsDatabase", "Utilizador adicionado à base de dados ${userToAdd.email}")
        true
    } catch (e: Exception) {
        Log.d("FunctionsDatabase", "Erro ao adicionar: ${userToAdd.email}", e)
        false
    }
}

suspend fun AddProductDatabase(productToAdd: Produto, databaseReference: FirebaseFirestore):Boolean{
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

suspend fun FetchProductsDatabase(databaseReference: FirebaseFirestore):List<Produto>{
    var listProducts = emptyList<Produto>()
    return try{
        val productsDatabase = databaseReference.collection("Produtos")
            .get()
            .await()
        listProducts = productsDatabase.map { product->
            product.toObject(Produto::class.java)
        }
        listProducts
    }catch (e:Exception){
        Log.d("FunctionsDatabaseFetch","Não foi dado fetch: ${e}")
        listProducts
    }
}

suspend fun FetchCarrinhosDatabase(databaseReference: FirebaseFirestore):List<Carrinho>{
    var listCarrinhos = emptyList<Carrinho>()
    return try{
        val carrinhosDatabase = databaseReference.collection("Carrinhos")
            .get()
            .await()
        listCarrinhos=carrinhosDatabase.map{carrinho->
            carrinho.toObject(Carrinho::class.java)
        }
        listCarrinhos
    }catch(e:Exception){
        Log.d("FcuntionsDatabaseFetch","Não foi dado fetch: ${e}")
        listCarrinhos
    }
}


suspend fun AddCarrinhoDatabase(carrinhoToAdd:Carrinho,databaseReference: FirebaseFirestore):Boolean{
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

suspend fun AddTesteDatabase(userToAdd: Utilizador, databaseReference: FirebaseFirestore):Boolean{
    return try {
        databaseReference.collection("Utilizador")
            .document(userToAdd.email)
            .set(userToAdd)
        val updatedCarrinho = listOf(1,2,3)
        databaseReference.collection("Utilizador")
            .document(userToAdd.email)
            .update("carrinhoCompras",updatedCarrinho)
            .await()
        Log.d("FunctionsDatabase","Adicionado a lista:${updatedCarrinho}")
        true
    }catch (e:Exception){
        Log.d("RegistoUtilizadorViewModel","Não adicionado a lista: ${e}")
        false
    }
}