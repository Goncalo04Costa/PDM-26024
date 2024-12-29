package com.example.carrinhocompras.viewmodel

import com.example.carrinhocompras.models.user
import com.example.carrinhocompras.repository.AddUserDatabase
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterViewModel: ViewModel() {
    val database = Firebase.firestore

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}
    suspend fun createAccount(email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userUId = result.user?.uid
            if (userUId != null) {
                val newUser = user(email, password, emptyList(), userUId, emptyList())
                val isUserAdded = AddUserDatabase(newUser, database)
                if (isUserAdded) {
                    Log.d("RegistoUtilizadorViewModel", "Utilizador ${email} criado com sucesso")
                } else {
                    Log.d("RegistoUtilizadorViewModel", "Falha ao adicionar ${email} à base de dados")
                }
                isUserAdded
            } else {
                Log.d("RegistoUtilizadorViewModel", "Erro: userUId é nulo")
                false
            }

            Log.d("RegistoUtilizadorViewModel", "Erro ao criar usuário com Firebase Auth")
                false

        } catch (e: Exception) {
            Log.d("RegistoUtilizadorViewModel", "Erro ao criar conta: ${e.message}")
            false
        }
    }
}
