package com.example.loja.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.loja.classes.Produto
import com.example.loja.metodos.ProcuraPorduto
import com.example.loja.metodos.AdicionaProduto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProdutosViewModel: ViewModel() {
    val database = Firebase.firestore

    suspend fun AddProduct(product: Produto):Boolean{
        return try{
            val resultProduct = AdicionaProduto(product,database)
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
            resultProducts = ProcuraPorduto(database)
            Log.d("ProdutosViewModel","Produtos fetched.")
            resultProducts
        }catch(e:Exception){
            Log.d("ProdutosViewModel","Ocorreu um erro: ${e}")
            emptyList()
        }
    }
}