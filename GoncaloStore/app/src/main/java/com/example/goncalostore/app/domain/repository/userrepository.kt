package com.example.goncalostore.app.domain.repository

import android.util.Log
import com.example.goncalostore.app.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun NewUser(userToAdd: User, databaseReference: FirebaseFirestore): Boolean {
    return try {
        databaseReference.collection("Users")
            .document(userToAdd.email)
            .set(userToAdd)
            .await()
        Log.d("FunctionsDatabase", "Sucesso ${userToAdd.email}")
        true
    } catch (e: Exception) {
        Log.d("FunctionsDatabase", "Erro ao adicionar: ${userToAdd.email}", e)
        false
    }
}