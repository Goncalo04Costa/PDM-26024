package com.example.goncalostore.app.fronted.ViewModel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalostore.app.backend.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Estado da lista de utilizadores
    private val _userListFlow = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> get() = _userListFlow



    // Estado de loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Mensagem de erro
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysOfMonth(month: Int, year: Int): List<String> {
        val daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth()
        return (1..daysInMonth).map { LocalDate.of(year, month, it).toString() }
    }



    suspend fun addUser(
        userToAdd: User,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ): Boolean {
        return try {
            val resultAuth = auth.createUserWithEmailAndPassword(userToAdd.email, userToAdd.password).await()
            val user = resultAuth.user

            if (user != null) {
                val userId = user.uid
                database.collection("User")
                    .document(userId)
                    .set(userToAdd.toStore())
                    .addOnSuccessListener {
                        Log.d("UserViewModel", "Usuário registrado com sucesso no Firestore")
                        onSuccess("Usuário registrado com sucesso")
                    }
                true
            } else {
                Log.d("UserViewModel", "Erro ao registrar usuário: Usuário nulo")
                false
            }
        } catch (ex: Exception) {
            Log.d("UserViewModel", "Erro ao registrar usuário: ${ex.message}")
            onFailure(ex.message ?: "Erro desconhecido")
            false
        }
    }

    suspend fun fetchUsers(): List<User> {
        return try {
            val query = database.collection("User").get().await()
            val userList = query.documents.mapNotNull { it.toObject(User::class.java) }
            _userListFlow.value = userList
            userList
        } catch (ex: Exception) {
            Log.d("UserViewModel", "Erro ao obter usuários: ${ex.message}")
            emptyList()
        }
    }





    }

