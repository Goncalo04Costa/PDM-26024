package com.example.loja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.loja.classes.Utilizador
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistoUtilizadorViewModel : ViewModel() {
    // Referência ao Firestore
    val database = Firebase.firestore

    // Referência ao FirebaseAuth
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    // Função para criar uma conta de utilizador
    suspend fun createAccount(email: String, password: String): Boolean {
        return try {
            // Criação do utilizador com Firebase Auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userUId = result.user?.uid

            if (result.user != null) {
                // Criação do objeto Utilizador
                val newUser = Utilizador(email, password, emptyList(), userUId, emptyList())

                // Adicionar o novo utilizador no Firestore
                val isUserAdded = addUserToFirestore(newUser)

                if (isUserAdded) {
                    Log.d("RegistoUtilizadorViewModel", "Utilizador $email criado com sucesso")
                } else {
                    Log.d("RegistoUtilizadorViewModel", "Falha ao adicionar $email à base de dados")
                }
                isUserAdded
            } else {
                Log.d("RegistoUtilizadorViewModel", "Erro ao criar usuário com Firebase Auth")
                false
            }
        } catch (e: Exception) {
            Log.d("RegistoUtilizadorViewModel", "Erro ao criar conta: ${e.message}")
            false
        }
    }

    // Função para adicionar o utilizador ao Firestore
    private suspend fun addUserToFirestore(user: Utilizador): Boolean {
        return try {
            val userCollection = database.collection("users")
            // Alteração aqui: usa-se o user.userId e não user.uid
            userCollection.document(user.uid ?: return false).set(user).await()
            true
        } catch (e: Exception) {
            Log.d("RegistoUtilizadorViewModel", "Erro ao adicionar utilizador no Firestore: ${e.message}")
            false
        }
    }
}
