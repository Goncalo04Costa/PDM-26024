package com.example.carrinhodecompras.repositorio

import com.example.carrinhodecompras.classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            User(
                id = it.uid,
                name = it.displayName ?: "",
                email = it.email ?: "",
                password = it
            )
        }
    }

    fun logout() {
        auth.signOut()
    }
}
