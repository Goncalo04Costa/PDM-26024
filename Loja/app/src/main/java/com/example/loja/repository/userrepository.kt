package com.example.loja.repository

import android.util.Log
import com.example.loja.classes.Utilizador
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val databaseReference: FirebaseFirestore
) {


    suspend fun novoutilizador(email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return false


            val novoUtilizador = Utilizador(email, password, emptyList(), userId, emptyList())
            databaseReference.collection("Utilizadores").document(userId).set(novoUtilizador).await()
            true
        } catch (e: Exception) {
            Log.d("UserRepository", "Erro ao registrar utilizador: ${e.message}")
            false
        }
    }


    suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.d("UserRepository", "Erro ao fazer login: ${e.message}")
            null
        }
    }


    fun atualuser(): FirebaseUser? {
        return auth.currentUser
    }


    suspend fun associarCarrinho(userId: String, carrinhoId: String): Boolean {
        return try {
            val utilizadorRef = databaseReference.collection("Utilizadores").document(userId)
            utilizadorRef.update("carrinhoCompras", carrinhoId).await()
            true
        } catch (e: Exception) {
            Log.d("UserRepository", "Erro ao associar carrinho: ${e.message}")
            false
        }
    }


    fun logout() {
        auth.signOut()
    }
}
