package com.example.app.domain.repository



import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.app.domain.model.Utilizador
import com.example.app.domain.repository.UtilizadorRepository

class AuthRepository(private val utilizadorRepository: UtilizadorRepository) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Método para fazer login com email e senha
    fun login(email: String, senha: String, onComplete: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val uid = user?.uid
                    if (uid != null) {
                        // Recuperar ou criar o Utilizador
                        recuperarOuCriarUtilizador(uid, email)
                    }
                    onComplete(true, uid)
                    Log.d("AuthRepository", "Login bem-sucedido: ${user?.email}")
                } else {
                    onComplete(false, task.exception?.message)
                    Log.d("AuthRepository", "Falha no login: ${task.exception?.message}")
                }
            }
    }

    // Método para recuperar ou criar o Utilizador no repositório
    private fun recuperarOuCriarUtilizador(uid: String, email: String) {
        val utilizadorExistente = utilizadorRepository.getUtilizadores().find { it.id == uid }
        if (utilizadorExistente == null) {
            // Criar um novo utilizador
            val novoUtilizador = Utilizador(
                id = uid,
                nome = "Nome do user",
                contacto = "Contato",
                NIF = "NIF",
                FotoPerfil = "URL Foto Perfil"
            )
            utilizadorRepository.addUtilizador(novoUtilizador)
            Log.d("AuthRepository", "Novo utilizador criado: $novoUtilizador")
        }
    }

    // Método para fazer logout
    fun logout() {
        firebaseAuth.signOut()
        Log.d("AuthRepository", "Usuário deslogado com sucesso")
    }

    // Verifica se o usuário está logado
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}
