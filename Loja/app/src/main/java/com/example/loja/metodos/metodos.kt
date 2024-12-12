package com.example.loja.metodos



import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.util.Log
import androidx.compose.runtime.remember
import com.example.loja.classes.Carrinho
import com.example.loja.classes.Produto
import com.example.loja.classes.Utilizador
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await


suspend fun AdicionaUtilizador(userToAdd: Utilizador, databaseReference: FirebaseFirestore): Boolean {
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

suspend fun AdicionaProduto(productToAdd: Produto, databaseReference: FirebaseFirestore):Boolean{
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

suspend fun ProcuraPorduto(databaseReference: FirebaseFirestore):List<Produto>{
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

suspend fun ProcuraCarrinho(databaseReference: FirebaseFirestore):List<Carrinho>{
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


suspend fun NovoCarrinho(carrinhoToAdd:Carrinho,databaseReference: FirebaseFirestore):Boolean{
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

