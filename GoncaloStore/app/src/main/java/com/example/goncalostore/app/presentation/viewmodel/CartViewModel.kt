package com.example.goncalostore.app.presentation.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.goncalostore.app.domain.model.Cart
import com.example.goncalostore.app.domain.repository.FetchCart
import com.example.goncalostore.app.domain.repository.FetchProducts
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CarrinhosViewModel: ViewModel() {
    val database = Firebase.firestore


    suspend fun FetchCarrinhos():List<Cart>{
        var resultCarrinhos = emptyList<Cart>()
        return try{
            resultCarrinhos = FetchCart(database)
            Log.d("CarrinhosViewModel","Carrinhos fetched:${resultCarrinhos}")
            resultCarrinhos
        }catch (e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            emptyList()
        }
    }
    suspend fun NewCart(carrinho:Cart):Boolean{
        return try{
            val resultCarrinho = com.example.goncalostore.app.domain.repository.NewCart(carrinho,database)
            resultCarrinho
        }catch(e:Exception){
            Log.d("CarrinhosViewModel","Erro:${e}")
            false
        }
    }
}