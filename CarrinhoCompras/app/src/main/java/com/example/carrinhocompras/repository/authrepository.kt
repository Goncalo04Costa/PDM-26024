package com.example.carrinhocompras.repository

import android.util.Log
import com.example.carrinhocompras.models.user
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Função para adicionar o usuário no Firestore
suspend fun addUserToDatabase(user: user, databaseReference: FirebaseFirestore): Result<Boolean> {
    return try {
        databaseReference.collection("Utilizadores")
            .document(user.uid)  // Usando o UID do usuário como identificador único
            .set(user)
            .await()
        Log.d("FunctionsDatabase", "Usuário ${user.email} adicionado à base de dados")
        Result.success(true)
    } catch (e: Exception) {
        Log.d("FunctionsDatabase", "Erro ao adicionar o usuário ${user.email}", e)
        Result.failure(e)  // Retorna a exceção para o código chamador lidar com ela
    }
}
