package com.example.goncalostore.app.domain.repository

import android.util.Log
import com.example.goncalostore.app.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun NewUser(userToAdd: User, databaseReference: FirebaseFirestore): Boolean {
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