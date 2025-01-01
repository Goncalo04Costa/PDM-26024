package com.example.goncalostore.app.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.goncalostore.app.domain.model.User
import com.example.goncalostore.app.domain.repository.NewUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewUserViewModel: ViewModel() {
    val database = Firebase.firestore

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}
    suspend fun createAccount(email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userUId = result.user?.uid
            if (result.user != null) {
                val newUser = User(email, password, emptyList(),userUId, emptyList())
                val isUserAdded = NewUser(newUser, database)
                if (isUserAdded) {
                    Log.d("RegistoUtilizadorViewModel", "Utilizador ${email} criado com sucesso")
                } else {
                    Log.d("RegistoUtilizadorViewModel", "Falha ao adicionar ${email} à base de dados")
                }
                isUserAdded
            } else {
                Log.d("RegistoUtilizadorViewModel", "Erro ao criar user ")
                false
            }
        } catch (e: Exception) {
            Log.d("RegistoUtilizadorViewModel", "Erro ao criar conta: ${e.message}")
            false
        }
    }
}
