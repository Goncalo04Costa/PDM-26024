package com.example.carrinhodecompras.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrinhodecompras.Classes.Utilizador
import com.example.carrinhodecompras.Database.AddUserDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class RegistoUtilizadorViewModel: ViewModel() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database = Firebase.firestore

    suspend fun createAccount(email: String, password: String): Boolean {
        return try {
            // Validação do email e senha
            if (email.isBlank() || password.isBlank()) {
                Log.d("RegistoUtilizadorViewModel", "Erro: Email ou senha em branco")
                return false
            }

            // Criar a conta no Firebase Auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            // Obter o ID do usuário
            val userUId = result.user?.uid
            if (userUId != null) {
                // Criar um objeto Utilizador
                val newUser = Utilizador(email, password, emptyList(), userUId, emptyList())

                // Adicionar o usuário ao banco de dados
                val isUserAdded = AddUserDatabase(newUser, database)

                if (isUserAdded) {
                    Log.d("RegistoUtilizadorViewModel", "Utilizador ${email} criado com sucesso")
                } else {
                    Log.d(
                        "RegistoUtilizadorViewModel",
                        "Falha ao adicionar ${email} à base de dados"
                    )
                }
                isUserAdded
            } else {
                Log.d(
                    "RegistoUtilizadorViewModel",
                    "Erro: Firebase Auth não retornou um usuário válido"
                )
                false
            }
        } catch (e: Exception) {
            // Log para erro genérico
            Log.d("RegistoUtilizadorViewModel", "Erro ao criar conta: ${e.message}")
            false
        }
    }
}
